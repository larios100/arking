package com.example.arking.feature_otis.domain.uses_cases

import com.example.arking.R
import com.example.arking.feature_otis.domain.model.OtiConcepts
import com.example.arking.feature_otis.domain.repository.OtiRepository
import com.example.arking.feature_otis.domain.utils.Resource
import com.example.arking.feature_otis.domain.utils.UiText
import com.example.arking.feature_otis.util.TotalOtiHelper

class AddOtiConcept(
    private val otiRepository: OtiRepository
) {
    suspend operator fun invoke(concepts: OtiConcepts): Resource<Unit> {
        if(concepts.concept.isBlank())
            return Resource.Error(UiText.StringResource(R.string.field_required), null, "Concept")
        if(concepts.unit.isBlank())
            return Resource.Error(UiText.StringResource(R.string.field_required), null, "Unit")

        if(concepts.parentConceptId != null){
            concepts.subtotal = concepts.unitPrice * concepts.quantity
            concepts.total = concepts.subtotal
            var conceptsSaved = otiRepository.getOtiConceptWithChild(concepts.parentConceptId!!)
            val total = conceptsSaved.filter { it.parentConceptId == concepts.parentConceptId && it.id != concepts.id }
                .sumOf { it.total } + concepts.total

            val parent = conceptsSaved.first { it.id == concepts.parentConceptId }
            parent.unitPrice = total
            parent.subtotal = parent.quantity * total
            parent.total = TotalOtiHelper(parent.subtotal).getTotalAmount()
            otiRepository.upsertOtiConcept(concepts)
            otiRepository.upsertOtiConcept(parent)
        }
        else{
            var conceptsSaved = otiRepository.getOtiConceptWithChild(concepts.id)
            val total = conceptsSaved.filter { it.parentConceptId == concepts.id }
                .sumOf { it.total }
            concepts.unitPrice = total
            concepts.subtotal = concepts.quantity * total
            concepts.total = TotalOtiHelper(concepts.subtotal).getTotalAmount()
            otiRepository.upsertOtiConcept(concepts)
        }

        return Resource.Success(null)
    }
}