package com.example.dilemario.ui.screens

import android.util.Log
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
import com.example.dilemario.data.DilemaApi
import kotlinx.coroutines.launch

@Composable
fun EditDilemmaScreen(navController: NavController, dilemaId: Int) {
    val scope = rememberCoroutineScope()

    var dilema by remember { mutableStateOf<DilemaApi?>(null) }
    var cargando by remember { mutableStateOf(true) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Campos editables
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var opcionA by remember { mutableStateOf("") }
    var opcionB by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }

    // -----------------------------
    // CARGAR DILEMA DESDE API
    // -----------------------------
    LaunchedEffect(dilemaId) {
        try {
            val response = RetrofitClient.api.getDilema(dilemaId)
            if (response.success) {
                dilema = response.data
                titulo = dilema?.titulo ?: ""
                descripcion = dilema?.descripcion ?: ""
                opcionA = dilema?.opcion_a ?: ""
                opcionB = dilema?.opcion_b ?: ""
                categoria = dilema?.categoria ?: ""
            }
        } catch (e: Exception) {
            Log.e("EditDilemmaScreen", "Error cargando dilema", e)
        } finally {
            cargando = false
        }
    }

    if (cargando) {
        Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            CircularProgressIndicator(color = Color(0xFF4CAF50))
        }
        return
    }

    dilema?.let { d ->
        Scaffold(
            containerColor = Color(0xFF202020),
            bottomBar = { /* Aquí podrías poner BottomNavigationBar si quieres */ }
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

                EditText("Título", titulo) { titulo = it }
                Spacer(Modifier.height(16.dp))
                EditText("Descripción", descripcion) { descripcion = it }
                Spacer(Modifier.height(16.dp))
                EditText("Opción A", opcionA) { opcionA = it }
                Spacer(Modifier.height(16.dp))
                EditText("Opción B", opcionB) { opcionB = it }
                Spacer(Modifier.height(16.dp))
                EditText("Categoría", categoria) { categoria = it }
                Spacer(Modifier.height(24.dp))

                // -----------------------------
                // GUARDAR CAMBIOS
                // -----------------------------
                Button(
                    onClick = {
                        scope.launch {
                            try {
                                RetrofitClient.api.actualizarDilema(
                                    d.id,
                                    mapOf(
                                        "titulo" to titulo,
                                        "descripcion" to descripcion,
                                        "opcion_a" to opcionA,
                                        "opcion_b" to opcionB,
                                        "categoria" to categoria
                                    )
                                )
                                navController.popBackStack()
                            } catch (e: Exception) {
                                Log.e("EditDilemmaScreen", "Error actualizando dilema", e)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Text("Guardar Cambios")
                }

                Spacer(Modifier.height(16.dp))

                // -----------------------------
                // ELIMINAR DILEMA
                // -----------------------------
                Button(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                ) {
                    Text("Eliminar Dilema")
                }

                if (showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog = false },
                        title = { Text("¿Eliminar dilema?", color = Color.White) },
                        text = { Text("Esta acción no se puede deshacer.", color = Color.LightGray) },
                        confirmButton = {
                            TextButton(onClick = {
                                scope.launch {
                                    try {
                                        RetrofitClient.api.eliminarDilema(d.id)
                                        navController.popBackStack()
                                    } catch (e: Exception) {
                                        Log.e("EditDilemmaScreen", "Error eliminando dilema", e)
                                    }
                                }
                                showDeleteDialog = false
                            }) { Text("Eliminar", color = Color.Red) }
                        },
                        dismissButton = { TextButton(onClick = { showDeleteDialog = false }) { Text("Cancelar", color = Color.White) } },
                        containerColor = Color(0xFF2C2C2C)
                    )
                }
            }
        }
    } ?: run {
        Text("Dilema no encontrado", color = Color.White)
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
