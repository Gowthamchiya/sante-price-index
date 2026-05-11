package com.example.bhuvana.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bhuvana.models.ProductItem
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun AddProductScreen() {

    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101416))
            .padding(20.dp)
    ) {

        Text(
            text = "Add Product Price",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Add today's mandi price for vendors",
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

                Button(
                    onClick = {

                        if (name.isBlank() || price.isBlank()) {

                            Toast.makeText(
                                context,
                                "Please enter product name and price",
                                Toast.LENGTH_SHORT
                            ).show()

                            return@Button
                        }

                        isLoading = true

                        val product = ProductItem(
                            name = name.trim(),
                            price = price.trim()
                        )

                        db.collection("products")
                            .add(product)
                            .addOnSuccessListener {

                                isLoading = false
                                name = ""
                                price = ""

                                Toast.makeText(
                                    context,
                                    "Product added successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener {

                                isLoading = false

                                Toast.makeText(
                                    context,
                                    "Failed to add product",
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
                        imageVector = Icons.Default.AddBusiness,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = if (isLoading) "Adding..." else "Add Product",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}