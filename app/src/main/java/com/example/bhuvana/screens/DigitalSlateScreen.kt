package com.example.bhuvana.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DigitalSlateScreen() {

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(20.dp),

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text(
            text = "DIGITAL PRICE BOARD",
            color = Color.Green,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Tomato - ₹40/kg",
            color = Color.White,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Onion - ₹32/kg",
            color = Color.White,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Potato - ₹28/kg",
            color = Color.White,
            fontSize = 24.sp
        )
    }
}