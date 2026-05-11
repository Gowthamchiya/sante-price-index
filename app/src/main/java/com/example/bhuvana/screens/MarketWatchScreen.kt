package com.example.bhuvana.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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

    var visible by remember {
        mutableStateOf(false)
    }

    var listener: ListenerRegistration? = null

    LaunchedEffect(Unit) {

        delay(200)
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
                            Color(0xFF07110D)
                        )
                    )
                )
                .padding(16.dp)
        ) {

            item {

                Text(
                    text = "PRICE WATCH",
                    color = Color(0xFF55F7B6),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Live mandi prices synced with Firebase",
                    color = Color.White.copy(alpha = 0.75f),
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                MarketWatchTopCard(
                    totalProducts = productList.size
                )

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = "Live Market Products",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            if (productList.isEmpty()) {

                item {

                    EmptyMarketState()
                }

            } else {

                items(productList) { product ->

                    AnimatedVisibility(
                        visible = visible,
                        enter = fadeIn() + slideInVertically()
                    ) {

                        MarketProductCard(
                            productName = product.name,
                            price = product.price
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun MarketWatchTopCard(
    totalProducts: Int
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
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
                    shape = RoundedCornerShape(22.dp)
                )
                .padding(22.dp)
        ) {

            Text(
                text = "MARKET INTELLIGENCE",
                color = Color.White.copy(alpha = 0.75f),
                fontSize = 12.sp,
                letterSpacing = 2.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "$totalProducts Products Live",
                color = Color(0xFF55F7B6),
                fontSize = 34.sp,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Prices are updated instantly from Firebase and synced with Customer Price Board.",
                color = Color.White.copy(alpha = 0.82f),
                fontSize = 15.sp,
                lineHeight = 21.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.TrendingUp,
                    contentDescription = null,
                    tint = Color(0xFF55F7B6)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Market status: Active and live",
                    color = Color(0xFF55F7B6),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun MarketProductCard(
    productName: String,
    price: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
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
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.Eco,
                contentDescription = null,
                tint = Color(0xFF55F7B6),
                modifier = Modifier.size(42.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = productName,
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Live Firebase Price",
                    color = Color.White.copy(alpha = 0.65f),
                    fontSize = 13.sp
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {

                Text(
                    text = price,
                    color = Color(0xFFFFD84D),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "+8.1%",
                    color = Color(0xFF55F7B6),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun EmptyMarketState() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF151817)
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(26.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = Icons.Default.Eco,
                contentDescription = null,
                tint = Color(0xFFFFD84D),
                modifier = Modifier.size(58.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "No live products",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Add products from the Add Price screen",
                color = Color.Gray,
                fontSize = 15.sp
            )
        }
    }
}