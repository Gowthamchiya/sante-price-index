package com.example.bhuvana.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(navController: NavController) {

    NavigationBar(
        containerColor = Color(0xFF151817)
    ) {

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("dashboard")
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Dashboard,
                    contentDescription = null
                )
            },
            label = {
                Text("Home")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF55F7B6),
                selectedTextColor = Color(0xFF55F7B6),
                unselectedIconColor = Color.White,
                unselectedTextColor = Color.White,
                indicatorColor = Color(0xFF12352B)
            )
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("market")
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.TrendingUp,
                    contentDescription = null
                )
            },
            label = {
                Text("Prices")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF55F7B6),
                selectedTextColor = Color(0xFF55F7B6),
                unselectedIconColor = Color.White,
                unselectedTextColor = Color.White,
                indicatorColor = Color(0xFF12352B)
            )
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("calculator")
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Calculate,
                    contentDescription = null
                )
            },
            label = {
                Text("Calc")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF55F7B6),
                selectedTextColor = Color(0xFF55F7B6),
                unselectedIconColor = Color.White,
                unselectedTextColor = Color.White,
                indicatorColor = Color(0xFF12352B)
            )
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("digitalSlate")
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Store,
                    contentDescription = null
                )
            },
            label = {
                Text("Board")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF55F7B6),
                selectedTextColor = Color(0xFF55F7B6),
                unselectedIconColor = Color.White,
                unselectedTextColor = Color.White,
                indicatorColor = Color(0xFF12352B)
            )
        )
    }
}