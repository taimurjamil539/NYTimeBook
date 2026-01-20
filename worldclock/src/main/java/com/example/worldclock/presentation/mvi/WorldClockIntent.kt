package com.example.worldclockapp.presentation.mvi


sealed interface WorldClockIntent {
    data object LoadZones : WorldClockIntent
    data class Search(val query: String) : WorldClockIntent
    data object ToggleFormat : WorldClockIntent
    data class ToggleTheme(val isDark: Boolean) : WorldClockIntent

}