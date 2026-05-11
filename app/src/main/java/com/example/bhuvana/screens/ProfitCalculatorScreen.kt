package com.example.bhuvana.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfitCalculatorScreen() {

    var buyingPrice by remember { mutableStateOf("") }
    var transportCost by remember { mutableStateOf("") }
    var profitMargin by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }

    var rrp by remember { mutableStateOf(0.0) }
    var netProfit by remember { mutableStateOf(0.0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Profit Calculator",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Buying Price
        OutlinedTextField(
            value = buyingPrice,
            onValueChange = { buyingPrice = it },
            label = { Text("Buying Price per kg") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Transport Cost
        OutlinedTextField(
            value = transportCost,
            onValueChange = { transportCost = it },
            label = { Text("Transport Cost per kg") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Profit Margin
        OutlinedTextField(
            value = profitMargin,
            onValueChange = { profitMargin = it },
            label = { Text("Profit Margin per kg") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Quantity
        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantity (kg)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Calculate Button
        Button(
            onClick = {
                val buy = buyingPrice.toDoubleOrNull() ?: 0.0
                val transport = transportCost.toDoubleOrNull() ?: 0.0
                val profit = profitMargin.toDoubleOrNull() ?: 0.0
                val qty = quantity.toDoubleOrNull() ?: 0.0

                // Recommended Retail Price per kg
                rrp = buy + transport + profit

                // Net Profit = (RRP - Buying Price - Transport) * Quantity
                netProfit = (rrp - buy - transport) * qty

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Output
        Text(
            text = "Recommended Retail Price (RRP) = ₹$rrp per kg",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Net Profit = ₹$netProfit",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}