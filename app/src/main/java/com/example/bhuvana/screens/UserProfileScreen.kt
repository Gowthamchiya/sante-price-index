package com.example.bhuvana.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserProfileScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Vendor Profile", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        Text("Name : Gowtham", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text("Profession : Vendor", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text("Experience : 5 Years", fontSize = 20.sp)
    }
}