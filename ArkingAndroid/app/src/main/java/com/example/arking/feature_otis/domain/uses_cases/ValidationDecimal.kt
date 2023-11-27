package com.example.arking.feature_otis.domain.uses_cases

import com.example.arking.R
import com.example.arking.feature_otis.domain.utils.UiText
import java.lang.Double.parseDouble

class ValidationDecimal {
    fun execute(text: String): ValidationResult {
        if(text.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.concept_required)
            )
        }
        try {
            val parse = parseDouble(text)
        }
        catch (ex: Exception){
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.invalid_format)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}