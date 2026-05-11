package com.example.bhuvana.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfitCalculatorScreen() {

    var buyingPrice by remember { mutableStateOf("") }
    var sellingPrice by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var result by remember { mutableStateOf(0.0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Profit Calculator",
            fontSize = 26.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = buyingPrice,
            onValueChange = { buyingPrice = it },
            label = { Text("Buying Price") }
        )

        OutlinedTextField(
            value = sellingPrice,
            onValueChange = { sellingPrice = it },
            label = { Text("Selling Price") }
        )

        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantity") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {

            val buy = buyingPrice.toDoubleOrNull() ?: 0.0
            val sell = sellingPrice.toDoubleOrNull() ?: 0.0
            val qty = quantity.toDoubleOrNull() ?: 0.0

            result = (sell - buy) * qty

        }) {
            Text("Calculate")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Profit = ₹$result",
            fontSize = 22.sp
        )
    }
}