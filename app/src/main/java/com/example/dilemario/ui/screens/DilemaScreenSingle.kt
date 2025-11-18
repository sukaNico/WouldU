package com.example.dilemario.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animate
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
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

// --------------------------------------------------------
// PANTALLA INDIVIDUAL — COMPLETA + ESTADO PERSISTENTE
// --------------------------------------------------------
@Composable
fun DilemaScreenSingle(
    dilema: Dilema,
    respuestaGuardada: String?,               // ← viene del pager
    onRespondido: (String) -> Unit            // ← reportamos cuando selecciona
) {

    // Estado inicial = lo guardado
    var selectedOption by remember { mutableStateOf(respuestaGuardada) }

    // Si el pager nos manda una respuesta que antes no estaba, sincronizamos
    LaunchedEffect(respuestaGuardada) {
        if (respuestaGuardada != null && selectedOption == null) {
            selectedOption = respuestaGuardada
        }
    }

    // Cuando responde por PRIMERA VEZ → reportamos al Pager
    LaunchedEffect(selectedOption) {
        if (selectedOption != null && respuestaGuardada == null) {
            onRespondido(selectedOption!!)
        }
    }

    // Cálculo de porcentajes
    val total = dilema.votosOpcionA + dilema.votosOpcionB
    val percentA = if (total > 0) (dilema.votosOpcionA * 100f) / total else 0f
    val percentB = if (total > 0) (dilema.votosOpcionB * 100f) / total else 0f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = dilema.titulo,
            fontSize = 26.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = dilema.descripcion,
            fontSize = 18.sp,
            color = Color.LightGray
        )

        Spacer(modifier = Modifier.height(40.dp))

        // -------------------------------------------------------
        // OPCIÓN A
        // -------------------------------------------------------
        AnswerOption(
            text = dilema.opcionA,
            isSelected = selectedOption == "A",
            isDisabled = selectedOption != null,
            onClick = { selectedOption = "A" }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // -------------------------------------------------------
        // OPCIÓN B
        // -------------------------------------------------------
        AnswerOption(
            text = dilema.opcionB,
            isSelected = selectedOption == "B",
            isDisabled = selectedOption != null,
            onClick = { selectedOption = "B" }
        )

        Spacer(modifier = Modifier.height(40.dp))

        // -------------------------------------------------------
        // RESULTADOS — SIEMPRE SE MANTIENEN
        // -------------------------------------------------------
        AnimatedVisibility(
            visible = selectedOption != null,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut()
        ) {
            SelectedResult(
                label = if (selectedOption == "A") dilema.opcionA else dilema.opcionB,
                percentage = if (selectedOption == "A") percentA else percentB
            )
        }
    }
}

// --------------------------------------------------------
// COMPONENTE: OPCIÓN CON ANIMACIÓN
// --------------------------------------------------------
@Composable
fun AnswerOption(
    text: String,
    isSelected: Boolean,
    isDisabled: Boolean,
    onClick: () -> Unit
) {

    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.1f else 1f,
        animationSpec = tween(300)
    )

    val alpha by animateFloatAsState(
        targetValue = if (isDisabled && !isSelected) 0.4f else 1f,
        animationSpec = tween(300)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale
            )
            .alpha(alpha)
            .background(
                color = if (isSelected) Color(0xFF4CAF50) else Color(0xFF303030),
                shape = RoundedCornerShape(22.dp)
            )
            .clickable(enabled = !isDisabled) {
                onClick()
            }
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, fontSize = 22.sp, color = Color.White)
    }
}

// --------------------------------------------------------
// COMPONENTE: RESULTADOS
// --------------------------------------------------------
@Composable
fun SelectedResult(label: String, percentage: Float) {

    var animated by remember { mutableStateOf(0f) }

    // Solo animar si es la primera vez que se ve
    LaunchedEffect(Unit) {
        animate(
            initialValue = 0f,
            targetValue = percentage,
            animationSpec = tween(1100)
        ) { value, _ ->
            animated = value
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = "Resultados",
            fontSize = 22.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Tú y el ${animated.toInt()}% de la gente eligieron:",
            fontSize = 18.sp,
            color = Color.LightGray
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = label,
            fontSize = 22.sp,
            color = Color(0xFF4CAF50)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Barra de porcentaje
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
