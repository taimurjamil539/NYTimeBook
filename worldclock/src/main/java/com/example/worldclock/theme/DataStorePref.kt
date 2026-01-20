package com.example.worldclockapp.ui.theme

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "Settings")
class KeyPrefs @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        val IS_DARK_MODE = booleanPreferencesKey("IS_DARK_MODE")
    }
    val isDarkMode: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_DARK_MODE] ?: false
    }
    suspend fun saveTheme(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = isDark
        }
    }
}
