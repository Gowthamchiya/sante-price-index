package com.example.bhuvana.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserProfileScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101416))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .clip(CircleShape)
                .background(Color(0xFF00C853))
                .padding(28.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Gowtham",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Weekly Market Vendor",
            color = Color.Gray,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        ProfileInfoCard(
            title = "Business Type",
            value = "Vegetable Vendor",
            iconColor = Color(0xFF00C853),
            iconType = "store"
        )

        ProfileInfoCard(
            title = "Experience",
            value = "5 Years",
            iconColor = Color(0xFFFFC107),
            iconType = "star"
        )

        ProfileInfoCard(
            title = "Market Status",
            value = "Active Seller",
            iconColor = Color(0xFF2962FF),
            iconType = "trend"
        )
    }
}

@Composable
fun ProfileInfoCard(
    title: String,
    value: String,
    iconColor: Color,
    iconType: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1C2022)
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column {

                Text(
                    text = title,
                    color = Color.Gray,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = value,
                    color = Color.White,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Icon(
                imageVector =
                    when (iconType) {
                        "store" -> Icons.Default.Store
                        "star" -> Icons.Default.Star
                        else -> Icons.Default.TrendingUp
                    },
                contentDescription = null,
                tint = iconColor
            )
        }
    }
}