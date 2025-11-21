package com.example.dilemario.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dilemario.data.RegisterRequest
import kotlinx.coroutines.launch
import retrofit2.HttpException

@Composable
fun CreateAccountScreen(navController: NavController) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var edadRango by remember { mutableStateOf("18-25") }
    var pais by remember { mutableStateOf("Colombia") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Crear Cuenta", color = Color.White, fontSize = 26.sp)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre", color = Color.White) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = Color.White) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Edad
        Row {
            listOf("18-25", "26-35", "36-45").forEach { rango ->
                Button(
                    onClick = { edadRango = rango },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (edadRango == rango) Color(0xFF4CAF50) else Color.DarkGray
                    ),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(rango, color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // País
        Row {
            listOf("Colombia", "México", "Argentina").forEach { p ->
                Button(
                    onClick = { pais = p },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (pais == p) Color(0xFF4CAF50) else Color.DarkGray
                    ),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(p, color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Crear Cuenta
        Button(
            onClick = {
                scope.launch {
                    try {
                        val response = RetrofitClient.api.register(
                            RegisterRequest(nombre, email, password, edadRango, pais)
                        )

                        if (response.success) {
                            Toast.makeText(context, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
                            navController.navigate("login") {
                                popUpTo("crearCuenta") { inclusive = true } // limpiar backstack
                            }
                        } else {
                            Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                        }

                    } catch (e: HttpException) {
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Crear Cuenta", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Ir al Login
        Button(
            onClick = {
                navController.navigate("login") {
                    popUpTo("crearCuenta") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Ir al Login", color = Color.White)
        }
    }
}
