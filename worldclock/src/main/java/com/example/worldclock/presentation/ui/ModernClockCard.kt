package com.example.worldclockapp.presentation.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.worldclockapp.presentation.WorldClockViewModel

@Composable
fun ModernClockCard(
    vm: WorldClockViewModel,
    name: String,
    id: String,
    instant: org.threeten.bp.Instant,
    use24h: Boolean,
    isDarkMode: Boolean,
    shimmerPhase: Float
) {
    val scaleAnim = remember { Animatable(0.96f) }

    LaunchedEffect(Unit) { scaleAnim.animateTo(1f, tween(500, easing = FastOutSlowInEasing)) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { scaleX = scaleAnim.value; scaleY = scaleAnim.value }
            .shadow(6.dp, RoundedCornerShape(20.dp))
            .background(
                brush = Brush.verticalGradient(
                    if (isDarkMode) listOf(Color(0xFF222639), Color(0xFF2E335A))
                    else listOf(Color.White, Color(0xFFF5F6FA))
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                1.dp,
                Brush.horizontalGradient(
                    if (isDarkMode) listOf(Color(0xFF76D4FF), Color(0xFF957FEF))
                    else listOf(Color(0xFF80D8FF), Color(0xFFB388FF))
                ),
                RoundedCornerShape(20.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
                .drawWithContent {
                    drawContent()
                    val shimmerBrush = Brush.linearGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.White.copy(alpha = 0.10f),
                            Color.Transparent
                        ),
                        start = Offset(-size.width + shimmerPhase * 2 * size.width, 0f),
                        end = Offset(shimmerPhase * 2 * size.width, size.height)
                    )
                    drawRect(shimmerBrush)
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(name, fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = if (isDarkMode) Color.White.copy(alpha = 0.8f) else Color.Black.copy(alpha = 0.8f))
                Text(id, fontSize = 12.sp, color = if (isDarkMode) Color.White.copy(alpha = 0.8f) else Color.Black.copy(alpha = 0.8f))
                Text(vm.formatDate(id, instant), fontSize = 13.sp, color = if (isDarkMode) Color.White.copy(alpha = 0.8f) else Color.Black.copy(alpha = 0.8f))
            }
            Text(
                vm.formatTime(id, use24h, instant),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
                color = if (isDarkMode) Color(0xFF80D8FF) else Color(0xFF1565C0)
            )
        }
    }
}
