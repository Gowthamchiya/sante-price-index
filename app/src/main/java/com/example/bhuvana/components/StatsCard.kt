package com.example.bhuvana.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatsCard(

    title: String,
    value: String,
    color1: Color,
    color2: Color

) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )

    ) {

        Column(

            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            color1,
                            color2
                        )
                    )
                )
                .padding(20.dp),

            verticalArrangement = Arrangement.Center

        ) {

            Text(
                text = title,
                color = Color.White,
                fontSize = 18.sp
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = value,
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}