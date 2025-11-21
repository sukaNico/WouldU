package com.example.dilemario.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dilemario.data.*
import com.example.dilemario.model.Dilema
import com.example.dilemario.ui.components.BottomNavigationBar
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@Composable
fun AdminDilemasScreen(navController: NavController) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    val scope = rememberCoroutineScope()

    var token by remember { mutableStateOf<String?>(null) }
    var userId by remember { mutableStateOf<Int?>(null) }
    var dilemas by remember { mutableStateOf<List<DilemaApi>>(emptyList()) }
    var cargando by remember { mutableStateOf(true) }

    // Leer token de DataStore y decodificar id
    LaunchedEffect(Unit) {
        val savedToken = prefs.token.firstOrNull()
        token = savedToken

        savedToken?.let {
            try {
                val parts = it.split(".")
                if (parts.size >= 2) {
                    val payload = parts[1]
                    val decoded = String(android.util.Base64.decode(payload, android.util.Base64.URL_SAFE))
                    val json = org.json.JSONObject(decoded)
                    userId = json.optInt("id", -1)
                    Log.d("AdminDilemasScreen", "User ID extraído del token: $userId")

                    // Establecer token en Retrofit
                    RetrofitClient.setToken(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                userId = -1
            }
        }

        // Cargar dilemas del usuario
        userId?.let { id ->
            try {
                val response = RetrofitClient.api.getDilemasUsuario(id)
                if (response.success) {
                    dilemas = response.data
                    Log.d("AdminDilemasScreen", "Dilemas cargados: ${dilemas.size}")
                }
            } catch (e: Exception) {
                Log.e("AdminDilemasScreen", "Error cargando dilemas", e)
            } finally {
                cargando = false
            }
        }
    }

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

            if (cargando) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF4CAF50))
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(dilemas) { dilema ->
                        DilemaCard(dilema = dilema, onClick = {
                            Log.d("AdminDilemasScreen", "ID DEL DILEMA:${dilema.id}")
                            navController.navigate("editDilemma/${dilema.id}")
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun DilemaCard(dilema: DilemaApi, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C2C)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = dilema.titulo, color = Color.White, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = dilema.descripcion, color = Color.LightGray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = { /* Acción */ },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFF4CAF50),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.height(32.dp)
                ) { Text(dilema.opcion_a, fontSize = 14.sp) }

                OutlinedButton(
                    onClick = { /* Acción */ },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFF4CAF50),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.height(32.dp)
                ) { Text(dilema.opcion_b, fontSize = 14.sp) }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Categoría: ${dilema.categoria}", color = Color.Gray, fontSize = 12.sp)
        }
    }
}
