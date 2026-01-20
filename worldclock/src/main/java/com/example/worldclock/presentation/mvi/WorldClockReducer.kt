package com.example.worldclockapp.presentation.mvi

class WorldClockReducer {
    fun reduce(state: WorldClockState, intent: WorldClockIntent): WorldClockState = when (intent) {
        is WorldClockIntent.ToggleFormat -> state.copy(use24h = !state.use24h)
        is WorldClockIntent.Search -> {
            val q = intent.query.trim().lowercase()
            val filtered = if (q.isEmpty()) state.zones else state.zones.filter {
                it.id.lowercase().contains(q) || it.displayName.lowercase().contains(q)
            }
            state.copy(query = intent.query, filteredZones = filtered)
        }
        is WorldClockIntent.ToggleTheme -> {
            state.copy(isDarkMode = intent.isDark)
        }
        is WorldClockIntent.LoadZones -> state
    }
}
