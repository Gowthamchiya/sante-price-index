package com.example.bhuvana.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bhuvana.components.BottomNavigationBar

@Composable
fun MarketWatchScreen(navController: NavController) {

    Scaffold(

        bottomBar = {

            BottomNavigationBar(navController)

        }

    ) { paddingValues ->

        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF101416))
                .padding(paddingValues)
                .padding(16.dp)

        ) {

            item {

                Text(
                    text = "Market Watch",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                MarketCard(
                    productName = "Tomato",
                    price = "₹40/kg",
                    trendUp = true,
                    gradient1 = Color(0xFFFF5252),
                    gradient2 = Color(0xFFFF1744)
                )

                MarketCard(
                    productName = "Onion",
                    price = "₹32/kg",
                    trendUp = false,
                    gradient1 = Color(0xFF7E57C2),
                    gradient2 = Color(0xFF5E35B1)
                )

                MarketCard(
                    productName = "Potato",
                    price = "₹28/kg",
                    trendUp = true,
                    gradient1 = Color(0xFFFFC107),
                    gradient2 = Color(0xFFFF9800)
                )
            }
        }
    }
}

@Composable
fun MarketCard(

    productName: String,
    price: String,
    trendUp: Boolean,
    gradient1: Color,
    gradient2: Color

) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )

    ) {

        Row(

            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            gradient1,
                            gradient2
                        )
                    )
                )
                .fillMaxWidth()
                .padding(20.dp),

            verticalAlignment = Alignment.CenterVertically

        ) {

            Icon(
                imageVector = Icons.Default.Eco,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(50.dp)
            )

            Spacer(
                modifier = Modifier.width(20.dp)
            )

            Column(

                modifier = Modifier.weight(1f)

            ) {

                Text(
                    text = productName,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = price,
                    color = Color.White,
                    fontSize = 20.sp
                )
            }

            Icon(

                imageVector =
                    if (trendUp)
                        Icons.Default.TrendingUp
                    else
                        Icons.Default.TrendingDown,

                contentDescription = null,

                tint = Color.White,

                modifier = Modifier.size(36.dp)

            )
        }
    }
}