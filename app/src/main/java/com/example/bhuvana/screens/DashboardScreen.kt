package com.example.bhuvana.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bhuvana.models.ProductItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

@Composable
fun DashboardScreen(navController: NavController) {

    val db = FirebaseFirestore.getInstance()

    var productList by remember { mutableStateOf(listOf<ProductItem>()) }
    var productToDelete by remember { mutableStateOf<ProductItem?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    var listener: ListenerRegistration? = null

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
                Text("Delete ${productToDelete?.name}?")
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
                    navController.navigate("addProduct")
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
            PremiumBottomBar(navController)
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

                PremiumTopHeader()

                Spacer(modifier = Modifier.height(18.dp))

                MarketHealthCard(navController)

                Spacer(modifier = Modifier.height(16.dp))

                InventoryValueCard(productList.size)

                Spacer(modifier = Modifier.height(16.dp))

                CommodityTrendsCard(
                    products = productList,
                    onEditClick = { product ->
                        navController.navigate("editProduct/${product.id}")
                    },
                    onDeleteClick = { product ->
                        productToDelete = product
                        showDeleteDialog = true
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                RegionalHubCard()

                Spacer(modifier = Modifier.height(16.dp))

                PremiumGrowthCard()
            }
        }
    }
}

@Composable
fun PremiumTopHeader() {

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
                    tint = Color(0xFF43F5B6)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "SANTE-PRICE\nINDEX",
                color = Color(0xFF55F7B6),
                fontSize = 25.sp,
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
fun MarketHealthCard(navController: NavController) {

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
                text = "MARKET HEALTH",
                color = Color.White.copy(alpha = 0.75f),
                fontSize = 12.sp,
                letterSpacing = 2.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Prices\ntrending\nUP",
                    color = Color(0xFF55F7B6),
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Black,
                    lineHeight = 44.sp
                )

                Icon(
                    imageVector = Icons.Default.TrendingUp,
                    contentDescription = null,
                    tint = Color(0xFF55F7B6),
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Aggregate market sentiment is bullish. Commodity volumes are stabilizing across regional hubs.",
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
                    modifier = Modifier.weight(1f).height(52.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00B37E)
                    )
                ) {
                    Text(
                        text = "CALCULATE\nPROFIT",
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                OutlinedButton(
                    onClick = {
                        navController.navigate("digitalSlate")
                    },
                    modifier = Modifier.weight(1f).height(52.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFFFD84D)
                    )
                ) {
                    Text(
                        text = "OPEN PRICE\nBOARD",
                        color = Color(0xFFFFD84D),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
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
                    color = Color(0xFF174B38),
                    shape = RoundedCornerShape(18.dp)
                )
                .padding(22.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "INVENTORY VALUE",
                    color = Color.White.copy(alpha = 0.75f),
                    fontSize = 12.sp,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
                )

                Icon(
                    imageVector = Icons.Default.Inventory,
                    contentDescription = null,
                    tint = Color(0xFFFFD84D)
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "₹${totalProducts * 450}.00",
                color = Color(0xFFFFD84D),
                fontSize = 40.sp,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "+4.2% from yesterday",
                color = Color(0xFF43F5B6),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(22.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color.DarkGray)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .height(5.dp)
                        .background(Color(0xFFFFD84D))
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Storage Capacity : 75% full",
                color = Color.White,
                fontSize = 11.sp
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Commodity Trends",
                    color = Color.White,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "View All",
                    color = Color(0xFF55F7B6),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

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

                    Spacer(modifier = Modifier.height(20.dp))
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

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(34.dp)
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
                text = "+8.1%",
                color = Color(0xFF55F7B6),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        IconButton(
            onClick = onEditClick
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = Color(0xFFFFD84D)
            )
        }

        IconButton(
            onClick = onDeleteClick
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = Color.Red
            )
        }
    }
}

@Composable
fun RegionalHubCard() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF151817)
        )
    ) {

        Column(
            modifier = Modifier.padding(22.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xFF1D3A32),
                                Color(0xFF0D1110)
                            )
                        )
                    )
                    .padding(18.dp),
                contentAlignment = Alignment.BottomStart
            ) {

                Column {

                    Text(
                        text = "REGIONAL HUBS",
                        color = Color(0xFFFFD84D),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black
                    )

                    Text(
                        text = "Sante Local Market",
                        color = Color.White,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Active Trading • High Volume",
                        color = Color.White,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@Composable
fun PremiumGrowthCard() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF151817)
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = null,
                tint = Color(0xFFFFD84D),
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Premium Growth Plan",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Access real-time price predictions and expert market analysis for your inventory.",
                color = Color.White.copy(alpha = 0.82f),
                fontSize = 16.sp,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFD84D)
                )
            ) {

                Text(
                    text = "UPGRADE TO PRO",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun PremiumBottomBar(navController: NavController) {

    NavigationBar(
        containerColor = Color(0xFF191C1B)
    ) {

        NavigationBarItem(
            selected = true,
            onClick = {
                navController.navigate("dashboard")
            },
            icon = {
                Icon(Icons.Default.Home, contentDescription = null)
            },
            label = {
                Text("HOME")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("market")
            },
            icon = {
                Icon(Icons.Default.TrendingUp, contentDescription = null)
            },
            label = {
                Text("PRICES")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("calculator")
            },
            icon = {
                Icon(Icons.Default.Calculate, contentDescription = null)
            },
            label = {
                Text("CALC")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("digitalSlate")
            },
            icon = {
                Icon(Icons.Default.ViewModule, contentDescription = null)
            },
            label = {
                Text("BOARD")
            }
        )
    }
}