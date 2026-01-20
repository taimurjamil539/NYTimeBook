package com.example.worldclockapp.presentation.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.worldclockapp.presentation.mvi.WorldClockIntent
import com.example.worldclockapp.presentation.mvi.WorldClockState
import com.example.worldclockapp.presentation.WorldClockViewModel
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SearchBar(vm: WorldClockViewModel, state: WorldClockState, colorScheme: ColorScheme,isDark: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = state.query,
            onValueChange = { vm.handleIntent(WorldClockIntent.Search(it)) },
            modifier = Modifier.weight(1f),
            placeholder = { Text("Search city or zone…") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorScheme.primary,
                unfocusedBorderColor = colorScheme.outline,
                focusedContainerColor = colorScheme.surface,
                unfocusedContainerColor = colorScheme.surface
            )
        )
        Spacer(Modifier.width(8.dp))
        AssistChip(
            onClick = { vm.handleIntent(WorldClockIntent.ToggleFormat) },
            label = { Text(if (state.use24h) "24h" else "12h",color = if (isDark) Color.White.copy(alpha = 0.8f) else Color.Black.copy(alpha = 0.8f)) }
        )
    }
    Spacer(Modifier.height(10.dp))
}
private fun DrawScope.drawLongitude(cx: Float, cy: Float, r: Float, lonDeg: Float, isDark: Boolean) {
    val lon = Math.toRadians(lonDeg.toDouble())
    val color = if (isDark) Color(0xFF3C4A6B) else Color(0xFFB7C4E8)
    val steps = 128
    val pts = (0..steps).map { i ->
        val t = -Math.PI / 2 + i * Math.PI / steps
        val lat = t
        val x = cx + r * cos(lat).toFloat() * sin(lon).toFloat()
        val y = cy + r * 0.7f * sin(lat).toFloat()
        Offset(x, y)
    }
    drawPath(
        Path().apply { moveTo(pts.first().x, pts.first().y); pts.drop(1).forEach { lineTo(it.x, it.y) } },
        color = color.copy(alpha = 0.28f),
        style = Stroke(width = 1.2f)
    )
}
private fun DrawScope.drawLatitude(cx: Float, cy: Float, r: Float, latDeg: Float, isDark: Boolean) {
    val lat = Math.toRadians(latDeg.toDouble())
    val color = if (isDark) Color(0xFF3C4A6B) else Color(0xFFB7C4E8)
    val steps = 128
    val pts = (0..steps).map { i ->
        val t = -Math.PI + i * 2 * Math.PI / steps
        val lon = t
        val x = cx + r * cos(lat).toFloat() * sin(lon).toFloat()
        val y = cy + r * 0.7f * sin(lat).toFloat()
        Offset(x, y)
    }
    drawPath(
        Path().apply { moveTo(pts.first().x, pts.first().y); pts.drop(1).forEach { lineTo(it.x, it.y) } },
        color = color.copy(alpha = 0.22f),
        style = Stroke(width = 1f)
    )
}

