package com.example.worldclockapp.presentation.ui
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
@Composable
fun SplashScreen(onFinish: () -> Unit, isDark: Boolean,navController: NavController) {
    val infiniteTransition = rememberInfiniteTransition(label = "clock")
    val rotateAnim by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = LinearEasing)
        ),
        label = "rotation"
    )
    val scale = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        scale.animateTo(1f, tween(1200, easing = FastOutSlowInEasing))
        delay(2500)
        onFinish()
    }
    val bgGradient = if (isDark)
        listOf(Color(0xFF0D1B2A), Color(0xFF1B263B))
    else
        listOf(Color(0xFFF8FAFF), Color(0xFFE3ECFF))
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = androidx.compose.ui.graphics.Brush.verticalGradient(bgGradient)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Canvas(
                modifier = Modifier
                    .size(150.dp)
                    .graphicsLayer {
                        scaleX = scale.value
                        scaleY = scale.value
                    }
            ) {
                val center = Offset(size.width / 2, size.height / 2)
                val radius = size.minDimension / 2.5f
                drawCircle(
                    color = if (isDark) Color(0xFF70E1FF) else Color(0xFF1565C0),
                    center = center,
                    radius = radius,
                    style = androidx.compose.ui.graphics.drawscope.Stroke(
                        width = 6f,
                        cap = StrokeCap.Round
                    )
                )
                val hourAngle = Math.toRadians((rotateAnim % 360) * 0.5)
                val hourEnd = Offset(
                    center.x + (radius * 0.5f * kotlin.math.sin(hourAngle)).toFloat(),
                    center.y - (radius * 0.5f * kotlin.math.cos(hourAngle)).toFloat()
                )
                drawLine(
                    color = if (isDark) Color(0xFFB794F4) else Color(0xFF5E92F3),
                    start = center,
                    end = hourEnd,
                    strokeWidth = 8f,
                    cap = StrokeCap.Round
                )
                val minuteAngle = Math.toRadians((rotateAnim * 3 % 360).toDouble())
                val minuteEnd = Offset(
                    center.x + (radius * 0.8f * kotlin.math.sin(minuteAngle)).toFloat(),
                    center.y - (radius * 0.8f * kotlin.math.cos(minuteAngle)).toFloat()
                )
                drawLine(
                    color = if (isDark) Color(0xFF70E1FF) else Color(0xFF1565C0),
                    start = center,
                    end = minuteEnd,
                    strokeWidth = 5f,
                    cap = StrokeCap.Round
                )
            }
            Spacer(Modifier.height(30.dp))
            Text(
                text = "Watch Clock",
                color = if (isDark) Color(0xFF70E1FF) else Color(0xFF0D47A1),
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.5.sp
            )
            Text(
                text = "Global Time Companion",
                color = if (isDark) Color(0xFF9FB3C8) else Color(0xFF5C6B80),
                fontSize = 14.sp
            )
        }
    }
}
