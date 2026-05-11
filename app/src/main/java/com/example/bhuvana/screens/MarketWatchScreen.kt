package com.example.bhuvana.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MarketWatchScreen() {

    val marketItems = listOf(
        "Tomato - ₹40/kg",
        "Onion - ₹32/kg",
        "Potato - ₹28/kg",
        "Carrot - ₹45/kg",
        "Beans - ₹60/kg"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        items(marketItems) { item ->

            Card(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1C2022)
                )

            ) {

                Text(
                    text = item,
                    modifier = Modifier.padding(20.dp),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}