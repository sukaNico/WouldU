package com.example.dilemario.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dilemario.ui.components.BottomNavigationBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDilemmaScreen(navController: NavController) {
    var titulo by remember { mutableStateOf(TextFieldValue("")) }
    var descripcion by remember { mutableStateOf(TextFieldValue("")) }
    var opcionA by remember { mutableStateOf(TextFieldValue("")) }
    var opcionB by remember { mutableStateOf(TextFieldValue("")) }
    var categoria by remember { mutableStateOf("General") }

    val categorias = listOf("General", "Humor", "Cultura", "Tecnología", "Educación")
    var expanded by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = Color(0xFF202020),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
                .background(Color(0xFF202020)),
            verticalArrangement = Arrangement.Top
        ) {

            Text(
                text = "Crear Dilema",
                color = Color.White,
                fontSize = 26.sp,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // ----- Campos de texto -----
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                placeholder = { Text("Escribe el título del dilema") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                placeholder = { Text("Describe el dilema") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 96.dp)
                    .padding(bottom = 12.dp)
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = opcionA,
                    onValueChange = { opcionA = it },
                    label = { Text("Opción A") },
                    placeholder = { Text("Opción A") },
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = opcionB,
                    onValueChange = { opcionB = it },
                    label = { Text("Opción B") },
                    placeholder = { Text("Opción B") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Dropdown de categorías
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = categoria,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoría") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categorias.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat) },
                            onClick = {
                                categoria = cat
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (titulo.text.isBlank() || opcionA.text.isBlank() || opcionB.text.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Completa título y ambas opciones antes de crear.")
                        }
                        return@Button
                    }

                    scope.launch {
                        snackbarHostState.showSnackbar("Dilema creado ✅")
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("Crear Dilema")
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
