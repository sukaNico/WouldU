package com.example.dilemario.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dilemario.data.DilemaApi
import com.example.dilemario.data.UserPreferences
import com.example.dilemario.model.Dilema
import com.example.dilemario.ui.components.BottomNavigationBar
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DilemaPagerScreen(navController: NavController, userId: Int) {

    val context = LocalContext.current
    val prefs: UserPreferences = remember { UserPreferences(context) }
    val apiService = RetrofitClient.api

    var dilemas by remember { mutableStateOf<List<DilemaApi>>(emptyList()) }
    var cargandoInicio by remember { mutableStateOf(true) }
    var cargandoMas by remember { mutableStateOf(false) }

    val respuestas = remember { mutableStateMapOf<Int, String?>() }
    val resultados = remember { mutableStateMapOf<Int, Pair<Float, Float>>() } // id -> (porcentajeA, porcentajeB)
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { dilemas.size })
    val scope = rememberCoroutineScope()

    var ultimaPaginaCargada by remember { mutableStateOf(-1) }
    var noHayMasDilemas by remember { mutableStateOf(false) }
    var jobCargarMas: Job? by remember { mutableStateOf(null) }

    // ---------------------------
    // CARGA INICIAL
    // ---------------------------
    LaunchedEffect(Unit) {
        val userToken = prefs.token.first()
        userToken?.let { RetrofitClient.setToken(it) }

        try {
            val response = apiService.getDilemasUnanswered(userId)
            if (response.success) dilemas = response.data
        } catch (e: Exception) {
            Log.e("DILEMA_LOAD", "Error cargando dilemas", e)
        } finally {
            cargandoInicio = false
        }
    }

    // ---------------------------
    // OBSERVAR PAGINA PARA CARGAR MÁS
    // ---------------------------
    LaunchedEffect(dilemas) {
        snapshotFlow { pagerState.currentPage }
            .collectLatest { currentPage ->
                val penultimo = dilemas.size - 2
                if (dilemas.isNotEmpty() && currentPage >= penultimo && !cargandoMas && !noHayMasDilemas && ultimaPaginaCargada < dilemas.size) {
                    jobCargarMas?.cancel()
                    jobCargarMas = scope.launch {
                        cargandoMas = true
                        ultimaPaginaCargada = dilemas.size
                        try {
                            val nuevosDilemas = apiService.getDilemasUnanswered(userId)
                            if (nuevosDilemas.success && nuevosDilemas.data.isNotEmpty()) {
                                dilemas = dilemas + nuevosDilemas.data
                            } else {
                                noHayMasDilemas = true
                            }
                        } catch (e: Exception) {
                            Log.e("DILEMA_LOAD", "Error cargando más dilemas", e)
                        } finally {
                            cargandoMas = false
                        }
                    }
                }
            }
    }

    // ---------------------------
    // UI
    // ---------------------------
    Scaffold(bottomBar = { BottomNavigationBar(navController) }) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (cargandoInicio) {
                LoadingMoreDilemas()
            } else {
                VerticalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize(),
                    userScrollEnabled = respuestas.getOrElse(pagerState.currentPage) { null } != null
                ) { page ->
                    val d = dilemas.getOrNull(page)
                    if (d != null) {
                        DilemaScreenSingle(
                            dilema = Dilema(
                                id = d.id,
                                titulo = d.titulo,
                                descripcion = d.descripcion,
                                opcionA = d.opcion_a,
                                opcionB = d.opcion_b,
                                votosOpcionA = 0,
                                votosOpcionB = 0,
                                categoria = d.categoria
                            ),
                            respuestaGuardada = respuestas[page],
                            resultadosGuardados = resultados[d.id],
                            onRespondido = { opcion ->
                                respuestas[page] = opcion
                            },
                            onResultadoActualizado = { a, b ->
                                resultados[d.id] = Pair(a, b)
                            }
                        )
                    }
                }

                if (cargandoMas) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 32.dp)
                    ) {
                        CircularProgressIndicator(color = Color(0xFF4CAF50))
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingMoreDilemas() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF202020)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = Color(0xFF4CAF50))
            Spacer(modifier = Modifier.height(16.dp))
            Text("Cargando dilemas...", color = Color.White, fontSize = 18.sp)
        }
    }
}