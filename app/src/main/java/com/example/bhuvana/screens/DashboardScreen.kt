package com.example.bhuvana.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bhuvana.models.ProductItem
import com.example.bhuvana.components.BottomNavigationBar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {

    val db = FirebaseFirestore.getInstance()
    var productList by remember { mutableStateOf(listOf<ProductItem>()) }
    var listener: ListenerRegistration? = null

    // Firestore listener
    LaunchedEffect(Unit) {
        listener = db.collection("products")
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    productList = snapshot.documents.map {
                        ProductItem(
                            id = it.id,
                            name = it.getString("name") ?: "",
                            price = it.getString("price") ?: ""
                        )
                    }
                }
            }
    }

    DisposableEffect(Unit) { onDispose { listener?.remove() } }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("addProduct") }) {
                Icon(Icons.Default.Add, contentDescription = "Add Product")
            }
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF101416))
                .padding(16.dp)
        ) {

            items(productList) { product ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1C2022))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column {
                            Text(product.name, color = Color.White)
                            Text(product.price, color = Color.Gray)
                        }

                        Row {
                            IconButton(onClick = {
                                navController.navigate("editProduct/${product.id}")
                            }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.Yellow)
                            }

                            IconButton(onClick = {
                                db.collection("products").document(product.id)
                                    .delete()
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                            }
                        }
                    }
                }
            }
        }
    }
}