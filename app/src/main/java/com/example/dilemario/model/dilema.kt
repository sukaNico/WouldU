package com.example.dilemario.model

data class Dilema(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val categoria: String,
    val opcionA: String,
    val opcionB: String,
    val votosOpcionA: Int,
    val votosOpcionB: Int
)

