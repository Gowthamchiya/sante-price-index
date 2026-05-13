package com.example.bhuvana.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF07110D),
                        Color(0xFF0B1712),
                        Color.Black
                    )
                )
            )
            .padding(20.dp)
    ) {

        Text(
            text = "Add Today’s Price",
            color = Color(0xFF55F7B6),
            fontSize = 30.sp,
            fontWeight = FontWeight.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Enter mandi product price. It will update live in Dashboard, Price Watch and Price Board.",
            color = Color.White.copy(alpha = 0.75f),
            fontSize = 15.sp,
            lineHeight = 21.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF151817)
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.Store,
                    contentDescription = null,
                    tint = Color(0xFFFFD84D)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Product Details",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(18.dp))

                AddProductInputField(
                    value = name,
                    label = "Product Name",
                    placeholder = "Example: Tomato",
                    keyboardType = KeyboardType.Text,
                    onChange = { name = it }
                )

                AddProductInputField(
                    value = price,
                    label = "Price",
                    placeholder = "Example: ₹40/kg",
                    keyboardType = KeyboardType.Text,
                    onChange = { price = it }
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
                                    "Price added successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener {

                                isLoading = false

                                Toast.makeText(
                                    context,
                                    "Failed to add price",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                    },
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00B37E),
                        disabledContainerColor = Color.Gray
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.AddBusiness,
                        contentDescription = null,
                        tint = Color.Black
                    )

                    Spacer(modifier = Modifier.padding(4.dp))

                    Text(
                        text = if (isLoading) "Adding Price..." else "Add Live Price",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun AddProductInputField(
    value: String,
    label: String,
    placeholder: String,
    keyboardType: KeyboardType,
    onChange: (String) -> Unit
) {

    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = {
            Text(label)
        },
        placeholder = {
            Text(placeholder)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedBorderColor = Color(0xFF55F7B6),
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = Color(0xFF55F7B6),
            unfocusedLabelColor = Color.Gray,
            cursorColor = Color(0xFF55F7B6)
        )
    )
}