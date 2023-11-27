package com.example.arking.feature_otis.domain.uses_cases

data class OtisUseCases(
    val getOtis: GetOtis,
    val addOti: AddOti,
    val getOti: GetOti,
    val addOtiConcept: AddOtiConcept,
    val addOtiConceptsToolsDefault: AddOtiConceptsToolsDefault,
    val editOtiConcept: EditOtiConcept,
    val deleteOtiConcept: DeleteOtiConcept,
    val getConceptsByOtiId: GetConceptsByOtiId,
    val validationDecimal: ValidationDecimal,
    val validationNotEmpty: ValidationNotEmpty
)