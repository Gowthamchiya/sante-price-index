package com.example.bhuvana.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
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

            onClick = {
                navController.navigate("dashboard")
            },

            icon = {

                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },

            label = {
                Text("Home")
            }
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
                Text("Calculator")
            }
        )

        NavigationBarItem(

            selected = false,

            onClick = {
                navController.navigate("market")
            },

            icon = {

                Icon(
                    imageVector = Icons.Default.Store,
                    contentDescription = null
                )
            },

            label = {
                Text("Market")
            }
        )
    }
}