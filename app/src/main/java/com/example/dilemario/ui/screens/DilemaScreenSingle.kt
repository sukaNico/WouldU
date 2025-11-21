package com.example.dilemario.ui.screens

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dilemario.model.Dilema
import kotlinx.coroutines.launch


// ---------------------------
// DILEMA SINGLE
// ---------------------------
@Composable
fun DilemaScreenSingle(
    dilema: Dilema,
    respuestaGuardada: String?,
    resultadosGuardados: Pair<Float, Float>? = null,
    onRespondido: (String) -> Unit,
    onResultadoActualizado: (Float, Float) -> Unit
) {
    val scope = rememberCoroutineScope()
    var selectedOption by remember { mutableStateOf(respuestaGuardada) }
    var votosA by remember { mutableIntStateOf(dilema.votosOpcionA) }
    var votosB by remember { mutableIntStateOf(dilema.votosOpcionB) }

    var porcentajeA by remember { mutableStateOf(resultadosGuardados?.first ?: 0f) }
    var porcentajeB by remember { mutableStateOf(resultadosGuardados?.second ?: 0f) }

    LaunchedEffect(respuestaGuardada) {
        if (respuestaGuardada != null && selectedOption == null) {
            selectedOption = respuestaGuardada
        }
    }

    LaunchedEffect(selectedOption) {
        if (selectedOption != null && respuestaGuardada == null) {
            onRespondido(selectedOption!!)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(dilema.titulo, fontSize = 26.sp, color = Color.White)
        Spacer(modifier = Modifier.height(10.dp))
        Text(dilema.descripcion, fontSize = 18.sp, color = Color.LightGray)
        Spacer(modifier = Modifier.height(40.dp))

        AnswerOption(
            text = dilema.opcionA,
            isSelected = selectedOption == "A",
            isDisabled = selectedOption != null
        ) {
            selectedOption = "A"
            scope.launch {
                val resp = RetrofitClient.api.responderDilema(
                    dilema.id,
                    mapOf("opcion_elegida" to "A")
                )

                votosA = resp.data.estadisticas.votos_a
                votosB = resp.data.estadisticas.votos_b

                val total = votosA + votosB
                porcentajeA = if (total > 0) (votosA * 100f) / total else 0f
                porcentajeB = if (total > 0) (votosB * 100f) / total else 0f

                onResultadoActualizado(porcentajeA, porcentajeB)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        AnswerOption(
            text = dilema.opcionB,
            isSelected = selectedOption == "B",
            isDisabled = selectedOption != null
        ) {
            selectedOption = "B"
            scope.launch {
                val resp = RetrofitClient.api.responderDilema(
                    dilema.id,
                    mapOf("opcion_elegida" to "B")
                )

                votosA = resp.data.estadisticas.votos_a
                votosB = resp.data.estadisticas.votos_b

                val total = votosA + votosB
                porcentajeA = if (total > 0) (votosA * 100f) / total else 0f
                porcentajeB = if (total > 0) (votosB * 100f) / total else 0f

                onResultadoActualizado(porcentajeA, porcentajeB)
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        AnimatedVisibility(
            visible = selectedOption != null,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut()
        ) {
            SelectedResult(
                label = if (selectedOption == "A") dilema.opcionA else dilema.opcionB,
                percentage = if (selectedOption == "A") porcentajeA else porcentajeB
            )
        }
    }
}

@Composable
fun AnswerOption(
    text: String,
    isSelected: Boolean,
    isDisabled: Boolean,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(targetValue = if (isSelected) 1.1f else 1f, animationSpec = tween(300))
    val alpha by animateFloatAsState(targetValue = if (isDisabled && !isSelected) 0.4f else 1f, animationSpec = tween(300))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .alpha(alpha)
            .background(
                color = if (isSelected) Color(0xFF4CAF50) else Color(0xFF303030),
                shape = RoundedCornerShape(22.dp)
            )
            .clickable(enabled = !isDisabled) { onClick() }
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, fontSize = 22.sp, color = Color.White)
    }
}

@Composable
fun SelectedResult(label: String, percentage: Float) {
    var animated by remember { mutableStateOf(0f) }

    LaunchedEffect(percentage) {
        androidx.compose.animation.core.animate(
            initialValue = 0f,
            targetValue = percentage,
            animationSpec = tween(1100)
        ) { value, _ -> animated = value }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Resultados", fontSize = 22.sp, color = Color.White)
        Spacer(modifier = Modifier.height(12.dp))
        Text("Tú y el ${animated.toInt()}% de la gente eligieron:", fontSize = 18.sp, color = Color.LightGray)
        Spacer(modifier = Modifier.height(12.dp))
        Text(label, fontSize = 22.sp, color = Color(0xFF4CAF50))
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(Color.DarkGray, RoundedCornerShape(10.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animated / 100f)
                    .background(Color(0xFF4CAF50), RoundedCornerShape(10.dp))
            )
        }
    }
}
