package com.example.bhuvana.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bhuvana.models.ProductItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.delay

@Composable
fun DigitalSlateScreen() {

    val db = FirebaseFirestore.getInstance()

    var productList by remember {
        mutableStateOf(listOf<ProductItem>())
    }

    var visible by remember {
        mutableStateOf(false)
    }

    var listener: ListenerRegistration? = null

    val infiniteTransition = rememberInfiniteTransition(
        label = "liveBlink"
    )

    val blinkAlpha by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(650),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blinkAlpha"
    )

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF07110D),
                        Color(0xFF0B1712),
                        Color.Black
                    )
                )
            )
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    width = 3.dp,
                    color = Color(0xFFFFD84D),
                    shape = RoundedCornerShape(26.dp)
                )
                .padding(16.dp)
        ) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )
            ) {

                Column(
                    modifier = Modifier
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    Color(0xFF1B5E20),
                                    Color(0xFF00B37E),
                                    Color(0xFF1B5E20)
                                )
                            )
                        )
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Icon(
                            imageVector = Icons.Default.Store,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(38.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "SANTE PRICE INDEX",
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "TODAY'S FAIR PRICE BOARD",
                        color = Color(0xFFFFD84D),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "● LIVE FIREBASE PRICE UPDATE",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.alpha(blinkAlpha)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFFFD84D),
                        shape = RoundedCornerShape(14.dp)
                    )
                    .padding(horizontal = 18.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "PRODUCT",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                )

                Text(
                    text = "FAIR PRICE",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (productList.isEmpty()) {

                EmptyPriceBoard()

            } else {

                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {

                    items(productList) { product ->

                        AnimatedVisibility(
                            visible = visible,
                            enter = fadeIn() + slideInVertically()
                        ) {

                            GrandPriceRow(
                                productName = product.name,
                                price = product.price
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Divider(
                color = Color(0xFFFFD84D),
                thickness = 2.dp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Verified,
                    contentDescription = null,
                    tint = Color(0xFF55F7B6)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "FAIR PRICE • LIVE MARKET • TRUSTED VENDOR",
                    color = Color(0xFF55F7B6),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun GrandPriceRow(
    productName: String,
    price: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
        shape = RoundedCornerShape(18.dp),
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
                    shape = RoundedCornerShape(18.dp)
                )
                .padding(horizontal = 18.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = productName.uppercase(),
                color = Color.White,
                fontSize = 29.sp,
                fontWeight = FontWeight.Black
            )

            Text(
                text = price,
                color = Color(0xFFFFD84D),
                fontSize = 30.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}

@Composable
fun EmptyPriceBoard() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "NO LIVE PRODUCTS",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "ADD PRICES FROM ADMIN SCREEN",
            color = Color(0xFFFFD84D),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}