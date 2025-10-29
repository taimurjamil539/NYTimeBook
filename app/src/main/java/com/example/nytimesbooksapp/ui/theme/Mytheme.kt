package com.example.nytimesbooksapp.ui.theme

import android.app.Activity
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorPalette = lightColorScheme(

    primary = Color(0xFFFF7043),
    onPrimary = Color.White,
    secondary = Color(0xFFFFCA28),
    background = Color(0xFFFACDB7),
    surface = Color.White,
    onSurface = Color(0xFF2B1B12),

    )

private val DarkColorPalette = darkColorScheme(
    primary = Color(0xFFFF8A65),
    onPrimary = Color(0xFF4A1E0F),
    secondary = Color(0xFFFFE082),
    background = Color(0xFF1A0F0A),
    surface = Color(0xFF261712),
    onSurface = Color(0xFFFFEDE3)
)

private val MyTypography = Typography(
    bodyLarge = androidx.compose.ui.text.TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    titleLarge = androidx.compose.ui.text.TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    )
)

@Composable
fun MyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette



    MaterialTheme(
        colorScheme = colors,
        typography = MyTypography,
        content = content
    )
}
