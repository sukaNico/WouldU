package com.example.dilemario.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dilemario.data.MockData
import com.example.dilemario.model.Dilema
import com.example.dilemario.ui.components.BottomNavigationBar
@Composable
fun AdminDilemasScreen(navController: NavController) {
    val dilemas = remember { MockData.dilemas.toMutableList() } // mutable para futuras acciones

    Scaffold(
        containerColor = Color(0xFF202020),
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFF202020))
                .padding(16.dp)
        ) {

            Text(
                text = "Administrar Dilemas",
                color = Color.White,
                fontSize = 26.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // -----------------------------
            // BOTÓN CREAR NUEVO DILEMA
            // -----------------------------
            Button(
                onClick = { navController.navigate("crearDilema") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text("Crear Nuevo Dilema", color = Color.White, fontSize = 16.sp)
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(dilemas) { dilema ->
                    DilemaCard(dilema = dilema, onClick = {
                        // Navegar a la pantalla de edición
                        navController.navigate("editDilemma/${dilema.id}")
                    })
                }
            }
        }
    }
}

@Composable
fun DilemaCard(dilema: Dilema, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2C2C2C)
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = dilema.titulo, color = Color.White, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = dilema.descripcion, color = Color.LightGray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))

            // ----- Opciones como botones redondeados -----
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = { /* Acción */ },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFF4CAF50),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(dilema.opcionA, fontSize = 14.sp)
                }

                OutlinedButton(
                    onClick = { /* Acción */ },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFF4CAF50),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(dilema.opcionB, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Categoría: ${dilema.categoria}", color = Color.Gray, fontSize = 12.sp)
        }
    }
}
