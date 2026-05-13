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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.WifiTethering
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DigitalSlateScreen() {

    val db = FirebaseFirestore.getInstance()

    var productList by remember {
        mutableStateOf(listOf<ProductItem>())
    }

    var currentTime by remember {
        mutableStateOf(getCurrentBoardTime())
    }

    var visible by remember {
        mutableStateOf(false)
    }

    var listener: ListenerRegistration? = null

    val blinkAnimation = rememberInfiniteTransition(label = "blinkAnimation")

    val liveAlpha by blinkAnimation.animateFloat(
        initialValue = 0.45f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(750),
            repeatMode = RepeatMode.Reverse
        ),
        label = "liveAlpha"
    )

    LaunchedEffect(Unit) {
        visible = true

        while (true) {
            currentTime = getCurrentBoardTime()
            delay(1000)
        }
    }

    LaunchedEffect(Unit) {
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
            .background(Color.Black)
            .padding(14.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Black,
                            Color(0xFF07110D),
                            Color.Black
                        )
                    )
                )
        ) {

            PremiumBoardHeader(
                liveAlpha = liveAlpha
            )

            Spacer(modifier = Modifier.height(16.dp))

            PremiumBoardStatus(
                time = currentTime,
                productCount = productList.size
            )

            Spacer(modifier = Modifier.height(18.dp))

            Divider(
                color = Color(0xFF55F7B6),
                thickness = 2.dp
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xFF55F7B6),
                        RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "PRODUCT",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black
                )

                Text(
                    text = "PRICE",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (productList.isEmpty()) {

                PremiumEmptyBoard()

            } else {

                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {

                    items(productList) { product ->

                        AnimatedVisibility(
                            visible = visible,
                            enter = fadeIn() + slideInVertically()
                        ) {

                            PremiumSlateRow(
                                productName = product.name,
                                price = product.price
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            PremiumBoardTicker()
        }
    }
}

@Composable
fun PremiumBoardHeader(
    liveAlpha: Float
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {

        Row(
            modifier = Modifier
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color(0xFF07110D),
                            Color(0xFF12352B),
                            Color(0xFF07110D)
                        )
                    )
                )
                .border(
                    width = 1.dp,
                    color = Color(0xFF25332E),
                    shape = RoundedCornerShape(18.dp)
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFF12352B), CircleShape),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Store,
                    contentDescription = null,
                    tint = Color(0xFF55F7B6),
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {

                Text(
                    text = "SANTE-PRICE",
                    color = Color.White,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )

                Text(
                    text = "DIGITAL MARKET BOARD",
                    color = Color(0xFF55F7B6),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.4.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .background(
                        Color.Black,
                        RoundedCornerShape(50.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color(0xFF55F7B6),
                        shape = RoundedCornerShape(50.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 6.dp)
                    .alpha(liveAlpha),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .background(Color(0xFF55F7B6), CircleShape)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "LIVE",
                    color = Color(0xFF55F7B6),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }
    }
}

@Composable
fun PremiumBoardStatus(
    time: String,
    productCount: Int
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
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
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "MARKET OPEN",
                    color = Color(0xFFFFD84D),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.2.sp
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = time,
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Black
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.WifiTethering,
                        contentDescription = null,
                        tint = Color(0xFF55F7B6),
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = "SYNCED",
                        color = Color(0xFF55F7B6),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$productCount ITEMS",
                    color = Color(0xFFFFD84D),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }
    }
}

@Composable
fun PremiumSlateRow(
    productName: String,
    price: String
) {

    val priceOnly = extractBoardPrice(price)
    val unitOnly = extractBoardUnit(price)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
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
                    shape = RoundedCornerShape(16.dp)
                )
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color(0xFF151817),
                            Color(0xFF0E211A)
                        )
                    )
                )
                .padding(horizontal = 18.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = productName.uppercase(),
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Live market price",
                    color = Color.White.copy(alpha = 0.60f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                verticalAlignment = Alignment.Bottom
            ) {

                Text(
                    text = priceOnly,
                    color = Color(0xFFFFD84D),
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = unitOnly,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
            }
        }
    }
}

@Composable
fun PremiumEmptyBoard() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 70.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "NO LIVE PRICES",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Black
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "ADD PRODUCT PRICES FIRST",
            color = Color(0xFFFFD84D),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun PremiumBoardTicker() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFF55F7B6),
                RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 14.dp, vertical = 12.dp)
    ) {

        Text(
            text = "LIVE PRICES UPDATED AUTOMATICALLY FROM FIREBASE",
            color = Color.Black,
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

fun getCurrentBoardTime(): String {
    val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return format.format(Date()).uppercase()
}

fun extractBoardPrice(priceText: String): String {

    val cleanText = priceText.replace("[^0-9.]".toRegex(), "")

    return if (cleanText.isBlank()) {
        priceText
    } else {
        cleanText
    }
}

fun extractBoardUnit(priceText: String): String {

    return when {
        priceText.contains("kg", ignoreCase = true) -> "/KG"
        priceText.contains("pc", ignoreCase = true) -> "/PC"
        priceText.contains("box", ignoreCase = true) -> "/BOX"
        priceText.contains("bag", ignoreCase = true) -> "/BAG"
        else -> "/KG"
    }
}