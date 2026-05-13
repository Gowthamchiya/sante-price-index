package com.example.bhuvana.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {

    val currentRoute =
        navController.currentBackStackEntryAsState()
            .value
            ?.destination
            ?.route

    NavigationBar(
        containerColor = Color(0xFF151817)
    ) {

        NavigationBarItem(
            selected = currentRoute == "dashboard",
            onClick = {
                navController.navigate("dashboard") {
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Dashboard,
                    contentDescription = "Home"
                )
            },
            label = {
                Text(
                    text = "Home",
                    fontWeight = FontWeight.Bold
                )
            },
            colors = bottomNavColors()
        )

        NavigationBarItem(
            selected = currentRoute == "market",
            onClick = {
                navController.navigate("market") {
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.TrendingUp,
                    contentDescription = "Prices"
                )
            },
            label = {
                Text(
                    text = "Prices",
                    fontWeight = FontWeight.Bold
                )
            },
            colors = bottomNavColors()
        )

        NavigationBarItem(
            selected = currentRoute == "calculator",
            onClick = {
                navController.navigate("calculator") {
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Calculate,
                    contentDescription = "Calc"
                )
            },
            label = {
                Text(
                    text = "Calc",
                    fontWeight = FontWeight.Bold
                )
            },
            colors = bottomNavColors()
        )

        NavigationBarItem(
            selected = currentRoute == "digitalSlate",
            onClick = {
                navController.navigate("digitalSlate") {
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Store,
                    contentDescription = "Board"
                )
            },
            label = {
                Text(
                    text = "Board",
                    fontWeight = FontWeight.Bold
                )
            },
            colors = bottomNavColors()
        )

        NavigationBarItem(
            selected = currentRoute == "profile",
            onClick = {
                navController.navigate("profile") {
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile"
                )
            },
            label = {
                Text(
                    text = "Profile",
                    fontWeight = FontWeight.Bold
                )
            },
            colors = bottomNavColors()
        )
    }
}

@Composable
fun bottomNavColors() = NavigationBarItemDefaults.colors(
    selectedIconColor = Color(0xFF55F7B6),
    selectedTextColor = Color(0xFF55F7B6),
    unselectedIconColor = Color.White,
    unselectedTextColor = Color.White,
    indicatorColor = Color(0xFF12352B)
)