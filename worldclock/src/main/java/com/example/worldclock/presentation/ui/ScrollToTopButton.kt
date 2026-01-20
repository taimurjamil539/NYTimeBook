package com.example.worldclockapp.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ScrollToTopButton(
    listState: androidx.compose.foundation.lazy.LazyListState,
    isDark: Boolean = false
) {
    val coroutineScope = rememberCoroutineScope()
    val showButton by remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }
    val pulse = rememberInfiniteTransition(label = "pulse")
    val scale by pulse.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            tween(1200, easing = LinearEasing),
            RepeatMode.Reverse
        ),
        label = "scale"
    )
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn(tween(400)) + scaleIn(),
            exit = fadeOut(tween(300)) + scaleOut(),
            modifier = Modifier
                .padding(end = 24.dp, bottom = 30.dp) // ✅ perfect bottom-right
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        shadowElevation = 18f
                    }
                    .clip(RoundedCornerShape(50))
                    .background(
                        brush = Brush.radialGradient(
                            colors = if (isDark)
                                listOf(Color(0xFF0FF0FC), Color(0xFF1B1B2F))
                            else
                                listOf(Color(0xFF2196F3), Color(0xFFE3F2FD))
                        )
                    )
                    .border(
                        width = 2.dp,
                        brush = Brush.linearGradient(
                            if (isDark)
                                listOf(Color(0xFFB794F4), Color(0xFF70E1FF))
                            else
                                listOf(Color(0xFF42A5F5), Color(0xFFAB47BC))
                        ),
                        shape = RoundedCornerShape(50)
                    )
                    .clickable {
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Scroll to Top",
                    tint = if (isDark) Color.White else Color(0xFF0D47A1),
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}