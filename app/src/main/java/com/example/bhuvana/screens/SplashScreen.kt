package com.example.bhuvana.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    var visible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {

        visible = true

        delay(2500)

        navController.navigate("dashboard") {
            popUpTo("splash") {
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Black,
                        Color(0xFF07110D),
                        Color.Black
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + scaleIn()
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .shadow(
                            elevation = 20.dp,
                            shape = CircleShape,
                            ambientColor = Color(0xFF55F7B6),
                            spotColor = Color(0xFF55F7B6)
                        )
                        .background(
                            color = Color(0xFF12352B),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.Store,
                        contentDescription = null,
                        tint = Color(0xFF55F7B6),
                        modifier = Modifier.size(62.dp)
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "SANTE-PRICE\nINDEX",
                    color = Color.White,
                    fontSize = 34.sp,
                    lineHeight = 36.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Smart Vendor Price Intelligence",
                    color = Color(0xFFFFD84D),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "LIVE MARKET • REALTIME FIREBASE • SMART PRICING",
                    color = Color(0xFF55F7B6),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.4.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}