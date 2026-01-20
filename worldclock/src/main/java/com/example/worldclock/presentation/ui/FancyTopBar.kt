package com.example.worldclockapp.presentation.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FancyTopBar(
    isDark: Boolean,
    onToggle: () -> Unit
) {
    val shimmer = rememberInfiniteTransition(label = "tb")
    val offset by shimmer.animateFloat(
        initialValue = 0f,
        targetValue = 300f,
        animationSpec = infiniteRepeatable(tween(4000, easing = LinearEasing)),
        label = "off"
    )

    val bgBrush = Brush.horizontalGradient(
        if (isDark)
            listOf(Color(0xFF1E1E2F), Color(0xFF2C2C44))
        else
            listOf(Color(0xFFF9FAFF), Color(0xFFE0E7FF))
    )

    Surface(color = Color.Transparent, shadowElevation = 8.dp) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(bgBrush)
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Title with animated underline
                Box {
                    Text(
                        text = "🌐 World Clock",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (isDark) Color(0xFF70E1FF) else Color(0xFF1565C0)
                    )
                    Canvas(Modifier.matchParentSize().padding(top = 30.dp)) {
                        drawLine(
                            brush = Brush.linearGradient(
                                listOf(
                                    Color.Transparent,
                                    if (isDark) Color(0xFF70E1FF) else Color(0xFF1565C0),
                                    Color.Transparent
                                ),
                                start = Offset(offset - 300f, 0f),
                                end = Offset(offset, 0f)
                            ),
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 4f
                        )
                    }
                }
                Spacer(Modifier.width(12.dp))
                // Theme toggle
                IconButton(
                    onClick = onToggle,
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isDark) Color(0xFF262B40) else Color(0xFFE6EBF8))
                        .border(
                            1.dp,
                            Brush.horizontalGradient(
                                if (isDark)
                                    listOf(Color(0xFF70E1FF), Color(0xFFB794F4))
                                else
                                    listOf(Color(0xFF6EC6FF), Color(0xFFB388FF))
                            ),
                            RoundedCornerShape(12.dp)
                        )
                ) {
                    Icon(
                        imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = "Toggle Theme",
                        tint = if (isDark) Color(0xFF70E1FF) else Color(0xFF1565C0)
                    )
                }
            }
        }
    }
}
