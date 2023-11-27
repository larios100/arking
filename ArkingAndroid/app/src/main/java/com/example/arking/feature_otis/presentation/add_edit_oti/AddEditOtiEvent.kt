package com.example.arking.feature_otis.presentation.add_edit_oti

import com.example.arking.feature_otis.domain.model.OtiConcepts
import com.example.arking.feature_otis.domain.utils.ConceptType
import com.example.arking.feature_otis.domain.utils.SignType
import java.util.UUID

sealed class AddEditOtiEvent {
    data class SetDescription(val description:String): AddEditOtiEvent()
    data class SetComments(val comments:String): AddEditOtiEvent()
    data class SetDate(val date:String): AddEditOtiEvent()
    data class SetStartDate(val startDate:String): AddEditOtiEvent()
    data class SetEndDate(val endDate:String): AddEditOtiEvent()
    data class AddConcept(val conceptType: ConceptType, val parentConceptId: UUID? = null): AddEditOtiEvent()
    data class DeleteConcept(val concepts: OtiConcepts): AddEditOtiEvent()
    data class EditConcept(val concepts: OtiConcepts): AddEditOtiEvent()
    data class SetConcept(val concept:String): AddEditOtiEvent()
    data class SetQuantity(val quantity: String): AddEditOtiEvent()
    data class SetUnit(val unit: String): AddEditOtiEvent()
    data class SetUnitPrice(val unitPrice: String): AddEditOtiEvent()
    data class SetAmount(val amount: String): AddEditOtiEvent()
    data class AddOtiConceptsToolDefault(val parentConceptId: UUID): AddEditOtiEvent()
    data class SingOti(val signType: SignType): AddEditOtiEvent()
    data class SaveSingOti(val path: String): AddEditOtiEvent()
    object CancelSingOti: AddEditOtiEvent()
    object CancelAddEditConcept: AddEditOtiEvent()
    object SaveConcept: AddEditOtiEvent()
    object SaveConceptAndClose: AddEditOtiEvent()
    object SaveOti: AddEditOtiEvent()
}