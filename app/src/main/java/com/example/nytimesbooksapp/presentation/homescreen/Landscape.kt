package com.example.nytimesbooksapp.presentation.homescreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun IsLandscpe(): Boolean{
    val configuration= LocalConfiguration.current
    return configuration.orientation==android.content.res.Configuration.ORIENTATION_LANDSCAPE
}