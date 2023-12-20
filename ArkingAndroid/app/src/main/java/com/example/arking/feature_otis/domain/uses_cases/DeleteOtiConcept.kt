package com.example.arking.feature_otis.domain.uses_cases

import com.example.arking.feature_otis.domain.model.OtiConcepts
import com.example.arking.feature_otis.domain.repository.OtiRepository
import com.example.arking.feature_otis.util.TotalOtiHelper
import com.example.arking.utils.Resource

class DeleteOtiConcept(
    private val otiRepository: OtiRepository
) {
    suspend operator fun invoke(concepts: OtiConcepts): Resource<Unit> {
        if(concepts.parentConceptId != null){
            var conceptsSaved = otiRepository.getOtiConceptWithChild(concepts.parentConceptId!!)
            val total = conceptsSaved.filter { it.parentConceptId == concepts.parentConceptId && it.id != concepts.id }
                .sumOf { it.total }
            val parent = conceptsSaved.first { it.id == concepts.parentConceptId }
            parent.unitPrice = total
            parent.subtotal = parent.quantity * total
            parent.total = TotalOtiHelper(parent.subtotal).getTotalAmount()
            otiRepository.deleteOtiConcept(concepts)
            otiRepository.upsertOtiConcept(parent)
        }
        else{
            val children = otiRepository.getOtiConceptWithChild(concepts.id)
            children.forEach {
                otiRepository.deleteOtiConcept(it)
            }
        }

        return Resource.Success(null)
    }
}