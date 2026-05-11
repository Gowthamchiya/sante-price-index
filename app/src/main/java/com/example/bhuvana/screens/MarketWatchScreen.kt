package com.example.bhuvana.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bhuvana.components.BottomNavigationBar

@Composable
fun MarketWatchScreen(navController: NavController) {
    Scaffold(bottomBar = { BottomNavigationBar(navController) }) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF101416))
                .padding(padding)
                .padding(16.dp)
        ) {
            item {
                Text("Market Watch", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(20.dp))

                MarketCard("Tomato", "₹40/kg", trendUp = true, gradient1 = Color(0xFF00C853), gradient2 = Color(0xFF64DD17))
                MarketCard("Onion", "₹32/kg", trendUp = false, gradient1 = Color(0xFFFFC107), gradient2 = Color(0xFFFF9800))
                MarketCard("Potato", "₹28/kg", trendUp = true, gradient1 = Color(0xFF00B0FF), gradient2 = Color(0xFF2962FF))
            }
        }
    }
}

@Composable
fun MarketCard(productName: String, price: String, trendUp: Boolean, gradient1: Color, gradient2: Color) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .background(Brush.horizontalGradient(colors = listOf(gradient1, gradient2)))
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Eco, contentDescription = null, tint = Color.White, modifier = Modifier.size(50.dp))
            Spacer(modifier = Modifier.width(20.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(productName, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(price, color = Color.White, fontSize = 20.sp)
            }
            Icon(if (trendUp) Icons.Default.TrendingUp else Icons.Default.TrendingDown, contentDescription = null, tint = Color.White, modifier = Modifier.size(36.dp))
        }
    }
}