package com.example.bhuvana.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bhuvana.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {

    Scaffold(

        bottomBar = {

            BottomNavigationBar(navController)

        },

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "SANTE PRICE INDEX",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
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

                Card(

                    shape = RoundedCornerShape(24.dp),

                    modifier = Modifier.fillMaxWidth(),

                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    )

                ) {

                    Box(

                        modifier = Modifier
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF00C853),
                                        Color(0xFF64DD17)
                                    )
                                )
                            )
                            .padding(24.dp)

                    ) {

                        Column {

                            Text(
                                text = "Today's Market Status",
                                color = Color.White,
                                fontSize = 18.sp
                            )

                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )

                            Text(
                                text = "Prices Trending Up",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp
                            )

                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Icon(
                                    imageVector = Icons.Default.TrendingUp,
                                    contentDescription = null,
                                    tint = Color.White
                                )

                                Spacer(
                                    modifier = Modifier.width(8.dp)
                                )

                                Text(
                                    text = "Overall market increased by 1.4%",
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                Text(
                    text = "Quick Actions",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                DashboardButton(
                    title = "Profit Calculator",
                    icon = Icons.Default.Calculate,
                    onClick = {
                        navController.navigate("calculator")
                    }
                )

                DashboardButton(
                    title = "Market Watch",
                    icon = Icons.Default.Store,
                    onClick = {
                        navController.navigate("market")
                    }
                )

                DashboardButton(
                    title = "Vendor Profile",
                    icon = Icons.Default.Person,
                    onClick = {
                        navController.navigate("profile")
                    }
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                Card(

                    shape = RoundedCornerShape(20.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1C2022)
                    ),

                    modifier = Modifier.fillMaxWidth()

                ) {

                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {

                        Text(
                            text = "Top Selling Product",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Text(
                            text = "Tomato",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text = "₹40/kg",
                            color = Color(0xFFFFC107),
                            fontSize = 22.sp
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )
            }
        }
    }
}

@Composable
fun DashboardButton(

    title: String,
    icon: ImageVector,
    onClick: () -> Unit

) {

    Button(

        onClick = onClick,

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),

        shape = RoundedCornerShape(18.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF1C2022)
        )

    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFFFFC107)
        )

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        Text(
            text = title,
            color = Color.White,
            fontSize = 18.sp
        )
    }
}