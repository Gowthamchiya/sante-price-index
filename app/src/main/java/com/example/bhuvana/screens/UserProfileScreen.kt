package com.example.bhuvana.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bhuvana.components.BottomNavigationBar

@Composable
fun UserProfileScreen(navController: NavController) {

    val context = LocalContext.current

    var isEditing by remember { mutableStateOf(false) }
    var isKannada by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("Gowtham") }
    var phone by remember { mutableStateOf("9876543210") }
    var address by remember { mutableStateOf("Sante Local Market") }

    var editName by remember { mutableStateOf(name) }
    var editPhone by remember { mutableStateOf(phone) }
    var editAddress by remember { mutableStateOf(address) }

    val title = if (isKannada) "ಪ್ರೊಫೈಲ್" else "Profile"
    val subtitle = if (isKannada) "ಸ್ಮಾರ್ಟ್ ಮಾರುಕಟ್ಟೆ ಮಾರಾಟಗಾರ" else "Smart Market Vendor"
    val business = if (isKannada) "ತರಕಾರಿ ಮತ್ತು ಮಾರುಕಟ್ಟೆ ವ್ಯಾಪಾರ" else "Vegetable & Market Trading"
    val status = if (isKannada) "ಲೈವ್ ಬೆಲೆ ವ್ಯವಸ್ಥೆ ಸಕ್ರಿಯವಾಗಿದೆ" else "Live Price System Active"
    val languageText = if (isKannada) "ಕನ್ನಡ" else "English"

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        },
        containerColor = Color(0xFF07110D)
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF07110D),
                            Color(0xFF0B1712),
                            Color.Black
                        )
                    )
                )
                .verticalScroll(rememberScrollState())
                .padding(18.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = title,
                    color = Color(0xFF55F7B6),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Black
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = {
                            editName = name
                            editPhone = phone
                            editAddress = address
                            isEditing = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Profile",
                            tint = Color(0xFFFFD84D)
                        )
                    }

                    Button(
                        onClick = {
                            isKannada = !isKannada
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFD84D)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = null,
                            tint = Color.Black
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = languageText,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF151817)
                )
            ) {

                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .background(
                                color = Color(0xFF12352B),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Color(0xFF55F7B6),
                            modifier = Modifier.size(54.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = name,
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Black
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = subtitle,
                        color = Color(0xFFFFD84D),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            ProfileInfoRow(
                icon = Icons.Default.Store,
                title = if (isKannada) "ವ್ಯಾಪಾರ ಪ್ರಕಾರ" else "Business Type",
                value = business
            )

            ProfileInfoRow(
                icon = Icons.Default.Phone,
                title = if (isKannada) "ಮೊಬೈಲ್ ಸಂಖ್ಯೆ" else "Mobile Number",
                value = phone
            )

            ProfileInfoRow(
                icon = Icons.Default.LocationOn,
                title = if (isKannada) "ವಿಳಾಸ" else "Address",
                value = address
            )

            ProfileInfoRow(
                icon = Icons.Default.Language,
                title = if (isKannada) "ಭಾಷೆ" else "Language",
                value = languageText
            )

            ProfileInfoRow(
                icon = Icons.Default.Store,
                title = if (isKannada) "ಸಿಸ್ಟಮ್ ಸ್ಥಿತಿ" else "System Status",
                value = status
            )

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = {
                    editName = name
                    editPhone = phone
                    editAddress = address
                    isEditing = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00B37E)
                )
            ) {

                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    tint = Color.Black
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = if (isKannada) "ಪ್ರೊಫೈಲ್ ತಿದ್ದುಪಡಿ" else "Edit Profile",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    if (isEditing) {

        AlertDialog(
            onDismissRequest = {
                isEditing = false
            },
            title = {
                Text(
                    text = if (isKannada) "ಪ್ರೊಫೈಲ್ ತಿದ್ದುಪಡಿ" else "Edit Profile"
                )
            },
            text = {
                Column {

                    OutlinedTextField(
                        value = editName,
                        onValueChange = {
                            editName = it
                        },
                        label = {
                            Text(
                                text = if (isKannada) "ಹೆಸರು" else "Name"
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = editPhone,
                        onValueChange = {
                            editPhone = it
                        },
                        label = {
                            Text(
                                text = if (isKannada) "ಮೊಬೈಲ್ ಸಂಖ್ಯೆ" else "Phone Number"
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = editAddress,
                        onValueChange = {
                            editAddress = it
                        },
                        label = {
                            Text(
                                text = if (isKannada) "ವಿಳಾಸ" else "Address"
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {

                        if (
                            editName.isBlank() ||
                            editPhone.isBlank() ||
                            editAddress.isBlank()
                        ) {
                            Toast.makeText(
                                context,
                                "Please fill all fields",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            name = editName.trim()
                            phone = editPhone.trim()
                            address = editAddress.trim()

                            isEditing = false

                            Toast.makeText(
                                context,
                                if (isKannada) "ಪ್ರೊಫೈಲ್ ನವೀಕರಿಸಲಾಗಿದೆ" else "Profile updated",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00B37E)
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = null,
                        tint = Color.Black
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = if (isKannada) "ಉಳಿಸಿ" else "Save",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        isEditing = false
                    }
                ) {
                    Text(
                        text = if (isKannada) "ರದ್ದು" else "Cancel"
                    )
                }
            }
        )
    }
}

@Composable
fun ProfileInfoRow(
    icon: ImageVector,
    title: String,
    value: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF151817)
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF55F7B6)
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    color = Color.White.copy(alpha = 0.65f),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = value,
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}