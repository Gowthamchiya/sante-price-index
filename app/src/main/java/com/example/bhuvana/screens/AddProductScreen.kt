package com.example.bhuvana.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bhuvana.models.ProductItem
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun AddProductScreen() {

    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    val db = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Add New Product", style = MaterialTheme.typography.headlineMedium)

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
                if (name.isNotBlank() && price.isNotBlank()) {
                    val product = ProductItem(name = name, price = price)
                    db.collection("products")
                        .add(product)
                        .addOnSuccessListener {
                            name = ""
                            price = ""
                        }
                        .addOnFailureListener { e ->
                            e.printStackTrace()
                        }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Product")
        }
    }
}