package com.example.dilemario.model

data class Dilema(
    var id: Int,
    var titulo: String,
    var descripcion: String,
    var categoria: String,
    var opcionA: String,
    var opcionB: String,
    var votosOpcionA: Int,
    var votosOpcionB: Int
)

