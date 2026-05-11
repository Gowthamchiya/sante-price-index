package com.example.bhuvana.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LanguageSettingsScreen() {

    var selectedLanguage by remember {
        mutableStateOf("English")
    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)

    ) {

        Text(
            text = "Selected Language : $selectedLanguage"
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                selectedLanguage = "English"
            }
        ) {

            Text("English")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                selectedLanguage = "Tamil"
            }
        ) {

            Text("Tamil")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                selectedLanguage = "Kannada"
            }
        ) {

            Text("Kannada")
        }
    }
}