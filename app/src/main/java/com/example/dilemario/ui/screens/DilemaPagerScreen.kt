package com.example.dilemario.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.dilemario.ui.screens.DilemaScreenSingle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dilemario.data.MockData
import com.example.dilemario.model.Dilema
import com.example.dilemario.ui.components.BottomNavigationBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DilemaPagerScreen(navController: NavController) {

    // Lista mutable observable
    val dilemas = remember { MockData.dilemas.toMutableStateList() }

    // PagerState
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { dilemas.size + 1 } // +1 para la página de carga
    )

    val respuestas = remember { mutableStateMapOf<Int, String?>() }

    val puedeHacerScroll = derivedStateOf {
        respuestas[pagerState.currentPage] != null || pagerState.currentPage == dilemas.size
    }

    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            VerticalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                userScrollEnabled = puedeHacerScroll.value
            ) { page: Int ->  // explícito para evitar errores de tipo
                if (page < dilemas.size) {
                    val dilema = dilemas[page]
                    DilemaScreenSingle(
                        dilema = dilema,
                        respuestaGuardada = respuestas[page],
                        onRespondido = { opcion: String -> respuestas[page] = opcion } // tipo explícito
                    )
                } else {
                    LoadingMoreDilemas {
                        scope.launch {
                            delay(1500)
                            val nuevosDilemas = MockData.dilemas.shuffled().take(5)
                            dilemas.addAll(nuevosDilemas)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingMoreDilemas(onLoad: () -> Unit) {
    LaunchedEffect(Unit) { onLoad() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF202020)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = Color(0xFF4CAF50))
            Spacer(modifier = Modifier.height(16.dp))
            Text("Cargando más dilemas...", color = Color.White, fontSize = 18.sp)
        }
    }
}
