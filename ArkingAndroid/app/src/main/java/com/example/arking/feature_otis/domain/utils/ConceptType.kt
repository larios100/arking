package com.example.arking.feature_otis.domain.utils

sealed class ConceptType{
    object Concept: ConceptType()
    object Workforce: ConceptType()
    object Material: ConceptType()
    object Tool: ConceptType()
}
