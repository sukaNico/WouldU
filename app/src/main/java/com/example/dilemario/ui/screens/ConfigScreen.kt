package com.example.dilemario.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dilemario.ui.components.BottomNavigationBar

@Composable
fun ConfigScreen(navController: NavController) {

    // Variables de opciones básicas
    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(true) }

    Scaffold(
        containerColor = Color(0xFF202020),
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = "Configuración",
                color = Color.White,
                fontSize = 26.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // ----- Opciones básicas -----
            SettingToggle(
                title = "Notificaciones",
                subtitle = "Activar alertas y actualizaciones",
                value = notificationsEnabled,
                onChange = { notificationsEnabled = it }
            )

            SettingToggle(
                title = "Modo Oscuro",
                subtitle = "Usar siempre el tema oscuro",
                value = darkModeEnabled,
                onChange = { darkModeEnabled = it }
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { /* TODO: cerrar sesión */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar sesión")
            }
        }
    }
}

@Composable
fun SettingToggle(
    title: String,
    subtitle: String,
    value: Boolean,
    onChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(title, color = Color.White, fontSize = 18.sp)
            Text(subtitle, color = Color.LightGray, fontSize = 13.sp)
        }

        Switch(
            checked = value,
            onCheckedChange = onChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color(0xFF4CAF50),
                checkedTrackColor = Color(0xFF6FD06F)
            )
        )
    }
}
