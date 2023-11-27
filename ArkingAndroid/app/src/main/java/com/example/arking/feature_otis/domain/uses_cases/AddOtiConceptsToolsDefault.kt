package com.example.arking.feature_otis.domain.uses_cases

import com.example.arking.feature_otis.domain.model.OtiConcepts
import com.example.arking.feature_otis.domain.repository.OtiRepository
import com.example.arking.feature_otis.domain.utils.ConceptType
import com.example.arking.feature_otis.domain.utils.Resource
import com.example.arking.feature_otis.util.TotalOtiHelper
import java.util.UUID

class AddOtiConceptsToolsDefault (
    private val otiRepository: OtiRepository
) {
    suspend operator fun invoke(otiId: UUID,otiConceptParentId: UUID): Resource<Unit> {
        val conceptsSaved = otiRepository.getOtiConceptWithChild(otiConceptParentId)
        val totalWorkForce = conceptsSaved.filter { it.conceptType == ConceptType.Workforce.toString() }.sumOf { it.total }
        otiRepository.upsertOtiConcept(
            OtiConcepts(
                "HERRAMIENTA MENOR",
                "%",
                totalWorkForce,
                0.02,
                totalWorkForce * 0.02,
                totalWorkForce * 0.02,
                ConceptType.Tool.toString(),
                otiId,
                otiConceptParentId
            )
        )
        otiRepository.upsertOtiConcept(
            OtiConcepts(
                "EQUIPO DE PROTECCION PERSONAL",
                "%",
                totalWorkForce,
                0.03,
                totalWorkForce * 0.03,
                totalWorkForce * 0.03,
                ConceptType.Tool.toString(),
                otiId,
                otiConceptParentId
            )
        )
        val parent = conceptsSaved.first { it.id == otiConceptParentId }
        val total = conceptsSaved.filter { it.parentConceptId == otiConceptParentId }
            .sumOf { it.total } + (totalWorkForce * 0.05)
        parent.unitPrice = total
        parent.subtotal = parent.quantity * total
        parent.total = TotalOtiHelper(parent.subtotal).getTotalAmount()
        otiRepository.upsertOtiConcept(parent)

        return Resource.Success(null)
    }
}