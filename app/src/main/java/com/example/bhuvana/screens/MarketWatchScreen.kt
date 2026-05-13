package com.example.bhuvana.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.WifiTethering
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bhuvana.components.BottomNavigationBar
import com.example.bhuvana.models.ProductItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.delay

@Composable
fun MarketWatchScreen(navController: NavController) {

    val db = FirebaseFirestore.getInstance()

    var productList by remember {
        mutableStateOf(listOf<ProductItem>())
    }

    var searchText by remember {
        mutableStateOf("")
    }

    var visible by remember {
        mutableStateOf(false)
    }

    var listener: ListenerRegistration? = null

    val filteredProducts = productList.filter {
        it.name.contains(searchText, ignoreCase = true)
    }

    val highProducts = productList.count {
        extractPriceValue(it.price) >= 50.0
    }

    val lowProducts = productList.count {
        extractPriceValue(it.price) < 50.0
    }

    LaunchedEffect(Unit) {
        delay(150)
        visible = true

        listener = db.collection("products")
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    return@addSnapshotListener
                }

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

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        },
        containerColor = Color(0xFF07110D)
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF07110D),
                            Color(0xFF0B1712),
                            Color(0xFF050806)
                        )
                    )
                )
                .padding(16.dp)
        ) {

            item {

                CleanMarketHeader()

                Spacer(modifier = Modifier.height(16.dp))

                CleanMarketSummaryCard(
                    totalProducts = productList.size,
                    highProducts = highProducts,
                    lowProducts = lowProducts
                )

                Spacer(modifier = Modifier.height(16.dp))

                CleanMarketSearchBox(
                    searchText = searchText,
                    onSearchChange = {
                        searchText = it
                    }
                )

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Products",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black
                    )

                    Text(
                        text = "${filteredProducts.size} live",
                        color = Color(0xFF55F7B6),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            if (filteredProducts.isEmpty()) {

                item {
                    CleanEmptyMarketState()
                }

            } else {

                items(filteredProducts) { product ->

                    AnimatedVisibility(
                        visible = visible,
                        enter = fadeIn() + slideInVertically()
                    ) {

                        CleanProductTrendCard(
                            product = product
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            item {

                Spacer(modifier = Modifier.height(6.dp))

                CleanSentimentCard(
                    highProducts = highProducts,
                    lowProducts = lowProducts
                )

                Spacer(modifier = Modifier.height(14.dp))
            }
        }
    }
}

@Composable
fun CleanMarketHeader() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {

            Text(
                text = "Price Watch",
                color = Color(0xFF55F7B6),
                fontSize = 32.sp,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Live market price analysis",
                color = Color.White.copy(alpha = 0.70f),
                fontSize = 15.sp
            )
        }

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFF12352B)),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = Icons.Default.WifiTethering,
                contentDescription = null,
                tint = Color(0xFF55F7B6)
            )
        }
    }
}

@Composable
fun CleanMarketSummaryCard(
    totalProducts: Int,
    highProducts: Int,
    lowProducts: Int
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {

        Column(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xFF151817),
                            Color(0xFF0E211A)
                        )
                    )
                )
                .border(
                    width = 1.dp,
                    color = Color(0xFF25332E),
                    shape = RoundedCornerShape(26.dp)
                )
                .padding(20.dp)
        ) {

            Text(
                text = "LIVE MARKET STATUS",
                color = Color(0xFF55F7B6),
                fontSize = 12.sp,
                letterSpacing = 1.6.sp,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "$totalProducts Products Synced",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Prices are updated from Firebase and classified using the ₹50 rule.",
                color = Color.White.copy(alpha = 0.72f),
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                CleanMiniStatCard(
                    title = "UP",
                    value = highProducts.toString(),
                    color = Color(0xFFFFD84D),
                    modifier = Modifier.weight(1f)
                )

                CleanMiniStatCard(
                    title = "DOWN",
                    value = lowProducts.toString(),
                    color = Color(0xFF55F7B6),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun CleanMiniStatCard(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .background(
                color = Color(0xFF101513),
                shape = RoundedCornerShape(18.dp)
            )
            .border(
                width = 1.dp,
                color = Color(0xFF25332E),
                shape = RoundedCornerShape(18.dp)
            )
            .padding(14.dp)
    ) {

        Text(
            text = title,
            color = Color.White.copy(alpha = 0.62f),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = value,
            color = color,
            fontSize = 26.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
fun CleanMarketSearchBox(
    searchText: String,
    onSearchChange: (String) -> Unit
) {

    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text("Search product")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(22.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedBorderColor = Color(0xFF55F7B6),
            unfocusedBorderColor = Color(0xFF25332E),
            focusedLabelColor = Color(0xFF55F7B6),
            unfocusedLabelColor = Color.Gray,
            cursorColor = Color(0xFF55F7B6)
        )
    )
}

@Composable
fun CleanProductTrendCard(
    product: ProductItem
) {

    val priceValue = extractPriceValue(product.price)
    val isHighValue = priceValue >= 50.0

    val trendColor =
        if (isHighValue)
            Color(0xFFFFD84D)
        else
            Color(0xFF55F7B6)

    val trendIcon =
        if (isHighValue)
            Icons.Default.TrendingUp
        else
            Icons.Default.TrendingDown

    val trendTitle =
        if (isHighValue)
            "UP"
        else
            "DOWN"

    val shortDescription =
        if (isHighValue)
            "Above ₹50. High value item, keep margin carefully."
        else
            "Below ₹50. Low value item, keep price attractive."

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF151817)
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color(0xFF25332E),
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF12352B)),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Agriculture,
                    contentDescription = null,
                    tint = Color(0xFF55F7B6),
                    modifier = Modifier.size(27.dp)
                )
            }

            Spacer(modifier = Modifier.width(15.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = product.name,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = shortDescription,
                    color = Color.White.copy(alpha = 0.65f),
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                horizontalAlignment = Alignment.End
            ) {

                Text(
                    text = product.price,
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .background(
                            color = Color(0xFF101513),
                            shape = RoundedCornerShape(50.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = trendColor.copy(alpha = 0.55f),
                            shape = RoundedCornerShape(50.dp)
                        )
                        .padding(horizontal = 9.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = trendIcon,
                        contentDescription = null,
                        tint = trendColor,
                        modifier = Modifier.size(14.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = trendTitle,
                        color = trendColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
    }
}

@Composable
fun CleanSentimentCard(
    highProducts: Int,
    lowProducts: Int
) {

    val total = highProducts + lowProducts

    val highPercent =
        if (total == 0) 0f else highProducts.toFloat() / total.toFloat()

    val lowPercent =
        if (total == 0) 0f else lowProducts.toFloat() / total.toFloat()

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF151817)
        )
    ) {

        Column(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFF25332E),
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(20.dp)
        ) {

            Text(
                text = "Market Sentiment",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            CleanSentimentBar(
                title = "UP items",
                percent = highPercent,
                color = Color(0xFFFFD84D)
            )

            Spacer(modifier = Modifier.height(14.dp))

            CleanSentimentBar(
                title = "DOWN items",
                percent = lowPercent,
                color = Color(0xFF55F7B6)
            )
        }
    }
}

@Composable
fun CleanSentimentBar(
    title: String,
    percent: Float,
    color: Color
) {

    Column {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = title,
                color = Color.White.copy(alpha = 0.75f),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "${(percent * 100).toInt()}%",
                color = color,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(7.dp))

        LinearProgressIndicator(
            progress = percent,
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(50.dp)),
            color = color,
            trackColor = Color(0xFF2D3330)
        )
    }
}

@Composable
fun CleanEmptyMarketState() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF151817)
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color(0xFF25332E),
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(Color(0xFF12352B), CircleShape),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Agriculture,
                    contentDescription = null,
                    tint = Color(0xFFFFD84D),
                    modifier = Modifier.size(38.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "No live products",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Add products from the Add Price screen",
                color = Color.White.copy(alpha = 0.65f),
                fontSize = 15.sp
            )
        }
    }
}

fun extractPriceValue(priceText: String): Double {

    val cleanText =
        priceText.replace("[^0-9.]".toRegex(), "")

    return cleanText.toDoubleOrNull() ?: 0.0
}