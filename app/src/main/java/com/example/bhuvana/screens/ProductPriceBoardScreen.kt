package com.example.bhuvana.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProductPriceBoardScreen() {

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text(
            text = "LIVE MARKET BOARD",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Tomato - ₹40/kg")
        Text(text = "Onion - ₹32/kg")
        Text(text = "Potato - ₹28/kg")
        Text(text = "Carrot - ₹45/kg")
    }
}