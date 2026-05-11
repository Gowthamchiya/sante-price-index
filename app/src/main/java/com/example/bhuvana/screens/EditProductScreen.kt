package com.example.bhuvana.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun EditProductScreen(
    navController: NavController,
    backStackEntry: NavBackStackEntry
) {

    val context = LocalContext.current

    val productId =
        backStackEntry.arguments?.getString("id") ?: ""

    val db = FirebaseFirestore.getInstance()

    var name by remember {
        mutableStateOf("")
    }

    var price by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    // LOAD PRODUCT DATA

    LaunchedEffect(Unit) {

        db.collection("products")
            .document(productId)
            .get()
            .addOnSuccessListener { document ->

                name = document.getString("name") ?: ""

                price = document.getString("price") ?: ""
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101416))
            .padding(20.dp)
    ) {

        Text(
            text = "Edit Product",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Update live Firebase product price",
            color = Color.Gray,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1C2022)
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                // PRODUCT NAME

                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    label = {
                        Text("Product Name")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFF00C853),
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(14.dp))

                // PRODUCT PRICE

                OutlinedTextField(
                    value = price,
                    onValueChange = {
                        price = it
                    },
                    label = {
                        Text("Price Example: ₹40/kg")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFF00C853),
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(22.dp))

                // UPDATE BUTTON

                Button(
                    onClick = {

                        if (name.isBlank() || price.isBlank()) {

                            Toast.makeText(
                                context,
                                "Please fill all fields",
                                Toast.LENGTH_SHORT
                            ).show()

                            return@Button
                        }

                        isLoading = true

                        db.collection("products")
                            .document(productId)
                            .update(
                                mapOf(
                                    "name" to name.trim(),
                                    "price" to price.trim()
                                )
                            )
                            .addOnSuccessListener {

                                isLoading = false

                                Toast.makeText(
                                    context,
                                    "Product updated successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                navController.popBackStack()
                            }
                            .addOnFailureListener {

                                isLoading = false

                                Toast.makeText(
                                    context,
                                    "Update failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00C853)
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = if (isLoading)
                            "Updating..."
                        else
                            "Update Product",

                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}