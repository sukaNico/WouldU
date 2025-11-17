package com.example.dilemario.data

import com.example.dilemario.model.Dilema

object MockData {
    val dilemas = listOf(
        Dilema(
            id = 1,
            titulo = "titulo 1",
            descripcion = "¿Desviarías un tren para salvar a 5 personas aunque muera 1?",
            opcionA = "Desviar (salvar 5)",
            opcionB = "No intervenir (salvar 1)",
            categoria = "Filosofía",
            votosOpcionA = 150,
            votosOpcionB = 50
        ),
        Dilema(
            id = 2,
            titulo = "titulo 2",
            descripcion = "¿Mentirías para evitar que un ser querido sufra?",
            opcionA = "Sí, por empatía",
            opcionB = "No, siempre la verdad",
            categoria = "Vida diaria",
            votosOpcionA = 230,
            votosOpcionB = 180
        )
    )
}
