package com.example.dilemario.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dilemario.ui.screens.DilemaPagerScreen
import com.example.dilemario.ui.screens.SplashScreen
import com.example.dilemario.ui.screens.FeedScreen
import com.example.dilemario.ui.screens.DetailScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "pager"   // ⭐ Ahora inicia directamente en el pager tipo TikTok
    ) {

        // ⭐ FEED TIPO TIKTOK CON SWIPE HACIA ARRIBA / ABAJO
        composable("pager") {
            DilemaPagerScreen(navController)
        }

        composable("splash") {
            SplashScreen(navController)
        }

        composable("feed") {
            FeedScreen(navController)
        }

        composable("detail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
            DetailScreen(navController, id)
        }
    }
}
