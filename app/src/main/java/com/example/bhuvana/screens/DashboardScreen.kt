package com.example.bhuvana.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bhuvana.components.BottomNavigationBar
import com.example.bhuvana.models.ProductItem
import com.example.bhuvana.security.AdminSecurity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

@Composable
fun DashboardScreen(navController: NavController) {

    val db = FirebaseFirestore.getInstance()

    var productList by remember { mutableStateOf(listOf<ProductItem>()) }

    var productToDelete by remember { mutableStateOf<ProductItem?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    var showAdminDialog by remember { mutableStateOf(false) }
    var adminPin by remember { mutableStateOf("") }
    var pinError by remember { mutableStateOf("") }
    var pendingAction by remember { mutableStateOf("") }
    var pendingProduct by remember { mutableStateOf<ProductItem?>(null) }

    var listener: ListenerRegistration? = null

    val highProducts = productList.count {
        extractDashboardPriceValue(it.price) >= 50.0
    }

    val lowProducts = productList.count {
        extractDashboardPriceValue(it.price) < 50.0
    }

    LaunchedEffect(Unit) {
        listener = db.collection("products")
            .addSnapshotListener { snapshot, error ->

                if (error != null) return@addSnapshotListener

                if (snapshot != null) {
                    productList = snapshot.documents.map { document ->
                        ProductItem(
                            id = document.id,
                            name = document.getString("name") ?: "",
                            price = document.getString("price") ?: ""
                        )
                    }
                }
            }
    }

    DisposableEffect(Unit) {
        onDispose {
            listener?.remove()
        }
    }

    if (showAdminDialog) {

        AlertDialog(
            onDismissRequest = {
                showAdminDialog = false
                adminPin = ""
                pinError = ""
                pendingAction = ""
                pendingProduct = null
            },
            title = {
                Text("Admin Verification")
            },
            text = {
                Column {

                    Text(
                        text = "Enter admin PIN to continue",
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = adminPin,
                        onValueChange = {
                            adminPin = it
                            pinError = ""
                        },
                        label = {
                            Text("Admin PIN")
                        },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (pinError.isNotBlank()) {

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = pinError,
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {

                        if (AdminSecurity.isValidPin(adminPin)) {

                            showAdminDialog = false
                            pinError = ""

                            when (pendingAction) {

                                "ADD" -> {
                                    navController.navigate("addProduct")
                                }

                                "EDIT" -> {
                                    pendingProduct?.let { product ->
                                        navController.navigate("editProduct/${product.id}")
                                    }
                                }

                                "DELETE" -> {
                                    productToDelete = pendingProduct
                                    showDeleteDialog = true
                                }
                            }

                            adminPin = ""
                            pendingAction = ""
                            pendingProduct = null

                        } else {
                            pinError = "Invalid PIN"
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00B37E)
                    )
                ) {
                    Text(
                        text = "Verify",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        showAdminDialog = false
                        adminPin = ""
                        pinError = ""
                        pendingAction = ""
                        pendingProduct = null
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showDeleteDialog && productToDelete != null) {

        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                productToDelete = null
            },
            title = {
                Text("Delete Product")
            },
            text = {
                Text("Are you sure you want to delete ${productToDelete?.name}?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        db.collection("products")
                            .document(productToDelete!!.id)
                            .delete()

                        showDeleteDialog = false
                        productToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        showDeleteDialog = false
                        productToDelete = null
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    pendingAction = "ADD"
                    pendingProduct = null
                    adminPin = ""
                    pinError = ""
                    showAdminDialog = true
                },
                containerColor = Color(0xFF00B37E)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.Black
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
        containerColor = Color(0xFF07110D)
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF07110D),
                            Color(0xFF0B1712),
                            Color(0xFF07110D)
                        )
                    )
                )
                .padding(16.dp)
        ) {

            item {

                DashboardHeader()

                Spacer(modifier = Modifier.height(18.dp))

                DashboardMarketCard(
                    navController = navController,
                    totalProducts = productList.size
                )

                Spacer(modifier = Modifier.height(16.dp))

                PriceRuleCard(
                    highProducts = highProducts,
                    lowProducts = lowProducts
                )

                Spacer(modifier = Modifier.height(16.dp))

                InventoryValueCard(productList.size)

                Spacer(modifier = Modifier.height(16.dp))

                CommodityTrendsCard(
                    products = productList,
                    onEditClick = { product ->
                        pendingAction = "EDIT"
                        pendingProduct = product
                        adminPin = ""
                        pinError = ""
                        showAdminDialog = true
                    },
                    onDeleteClick = { product ->
                        pendingAction = "DELETE"
                        pendingProduct = product
                        adminPin = ""
                        pinError = ""
                        showAdminDialog = true
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun DashboardHeader() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF12352B)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Store,
                    contentDescription = null,
                    tint = Color(0xFF55F7B6)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "SANTE-PRICE\nINDEX",
                color = Color(0xFF55F7B6),
                fontSize = 25.sp,
                lineHeight = 25.sp,
                fontWeight = FontWeight.Black
            )
        }

        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = null,
            tint = Color(0xFF55F7B6)
        )
    }
}

@Composable
fun DashboardMarketCard(
    navController: NavController,
    totalProducts: Int
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF151817)
        )
    ) {

        Column(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFF25332E),
                    shape = RoundedCornerShape(18.dp)
                )
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xFF151817),
                            Color(0xFF0E211A)
                        )
                    )
                )
                .padding(22.dp)
        ) {

            Text(
                text = "MARKET OVERVIEW",
                color = Color.White.copy(alpha = 0.75f),
                fontSize = 12.sp,
                letterSpacing = 2.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "$totalProducts Live Products",
                color = Color(0xFF55F7B6),
                fontSize = 34.sp,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Live Firebase prices are synced with Price Watch and Customer Board.",
                color = Color.White.copy(alpha = 0.80f),
                fontSize = 15.sp,
                lineHeight = 21.sp
            )

            Spacer(modifier = Modifier.height(22.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Button(
                    onClick = {
                        navController.navigate("calculator")
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00B37E)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Calculate,
                        contentDescription = null,
                        tint = Color.Black
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "Calc",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = {
                        navController.navigate("digitalSlate")
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFD84D)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ViewModule,
                        contentDescription = null,
                        tint = Color.Black
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "Board",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun PriceRuleCard(
    highProducts: Int,
    lowProducts: Int
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF151817)
        )
    ) {

        Column(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFF25332E),
                    shape = RoundedCornerShape(18.dp)
                )
                .padding(20.dp)
        ) {

            Text(
                text = "₹50 Price Rule",
                color = Color.White,
                fontSize = 21.sp,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "₹50 or above = UP. Below ₹50 = DOWN.",
                color = Color.White.copy(alpha = 0.72f),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                RuleMiniCard(
                    title = "UP",
                    value = highProducts.toString(),
                    color = Color(0xFFFFD84D),
                    iconUp = true,
                    modifier = Modifier.weight(1f)
                )

                RuleMiniCard(
                    title = "DOWN",
                    value = lowProducts.toString(),
                    color = Color(0xFF55F7B6),
                    iconUp = false,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun RuleMiniCard(
    title: String,
    value: String,
    color: Color,
    iconUp: Boolean,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .background(
                color = Color(0xFF101513),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = Color(0xFF25332E),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector =
                if (iconUp)
                    Icons.Default.TrendingUp
                else
                    Icons.Default.TrendingDown,
            contentDescription = null,
            tint = color
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column {

            Text(
                text = title,
                color = Color.White.copy(alpha = 0.65f),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = value,
                color = color,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}

@Composable
fun InventoryValueCard(totalProducts: Int) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF151817)
        )
    ) {

        Column(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFF25332E),
                    shape = RoundedCornerShape(18.dp)
                )
                .padding(22.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Inventory Summary",
                    color = Color.White.copy(alpha = 0.75f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Icon(
                    imageVector = Icons.Default.Inventory,
                    contentDescription = null,
                    tint = Color(0xFFFFD84D)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "$totalProducts Products Available",
                color = Color(0xFFFFD84D),
                fontSize = 28.sp,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(14.dp))

            LinearProgressIndicator(
                progress = 0.75f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(50.dp)),
                color = Color(0xFFFFD84D),
                trackColor = Color(0xFF2D3330)
            )
        }
    }
}

@Composable
fun CommodityTrendsCard(
    products: List<ProductItem>,
    onEditClick: (ProductItem) -> Unit,
    onDeleteClick: (ProductItem) -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF151817)
        )
    ) {

        Column(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFF25332E),
                    shape = RoundedCornerShape(18.dp)
                )
                .padding(22.dp)
        ) {

            Text(
                text = "Latest Products",
                color = Color.White,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (products.isEmpty()) {

                Text(
                    text = "No products added yet",
                    color = Color.Gray,
                    fontSize = 16.sp
                )

            } else {

                products.take(5).forEach { product ->

                    CommodityRow(
                        product = product,
                        onEditClick = {
                            onEditClick(product)
                        },
                        onDeleteClick = {
                            onDeleteClick(product)
                        }
                    )

                    Spacer(modifier = Modifier.height(18.dp))
                }
            }
        }
    }
}

@Composable
fun CommodityRow(
    product: ProductItem,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {

    val priceValue = extractDashboardPriceValue(product.price)
    val isUp = priceValue >= 50.0

    val trendColor =
        if (isUp)
            Color(0xFFFFD84D)
        else
            Color(0xFF55F7B6)

    val trendText =
        if (isUp)
            "UP"
        else
            "DOWN"

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF26332F)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Store,
                contentDescription = null,
                tint = Color(0xFF55F7B6),
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = product.name,
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Live Firebase Price",
                color = Color.White.copy(alpha = 0.65f),
                fontSize = 11.sp
            )
        }

        Column(
            horizontalAlignment = Alignment.End
        ) {

            Text(
                text = product.price,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = trendText,
                color = trendColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Black
            )
        }

        IconButton(
            onClick = onEditClick
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                tint = Color(0xFFFFD84D)
            )
        }

        IconButton(
            onClick = onDeleteClick
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.Red
            )
        }
    }
}

fun extractDashboardPriceValue(priceText: String): Double {

    val cleanText =
        priceText.replace("[^0-9.]".toRegex(), "")

    return cleanText.toDoubleOrNull() ?: 0.0
}