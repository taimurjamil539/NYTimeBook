package com.example.worldclockapp.presentation.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun DesignBackground(isDark: Boolean) {
    val infinite = rememberInfiniteTransition(label = "bg")
    val offset by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(25000, easing = LinearEasing),
            RepeatMode.Reverse
        ),
        label = "anim"
    )

    val colors = if (isDark)
        listOf(Color(0xFF090C10), Color(0xFF10141C), Color(0xFF1F1F2E))
    else
        listOf(Color(0xFFF9FAFF), Color(0xFFE6EEFF), Color(0xFFDDE7F7))

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawRect(
            Brush.linearGradient(
                colors = colors,
                start = Offset(offset, 0f),
                end = Offset(0f, offset)
            )
        )
    }
}