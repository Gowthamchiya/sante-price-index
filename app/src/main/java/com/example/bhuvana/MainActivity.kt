package com.example.bhuvana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.bhuvana.navigation.AppNavigation
import com.example.bhuvana.ui.theme.BhuvanaTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()

            BhuvanaTheme {
                AppNavigation(navController)
            }
        }
    }
}