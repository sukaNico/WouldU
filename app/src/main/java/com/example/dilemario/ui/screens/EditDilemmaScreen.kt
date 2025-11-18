package com.example.dilemario.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dilemario.data.MockData
import com.example.dilemario.model.Dilema
import com.example.dilemario.ui.components.BottomNavigationBar

@Composable
fun EditDilemmaScreen(navController: NavController, dilemaId: Int) {

    val dilema = MockData.dilemas.find { it.id == dilemaId }

    if (dilema == null) {
        Text("Dilema no encontrado", color = Color.White)
        return
    }

    var titulo by remember { mutableStateOf(dilema.titulo) }
    var descripcion by remember { mutableStateOf(dilema.descripcion) }
    var opcionA by remember { mutableStateOf(dilema.opcionA) }
    var opcionB by remember { mutableStateOf(dilema.opcionB) }
    var categoria by remember { mutableStateOf(dilema.categoria) }

    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color(0xFF202020),
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = "Editar Dilema",
                color = Color.White,
                fontSize = 28.sp,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // -----------------------------
            // CAMPOS
            // -----------------------------

            EditText(label = "Título", value = titulo, onChange = { titulo = it })
            Spacer(Modifier.height(16.dp))

            EditText(label = "Descripción", value = descripcion, onChange = { descripcion = it })
            Spacer(Modifier.height(16.dp))

            EditText(label = "Opción A", value = opcionA, onChange = { opcionA = it })
            Spacer(Modifier.height(16.dp))

            EditText(label = "Opción B", value = opcionB, onChange = { opcionB = it })
            Spacer(Modifier.height(16.dp))

            EditText(label = "Categoría", value = categoria, onChange = { categoria = it })
            Spacer(Modifier.height(24.dp))

            // -----------------------------
            // BOTÓN GUARDAR
            // -----------------------------
            Button(
                onClick = {
                    dilema.titulo = titulo
                    dilema.descripcion = descripcion
                    dilema.opcionA = opcionA
                    dilema.opcionB = opcionB
                    dilema.categoria = categoria
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50)
                )
            ) {
                Text("Guardar Cambios")
            }

            Spacer(Modifier.height(16.dp))

            // -----------------------------
            // BOTÓN ELIMINAR
            // -----------------------------
            Button(
                onClick = { showDeleteDialog = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD32F2F)
                )
            ) {
                Text("Eliminar Dilema")
            }

            // -----------------------------
            // DIÁLOGO CONFIRMACIÓN
            // -----------------------------
            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = {
                        Text("¿Eliminar dilema?", color = Color.White)
                    },
                    text = {
                        Text(
                            "Esta acción no se puede deshacer.",
                            color = Color.LightGray
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                MockData.dilemas.remove(dilema)
                                showDeleteDialog = false
                                navController.popBackStack()
                            }
                        ) {
                            Text("Eliminar", color = Color.Red)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) {
                            Text("Cancelar", color = Color.White)
                        }
                    },
                    containerColor = Color(0xFF2C2C2C)
                )
            }
        }
    }
}

@Composable
fun EditText(label: String, value: String, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedContainerColor = Color(0xFF303030),
            unfocusedContainerColor = Color(0xFF303030),
            cursorColor = Color(0xFF4CAF50),
            focusedLabelColor = Color(0xFF4CAF50)
        )
    )
}
