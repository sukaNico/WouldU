package com.example.dilemario.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.navigation.NavController
import com.example.dilemario.data.UserPreferences
import com.example.dilemario.data.dataStore
import com.example.dilemario.ui.components.BottomNavigationBar
import kotlinx.coroutines.launch

@Composable
fun ConfigScreen(navController: NavController) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

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

            // ----- Opciones de configuración -----
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

            // ----- Botón Cerrar Sesión -----
            Button(
                onClick = {
                    scope.launch {
                        // Borrar todos los datos de sesión
                        context.dataStore.edit { prefs ->
                            prefs.remove(UserPreferences.USER_NAME)
                            prefs.remove(UserPreferences.USER_EMAIL)
                            prefs.remove(UserPreferences.USER_AGE)
                            prefs.remove(UserPreferences.USER_COUNTRY)
                            prefs.remove(UserPreferences.LOGIN_EMAIL)
                            prefs.remove(UserPreferences.LOGIN_PASSWORD)
                        }

                        // Navegar al login y limpiar backstack
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                },
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
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
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
