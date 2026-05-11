package com.example.bhuvana.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bhuvana.screens.DashboardScreen
import com.example.bhuvana.screens.ProfitCalculatorScreen

@Composable
fun AppNavigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "dashboard"
    ) {

        composable("dashboard") {

            DashboardScreen(navController)

        }

        composable("calculator") {

            ProfitCalculatorScreen()

        }
    }
}