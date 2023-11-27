package com.example.arking.feature_otis.domain.uses_cases

import com.example.arking.feature_otis.domain.model.OtiConcepts
import com.example.arking.feature_otis.domain.repository.OtiRepository
import com.example.arking.feature_otis.domain.utils.Resource
import com.example.arking.feature_otis.util.TotalOtiHelper

class EditOtiConcept (
    private val otiRepository: OtiRepository
) {
    suspend operator fun invoke(concepts: OtiConcepts): Resource<Unit> {
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
            val children = otiRepository.getOtiConceptWithChild(concepts.id)
            val total = children.filter { it.parentConceptId == concepts.id && it.id != concepts.id }
                .sumOf { it.total }
            concepts.unitPrice = total
            concepts.subtotal = concepts.quantity * total
            concepts.total = TotalOtiHelper(concepts.subtotal).getTotalAmount()
            otiRepository.upsertOtiConcept(concepts)
        }

        return Resource.Success(null)
    }
}