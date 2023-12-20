package com.example.arking.feature_otis.presentation.add_edit_oti

import com.example.arking.feature_otis.domain.model.OtiConcepts
import com.example.arking.feature_otis.domain.utils.ConceptType
import com.example.arking.feature_otis.domain.utils.SignType
import com.example.arking.utils.UiText
import java.util.UUID

data class AddEditOtiState(
    val description: String = "",
    val descriptionError: UiText? = null,
    val comments: String = "",
    val date: String = "Abrir",
    val startDate: String = "Abrir",
    val endDate: String = "Abrir",
    val isAddingEditingConcept: Boolean = false,
    val isSingning: Boolean = false,
    val addingEditingConceptType: ConceptType = ConceptType.Concept,
    val currentOtiConceptId: UUID = UUID.randomUUID(),
    val parentConceptId: UUID? = null,
    val signType: SignType? = null,
    val signResident: String? = null,
    val signAuditor: String? = null,
    val concepts: List<OtiConcepts> = emptyList(),
    //Add or edit
    val conceptId: UUID? = null,
    val concept: String = "",
    val conceptError: UiText? = null,
    val unit: String = "",
    val unitError: UiText? = null,
    val unitPrice: String = "",
    val amount: String = "",
    val quantity: String = ""
)
