package com.example.bhuvana.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bhuvana.components.BottomNavigationBar

@Composable
fun ProfitCalculatorScreen(navController: NavController) {

    var mandiPrice by remember { mutableStateOf("") }
    var transportCost by remember { mutableStateOf("") }
    var wasteCost by remember { mutableStateOf("") }
    var profitMargin by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }

    var sellingPrice by remember { mutableStateOf(0.0) }
    var grossSales by remember { mutableStateOf(0.0) }
    var netProfit by remember { mutableStateOf(0.0) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        },
        containerColor = Color(0xFF07110D)
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF07110D))
                .padding(20.dp)
        ) {

            Text(
                text = "Profit Calculator",
                color = Color(0xFF55F7B6),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            SimpleInput(
                value = mandiPrice,
                label = "Mandi Price per kg",
                onChange = { mandiPrice = it }
            )

            SimpleInput(
                value = transportCost,
                label = "Transport Cost per kg",
                onChange = { transportCost = it }
            )

            SimpleInput(
                value = wasteCost,
                label = "Waste Cost per kg",
                onChange = { wasteCost = it }
            )

            SimpleInput(
                value = profitMargin,
                label = "Profit per kg",
                onChange = { profitMargin = it }
            )

            SimpleInput(
                value = quantity,
                label = "Quantity kg",
                onChange = { quantity = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val mandi = mandiPrice.toDoubleOrNull() ?: 0.0
                    val transport = transportCost.toDoubleOrNull() ?: 0.0
                    val waste = wasteCost.toDoubleOrNull() ?: 0.0
                    val profit = profitMargin.toDoubleOrNull() ?: 0.0
                    val qty = quantity.toDoubleOrNull() ?: 0.0

                    sellingPrice = mandi + transport + waste + profit
                    grossSales = sellingPrice * qty
                    netProfit = profit * qty
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00B37E)
                )
            ) {
                Text(
                    text = "Calculate",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Fair Selling Price: ₹${sellingPrice.toInt()} / kg",
                color = Color(0xFFFFD84D),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Gross Sales: ₹${grossSales.toInt()}",
                color = Color.White,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Net Profit: ₹${netProfit.toInt()}",
                color = Color(0xFF55F7B6),
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Formula: Mandi + Transport + Waste + Profit",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun SimpleInput(
    value: String,
    label: String,
    onChange: (String) -> Unit
) {

    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = {
            Text(label)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedBorderColor = Color(0xFF55F7B6),
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = Color(0xFF55F7B6),
            unfocusedLabelColor = Color.Gray,
            cursorColor = Color(0xFF55F7B6)
        )
    )
}