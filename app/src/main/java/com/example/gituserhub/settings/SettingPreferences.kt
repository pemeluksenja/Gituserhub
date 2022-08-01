package com.example.gituserhub.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val THEME_MODE = booleanPreferencesKey("theme_setting")

    fun getThemePreferences(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_MODE] ?: false
        }
    }

    suspend fun saveThemePreferences(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}