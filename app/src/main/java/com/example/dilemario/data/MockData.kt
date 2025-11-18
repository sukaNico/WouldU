package com.example.dilemario.data

import androidx.compose.runtime.mutableStateListOf
import com.example.dilemario.model.Dilema

object MockData {
    val dilemas = mutableStateListOf(
        Dilema(
            id = 1,
            titulo = "El Dilema del Tren",
            descripcion = "¿Desviarías un tren para salvar a 5 personas aunque muera 1?",
            opcionA = "Desviar (salvar 5)",
            opcionB = "No intervenir (salvar 1)",
            categoria = "Filosofía",
            votosOpcionA = 150,
            votosOpcionB = 50
        ),
        Dilema(
            id = 2,
            titulo = "La Mentira Compasiva",
            descripcion = "¿Mentirías para evitar que un ser querido sufra?",
            opcionA = "Sí, por empatía",
            opcionB = "No, siempre la verdad",
            categoria = "Ética",
            votosOpcionA = 230,
            votosOpcionB = 180
        )
    )
}
