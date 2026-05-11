package com.example.bhuvana.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(navController: NavController) {

    NavigationBar(
        containerColor = Color(0xFF1C2022)
    ) {

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("dashboard") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("calculator") },
            icon = { Icon(Icons.Default.Calculate, contentDescription = "Calculator") },
            label = { Text("Calculator") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("market") },
            icon = { Icon(Icons.Default.Store, contentDescription = "Market") },
            label = { Text("Market") }
        )
    }
}