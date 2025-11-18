package com.example.dilemario.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dilemario.R
import com.example.dilemario.ui.components.BottomNavigationBar

@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        containerColor = Color(0xFF202020), // Fondo oscuro
        bottomBar = { BottomNavigationBar(navController) } // Mantener la navegación inferior
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Foto de perfil
            Image(
                painter = painterResource(id = R.drawable.profile_placeholder), // Imagen local o usa Coil
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.Gray),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Información del usuario
            Text(text = "Nicolás Carvajal", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text(text = "correo@ejemplo.com", fontSize = 16.sp, color = Color.LightGray)
            Text(text = "Edad: 21", fontSize = 16.sp, color = Color.LightGray)

            Spacer(modifier = Modifier.height(24.dp))

            // Estadísticas
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF303030)) // Card oscuro
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Estadísticas", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Dilemas contestados: 42", color = Color.LightGray)
                    Text(text = "Dilemas creados: 5", color = Color.LightGray)
                    Text(text = "Nivel: Intermedio", color = Color.LightGray)
                }
            }
        }
    }
}
