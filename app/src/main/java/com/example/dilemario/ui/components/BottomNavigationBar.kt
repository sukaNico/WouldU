package com.example.dilemario.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings

@Composable
fun BottomNavigationBar(navController: NavController) {

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(containerColor = Color(0xFF202020)) {

        // CONFIGURACIÓN
        NavigationBarItem(
            selected = currentRoute == "config",
            onClick = { navController.navigate("config") },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Configuración") },
            label = { Text("Config") }
        )

        // ⭐ HOME (lleva al pager)
        NavigationBarItem(
            selected = currentRoute == "pager",
            onClick = { navController.navigate("pager") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") }
        )

        // ⭐ DILEMAS (administrar, crear, borrar)
        NavigationBarItem(
            selected = currentRoute == "dilemas",
            onClick = { navController.navigate("adminDilemas") },
            icon = { Icon(Icons.Default.List, contentDescription = "Dilemas") },
            label = { Text("Dilemas") }
        )


        // PERFIL
        NavigationBarItem(
            selected = currentRoute == "perfil",
            onClick = { navController.navigate("profile") },
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") }
        )
    }
}
