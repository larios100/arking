package com.example.arking.feature_otis.presentation.add_edit_oti

import com.example.arking.feature_otis.domain.model.OtiConcepts
import com.example.arking.feature_otis.domain.utils.ConceptType
import java.util.UUID

data class AddEditOtiState(
    val description: String = "",
    val comments: String = "",
    val date: String = "Abrir",
    val startDate: String = "Abrir",
    val endDate: String = "Abrir",
    val isAddingEditingConcept: Boolean = false,
    val addingEditingConceptType: ConceptType = ConceptType.Concept,
    val concepts: List<OtiConcepts> = emptyList(),
    //Add or edit
    val conceptId: UUID? = null,
    val concept: String = "",
    val unit: String = "",
    val unitPrice: Double? = null,
    val amount: Double?= null,
    val quantity: Int? = null
)
