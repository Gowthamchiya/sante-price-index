package com.example.bhuvana.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bhuvana.components.BottomNavigationBar

// DATA MODEL
data class ProductItem(
    val name: String,
    val price: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {

    var searchText by remember { mutableStateOf("") }

    val productList = remember {
        listOf(
            ProductItem("Tomato", "₹40/kg"),
            ProductItem("Onion", "₹32/kg"),
            ProductItem("Potato", "₹28/kg"),
            ProductItem("Carrot", "₹55/kg"),
            ProductItem("Beans", "₹60/kg"),
            ProductItem("Cabbage", "₹35/kg"),
            ProductItem("Brinjal", "₹45/kg")
        )
    }

    val filteredProducts = productList.filter {
        it.name.contains(searchText, ignoreCase = true)
    }

    Scaffold(

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "SANTE PRICE INDEX",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("calculator") },
                containerColor = Color(0xFF00C853)
            ) {
                Icon(Icons.Default.Calculate, contentDescription = null, tint = Color.White)
            }
        },

        bottomBar = {
            BottomNavigationBar(navController)
        }

    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0E1416))
                .padding(padding)
                .padding(16.dp)
        ) {

            // HEADER
            item {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(Color(0xFF1C1F22))
                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            "Market Overview",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            "Prices Trending Up 🚀",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(10.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.TrendingUp,
                                contentDescription = null,
                                tint = Color(0xFF00C853)
                            )

                            Spacer(Modifier.width(8.dp))

                            Text(
                                "Market increased by 1.4%",
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                // SEARCH
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Search products...") },
                    leadingIcon = { Icon(Icons.Default.Search, null) },
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF1C1F22),
                        unfocusedContainerColor = Color(0xFF1C1F22),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )

                Spacer(Modifier.height(20.dp))

                Text(
                    "Quick Actions",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(10.dp))

                DashboardButton("Profit Calculator", Icons.Default.Calculate) {
                    navController.navigate("calculator")
                }

                DashboardButton("Market Watch", Icons.Default.Store) {
                    navController.navigate("market")
                }

                DashboardButton("Vendor Profile", Icons.Default.Person) {
                    navController.navigate("profile")
                }

                Spacer(Modifier.height(20.dp))

                Text(
                    "Live Prices",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(10.dp))
            }

            // PRODUCTS
            items(filteredProducts) { product ->
                ProductCard(product)
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun DashboardButton(title: String, icon: ImageVector, onClick: () -> Unit) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF1C1F22)
        )
    ) {

        Icon(icon, null, tint = Color(0xFFFFC107))

        Spacer(Modifier.width(10.dp))

        Text(title, color = Color.White)
    }
}

@Composable
fun ProductCard(product: ProductItem) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color(0xFF1C1F22))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column {

                Text(
                    product.name,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "Updated today",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            Text(
                product.price,
                color = Color(0xFFFFC107),
                fontWeight = FontWeight.Bold
            )
        }
    }
}