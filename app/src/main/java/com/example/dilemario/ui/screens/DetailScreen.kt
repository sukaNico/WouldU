package com.example.dilemario.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dilemario.data.MockData

@Composable
fun DetailScreen(navController: NavController, id: Int) {
    val dilema = MockData.dilemas.firstOrNull { it.id == id } ?: return

    val total = (dilema.votosOpcionA + dilema.votosOpcionB).coerceAtLeast(1)
    val porcA = (dilema.votosOpcionA * 100) / total
    val porcB = (dilema.votosOpcionB * 100) / total

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(text = dilema.titulo, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = dilema.descripcion)

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* mock: incrementar si quieres */ }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "A: ${dilema.opcionA}")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { /* mock */ }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "B: ${dilema.opcionB}")
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Estadísticas: A: ${dilema.votosOpcionA} ($porcA%)  •  B: ${dilema.votosOpcionA} ($porcB%)")
    }
}
