package com.example.gituserhub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.gituserhub.settings.SettingPreferences
import kotlinx.coroutines.launch

class ThemeViewModel(private val pref: SettingPreferences): ViewModel() {
    fun getThemePreferences(): LiveData<Boolean> {
        return pref.getThemePreferences().asLiveData()
    }

    fun saveThemePreferences(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemePreferences(isDarkModeActive)
        }
    }
}