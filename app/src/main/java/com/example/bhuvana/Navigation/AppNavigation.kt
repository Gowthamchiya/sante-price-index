package com.example.bhuvana.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bhuvana.screens.AddProductScreen
import com.example.bhuvana.screens.DashboardScreen
import com.example.bhuvana.screens.DigitalSlateScreen
import com.example.bhuvana.screens.EditProductScreen
import com.example.bhuvana.screens.MarketWatchScreen
import com.example.bhuvana.screens.ProfitCalculatorScreen
import com.example.bhuvana.screens.UserProfileScreen

@Composable
fun AppNavigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "dashboard"
    ) {

        composable("dashboard") {
            DashboardScreen(navController)
        }

        composable("market") {
            MarketWatchScreen(navController)
        }

        composable("calculator") {
            ProfitCalculatorScreen()
        }

        composable("digitalSlate") {
            DigitalSlateScreen()
        }

        composable("profile") {
            UserProfileScreen()
        }

        composable("addProduct") {
            AddProductScreen()
        }

        composable(
            route = "editProduct/{id}",
            arguments = listOf(
                navArgument("id") {
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->

            EditProductScreen(
                navController = navController,
                backStackEntry = backStackEntry
            )
        }
    }
}