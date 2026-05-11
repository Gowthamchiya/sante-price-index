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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.TrendingUp
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfitCalculatorScreen() {

    var mandiPrice by remember { mutableStateOf("") }
    var transportCost by remember { mutableStateOf("") }
    var wasteCost by remember { mutableStateOf("") }
    var profitMargin by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }

    var rrp by remember { mutableStateOf(0.0) }
    var netProfit by remember { mutableStateOf(0.0) }
    var grossSales by remember { mutableStateOf(0.0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101416))
            .padding(16.dp)
    ) {

        Text(
            text = "Profit Calculator",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Calculate fair selling price using Mandi + Transport + Waste + Profit",
            color = Color.Gray,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1C2022)
            )
        ) {

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                ProInputField(
                    value = mandiPrice,
                    label = "Mandi Price per kg",
                    onChange = { mandiPrice = it }
                )

                ProInputField(
                    value = transportCost,
                    label = "Transport Cost per kg",
                    onChange = { transportCost = it }
                )

                ProInputField(
                    value = wasteCost,
                    label = "Waste / Extra Cost per kg",
                    onChange = { wasteCost = it }
                )

                ProInputField(
                    value = profitMargin,
                    label = "Profit Margin per kg",
                    onChange = { profitMargin = it }
                )

                ProInputField(
                    value = quantity,
                    label = "Quantity in kg",
                    onChange = { quantity = it }
                )

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = {

                        val mandi = mandiPrice.toDoubleOrNull() ?: 0.0
                        val transport = transportCost.toDoubleOrNull() ?: 0.0
                        val waste = wasteCost.toDoubleOrNull() ?: 0.0
                        val profit = profitMargin.toDoubleOrNull() ?: 0.0
                        val qty = quantity.toDoubleOrNull() ?: 0.0

                        rrp = mandi + transport + waste + profit

                        grossSales = rrp * qty

                        netProfit = profit * qty
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00C853)
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.Calculate,
                        contentDescription = null,
                        tint = Color.White
                    )

                    Spacer(modifier = Modifier.padding(4.dp))

                    Text(
                        text = "Calculate Fair Price",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        ResultCard(
            title = "Recommended Retail Price",
            value = "₹$rrp / kg",
            color = Color(0xFF00C853)
        )

        ResultCard(
            title = "Gross Sales",
            value = "₹$grossSales",
            color = Color(0xFF2962FF)
        )

        ResultCard(
            title = "Net Profit",
            value = "₹$netProfit",
            color = Color(0xFFFFC107)
        )
    }
}

@Composable
fun ProInputField(
    value: String,
    label: String,
    onChange: (String) -> Unit
) {

    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = {
            Text(label)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedBorderColor = Color(0xFF00C853),
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.Gray,
            cursorColor = Color.White
        )
    )
}

@Composable
fun ResultCard(
    title: String,
    value: String,
    color: Color
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1C2022)
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {

                Text(
                    text = title,
                    color = Color.Gray,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = value,
                    color = color,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Icon(
                imageVector = Icons.Default.TrendingUp,
                contentDescription = null,
                tint = color
            )
        }
    }
}