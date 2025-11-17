package com.example.dilemario.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.dilemario.data.MockData
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DilemaPagerScreen(navController: NavController) {

    val dilemas = MockData.dilemas

    // Estado del pager
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { dilemas.size }
    )

    // Guardamos qué dilemas han sido respondidos
    val respuestas = remember { mutableStateMapOf<Int, Boolean>() }

    val scope = rememberCoroutineScope()

    VerticalPager(
        state = pagerState,
        modifier = Modifier,
        // 🚫 Bloquea scroll manual
        userScrollEnabled = false
    ) { page ->

        val dilema = dilemas[page]

        // 👉 Pasamos un "callback" que se dispara cuando el dilema se responde
        DilemaScreenSingle(
            dilema = dilema,
            onRespondido = {

                // Marcar dilema como respondido
                respuestas[page] = true

                // Avanzar solo si no es el último
                if (page < dilemas.size - 1) {
                    scope.launch {
                        pagerState.animateScrollToPage(page + 1)
                    }
                }
            }
        )
    }
}
