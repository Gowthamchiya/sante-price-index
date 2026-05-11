package com.example.bhuvana.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bhuvana.screens.AboutMissionScreen
import com.example.bhuvana.screens.DashboardScreen
import com.example.bhuvana.screens.DigitalSlateScreen
import com.example.bhuvana.screens.EditProfileScreen
import com.example.bhuvana.screens.LanguageSettingsScreen
import com.example.bhuvana.screens.MarketWatchScreen
import com.example.bhuvana.screens.ProductListViewModelScreen
import com.example.bhuvana.screens.ProductPriceBoardScreen
import com.example.bhuvana.screens.ProfitCalculatorScreen
import com.example.bhuvana.screens.SplashScreen
import com.example.bhuvana.screens.UserProfileScreen

@Composable
fun AppNavigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        // Splash screen with NavController
        composable("splash") {
            SplashScreen(navController = navController)
        }

        // Dashboard screen
        composable("dashboard") {
            DashboardScreen(navController = navController)
        }

        // Profit Calculator
        composable("calculator") {
            ProfitCalculatorScreen()
        }

        // Market Watch
        composable("market") {
            MarketWatchScreen()
        }

        // About Mission
        composable("about") {
            AboutMissionScreen()
        }

        // User Profile
        composable("profile") {
            UserProfileScreen()
        }

        // Language Settings
        composable("language") {
            LanguageSettingsScreen()
        }

        // Product Price Board
        composable("priceBoard") {
            ProductPriceBoardScreen()
        }

        // Edit Profile
        composable("editProfile") {
            EditProfileScreen()
        }

        // Digital Slate
        composable("digitalSlate") {
            DigitalSlateScreen()
        }

        // Product List
        composable("products") {
            ProductListViewModelScreen()
        }
    }
}