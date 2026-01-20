package com.example.worldclockapp.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldclockapp.domain.usecase.GetTimeZonesUseCase
import com.example.worldclockapp.presentation.mvi.WorldClockIntent
import com.example.worldclockapp.presentation.mvi.WorldClockReducer
import com.example.worldclockapp.presentation.mvi.WorldClockState
import com.example.worldclockapp.ui.theme.KeyPrefs
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter



@HiltViewModel
class WorldClockViewModel @Inject constructor(
    private val getZones: GetTimeZonesUseCase,private val datastore: KeyPrefs
) : ViewModel() {
    private val reducer = WorldClockReducer()
    private val _state = MutableStateFlow(WorldClockState(isLoading = true))
    val state = _state.asStateFlow()
    private val _utcNow = MutableStateFlow(Instant.now())
    val utcNow = _utcNow.asStateFlow()
    init {
        handleIntent(WorldClockIntent.LoadZones)
        viewModelScope.launch {
            while (true) {
                _utcNow.value = Instant.now()
                delay(1_000)
            }
        }
    }
    fun handleIntent(intent: WorldClockIntent) {
        when (intent) {
            is WorldClockIntent.LoadZones -> loadZones()
            is WorldClockIntent.ToggleTheme ->toggleTheme(intent.isDark)
            else -> _state.update { reducer.reduce(it, intent) }
        }
    }
    private fun loadZones() = viewModelScope.launch {
        try {
            _state.update { it.copy(isLoading = true, error = null) }
            val zones = getZones()
            _state.update { it.copy(zones = zones, filteredZones = zones, isLoading = false) }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, error = e.message ?: "Unknown error") }
        }
    }
    fun formatTime(zoneId: String, use24h: Boolean, instant: Instant): String {
        val zoned = ZonedDateTime.ofInstant(instant, ZoneId.of(zoneId))
        val pattern = if (use24h) "HH:mm:ss" else "hh:mm:ss a"
        return DateTimeFormatter.ofPattern(pattern).format(zoned)
    }
    private fun toggleTheme(isDark: Boolean) {
        viewModelScope.launch {
            datastore.saveTheme(isDark)
            _state.update { it.copy(isDarkMode = isDark) }
        }
    }
    fun formatDate(zoneId: String, instant: Instant): String {
        val zoned = ZonedDateTime.ofInstant(instant, ZoneId.of(zoneId))
        return DateTimeFormatter.ofPattern("EEE, dd MMM yyyy").format(zoned)
    }
}