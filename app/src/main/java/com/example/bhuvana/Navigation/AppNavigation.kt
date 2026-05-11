package com.example.bhuvana.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bhuvana.screens.*

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") { DashboardScreen(navController) }
        composable("calculator") { ProfitCalculatorScreen() }
        composable("market") { MarketWatchScreen(navController) }
        composable("profile") { UserProfileScreen() }
        composable("addProduct") { AddProductScreen() }

        composable(
            "editProduct/{id}",
            arguments = listOf(navArgument("id") { defaultValue = "" })
        ) { backStackEntry ->
            EditProductScreen(navController, backStackEntry)
        }
    }
}