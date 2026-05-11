package com.example.bhuvana.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

@Composable
fun EditProductScreen(navController: NavController, backStackEntry: NavBackStackEntry) {

    val productId = backStackEntry.arguments?.getString("id") ?: return
    val db = FirebaseFirestore.getInstance()

    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    // Load product data
    LaunchedEffect(productId) {
        db.collection("products").document(productId)
            .get()
            .addOnSuccessListener { doc ->
                name = doc.getString("name") ?: ""
                price = doc.getString("price") ?: ""
            }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Edit Product", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Product Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                db.collection("products").document(productId)
                    .update(mapOf("name" to name, "price" to price))
                    .addOnSuccessListener { navController.popBackStack() }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update Product")
        }
    }
}