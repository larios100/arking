package com.example.arking.feature_otis.domain.uses_cases

import com.example.arking.R
import com.example.arking.utils.UiText

class ValidationNotEmpty {
    fun execute(text: String): ValidationResult {
        if(text.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.field_required)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}