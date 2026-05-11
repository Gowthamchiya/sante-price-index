package com.example.bhuvana.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    LaunchedEffect(Unit) {

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
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF00C853),
                        Color(0xFF101416)
                    )
                )
            ),

        contentAlignment = Alignment.Center

    ) {

        Column(

            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Card(

                shape = CircleShape,

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )

            ) {

                Box(

                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),

                    contentAlignment = Alignment.Center

                ) {

                    Icon(
                        imageVector = Icons.Default.Store,
                        contentDescription = null,
                        tint = Color(0xFF00C853),
                        modifier = Modifier.size(60.dp)
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            Text(
                text = "SANTE PRICE INDEX",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Smart Market Intelligence",
                color = Color.White,
                fontSize = 18.sp
            )

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            CircularProgressIndicator(
                color = Color.White
            )
        }
    }
}