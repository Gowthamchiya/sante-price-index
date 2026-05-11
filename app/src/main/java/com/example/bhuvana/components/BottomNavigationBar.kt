package com.example.bhuvana.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {

    val currentRoute = navController
        .currentBackStackEntryAsState()
        .value?.destination?.route

    NavigationBar(
        containerColor = Color(0xFF1C2022)
    ) {

        NavigationBarItem(
            selected = currentRoute == "dashboard",
            onClick = {
                navController.navigate("dashboard") {
                    popUpTo(0)
                }
            },
            icon = {
                Icon(Icons.Default.Home, contentDescription = null)
            },
            label = { Text("Home") }
        )

        NavigationBarItem(
            selected = currentRoute == "calculator",
            onClick = {
                navController.navigate("calculator") {
                    popUpTo(0)
                }
            },
            icon = {
                Icon(Icons.Default.Calculate, contentDescription = null)
            },
            label = { Text("Calc") }
        )

        NavigationBarItem(
            selected = currentRoute == "market",
            onClick = {
                navController.navigate("market") {
                    popUpTo(0)
                }
            },
            icon = {
                Icon(Icons.Default.Store, contentDescription = null)
            },
            label = { Text("Market") }
        )

        NavigationBarItem(
            selected = currentRoute == "profile",
            onClick = {
                navController.navigate("profile") {
                    popUpTo(0)
                }
            },
            icon = {
                Icon(Icons.Default.Person, contentDescription = null)
            },
            label = { Text("Profile") }
        )
    }
}