package com.example.dilemario.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dilemario.ui.screens.AdminDilemasScreen
import com.example.dilemario.ui.screens.ConfigScreen
import com.example.dilemario.ui.screens.CreateAccountScreen
import com.example.dilemario.ui.screens.CreateDilemmaScreen
import com.example.dilemario.ui.screens.DilemaPagerScreen
import com.example.dilemario.ui.screens.SplashScreen
import com.example.dilemario.ui.screens.FeedScreen
import com.example.dilemario.ui.screens.DetailScreen
import com.example.dilemario.ui.screens.EditDilemmaScreen
import com.example.dilemario.ui.screens.LoginScreen
import com.example.dilemario.ui.screens.ProfileScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "adminDilemas"   // ⭐ Ahora inicia directamente en el pager tipo TikTok
    ) {

        // ⭐ FEED TIPO TIKTOK CON SWIPE HACIA ARRIBA / ABAJO

        composable("config") {
            ConfigScreen(navController)
        }

        composable("adminDilemas") {
            AdminDilemasScreen(navController)
        }

        composable("editDilemma/{id}") { backStackEntry ->
            val dilemaId = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
            EditDilemmaScreen(navController = navController, dilemaId = dilemaId)
        }


        composable("pager") {
            DilemaPagerScreen(navController)
        }

        composable("splash") {
            SplashScreen(navController)
        }

        composable("crearDilema") {
            CreateDilemmaScreen(navController)
        }

        composable("feed") {
            FeedScreen(navController)
        }

        composable("detail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
            DetailScreen(navController, id)
        }

        composable("crearCuenta") {
            CreateAccountScreen(
                onRegister = { /* lógica */ },
                onBack = { navController.popBackStack() }
            )
        }

        composable("login") {
            LoginScreen(
                onLogin = { /* validar login */ },
                onGoToRegister = { navController.navigate("crearCuenta") }
            )
        }

        composable("profile") { ProfileScreen(navController) }

    }
}
