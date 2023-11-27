package com.example.arking.feature_otis.domain.uses_cases

import com.example.arking.feature_otis.domain.utils.UiText

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)