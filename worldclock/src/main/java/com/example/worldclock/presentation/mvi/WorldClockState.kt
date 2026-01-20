package com.example.worldclockapp.presentation.mvi
import com.example.worldclockapp.domain.model.TimeZoneModel
data class WorldClockState(
    val zones: List<TimeZoneModel> = emptyList(),
    val filteredZones: List<TimeZoneModel> = emptyList(),
    val query: String = "",
    val use24h: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isDarkMode: Boolean = false,

    )