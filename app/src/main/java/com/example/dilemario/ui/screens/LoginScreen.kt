package com.example.dilemario.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    onLogin: () -> Unit = {},
    onGoToRegister: () -> Unit = {}
) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val backgroundColor = Color(0xFF11131B)
    val cardColor = Color(0xFF2A2D34)
    val textColor = Color(0xFFE6E6E6)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(24.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Iniciar Sesión",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Usuario
            TextField(
                value = username,
                onValueChange = { username = it },
                placeholder = { Text("Usuario") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = cardColor,
                    focusedContainerColor = cardColor,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = textColor,
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    unfocusedPlaceholderColor = Color.Gray,
                    focusedPlaceholderColor = Color.Gray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(20.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Contraseña
            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = cardColor,
                    focusedContainerColor = cardColor,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = textColor,
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    unfocusedPlaceholderColor = Color.Gray,
                    focusedPlaceholderColor = Color.Gray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(20.dp)
            )

            Spacer(modifier = Modifier.height(35.dp))

            Button(
                onClick = onLogin,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = cardColor,
                    contentColor = textColor
                ),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text("Entrar", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(18.dp))

            TextButton(onClick = onGoToRegister) {
                Text("Crear cuenta nueva", color = Color.Gray)
            }
        }
    }
}
