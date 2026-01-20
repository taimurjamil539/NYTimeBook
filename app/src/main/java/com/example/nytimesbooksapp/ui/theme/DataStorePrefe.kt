package com.example.nytimesbooksapp.ui.theme

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("Setting")
class Keyprefs @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        val IS_DARK_MOOD = booleanPreferencesKey("IS_DARK_MOOD")
        val LAST_SYNC_DATE = stringPreferencesKey("LAST_SYNC_DATE")
        val SELECTED_DATE=stringPreferencesKey("SELECTED_DATE")

    }

    suspend fun savetheme(isDark: Boolean){
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_MOOD]=isDark
        }
    }
    suspend fun savelastsync(data: String){
        context.dataStore.edit { preferences ->
            preferences[LAST_SYNC_DATE]=data
        }

    }
    suspend fun savedate(date:String){
        context.dataStore.edit {
            preferences ->
            preferences[SELECTED_DATE]=date
        }   }
}