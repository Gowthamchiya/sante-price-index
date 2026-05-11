package com.example.bhuvana

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.bhuvana.navigation.AppNavigation
import com.example.bhuvana.ui.theme.BhuvanaTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔥 Initialize Firebase
        FirebaseApp.initializeApp(this)

        // 🔹 Optional: test Firestore connection
        testFirebaseConnection()

        setContent {
            val navController = rememberNavController()

            BhuvanaTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavigation(navController)
                }
            }
        }
    }

    // 🔹 Simple Firestore test to confirm Firebase setup
    private fun testFirebaseConnection() {
        val db = FirebaseFirestore.getInstance()
        val data = hashMapOf("status" to "connected")

        db.collection("test_connection")
            .add(data)
            .addOnSuccessListener {
                Log.d("FIREBASE_TEST", "SUCCESS: Firebase Connected ✔")
            }
            .addOnFailureListener { e ->
                Log.e("FIREBASE_TEST", "FAILED: Firebase Not Connected ❌", e)
            }
    }
}