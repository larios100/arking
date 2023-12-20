package com.example.arking.feature_settings.presentation.settings

import com.example.arking.utils.UiText

data class SettingsState(
    val error: UiText? = null,
    val loading: Boolean = false
)