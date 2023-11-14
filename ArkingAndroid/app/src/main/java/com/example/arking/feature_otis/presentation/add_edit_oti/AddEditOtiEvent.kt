package com.example.arking.feature_otis.presentation.add_edit_oti

import com.example.arking.feature_otis.domain.utils.ConceptType

sealed class AddEditOtiEvent {
    data class SetDescription(val description:String): AddEditOtiEvent()
    data class SetComments(val comments:String): AddEditOtiEvent()
    data class SetDate(val date:String): AddEditOtiEvent()
    data class SetStartDate(val startDate:String): AddEditOtiEvent()
    data class SetEndDate(val endDate:String): AddEditOtiEvent()
    data class AddConcept(val conceptType: ConceptType): AddEditOtiEvent()
    data class SetConcept(val concept:String): AddEditOtiEvent()
    data class SetQuantity(val quantity: String): AddEditOtiEvent()
    data class SetUnit(val unit: String): AddEditOtiEvent()
    data class SetUnitPrice(val unitPrice: String): AddEditOtiEvent()
    data class SetAmount(val amount: String): AddEditOtiEvent()
    object CancelAddEditConcept: AddEditOtiEvent()
    object SaveConcept: AddEditOtiEvent()
    object SaveConceptAndClose: AddEditOtiEvent()
}