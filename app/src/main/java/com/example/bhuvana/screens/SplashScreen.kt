package com.example.bhuvana.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    val alpha = remember {
        Animatable(0f)
    }

    LaunchedEffect(true) {

        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(2000)
        )

        delay(2500)

        navController.navigate("dashboard")
    }

    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF101416),
                        Color(0xFF1B5E20)
                    )
                )
            ),

        contentAlignment = Alignment.Center

    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.alpha(alpha.value)
        ) {

            Icon(
                imageVector = Icons.Default.Store,
                contentDescription = null,
                tint = Color(0xFF00E676),
                modifier = Modifier.size(100.dp)
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "SANTE PRICE INDEX",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Smart Market Intelligence",
                color = Color.LightGray,
                fontSize = 16.sp
            )
        }
    }
}