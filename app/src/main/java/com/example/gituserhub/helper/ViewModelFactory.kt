package com.example.gituserhub.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gituserhub.settings.SettingPreferences
import com.example.gituserhub.viewmodel.ThemeViewModel

class ViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}