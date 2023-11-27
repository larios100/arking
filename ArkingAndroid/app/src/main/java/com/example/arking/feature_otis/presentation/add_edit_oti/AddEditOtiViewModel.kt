package com.example.arking.feature_otis.presentation.add_edit_oti

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arking.R
import com.example.arking.feature_otis.domain.model.Oti
import com.example.arking.feature_otis.domain.model.OtiConcepts
import com.example.arking.feature_otis.domain.uses_cases.OtisUseCases
import com.example.arking.feature_otis.domain.utils.ConceptType
import com.example.arking.feature_otis.domain.utils.Resource
import com.example.arking.feature_otis.domain.utils.SignType
import com.example.arking.feature_otis.domain.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Double.parseDouble
import java.lang.Integer.parseInt
import java.util.UUID
import javax.inject.Inject
@HiltViewModel
class AddEditOtiViewModel @Inject constructor(
    private val otisUseCases: OtisUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(AddEditOtiState())
    private var _concepts = mutableStateOf(ArrayList<OtiConcepts>())
    val state: State<AddEditOtiState> = _state
    private val saveEventChannel = Channel<UiEvent>()
    val saveEvent = saveEventChannel.receiveAsFlow()
    private var currentOtiId: UUID = if(savedStateHandle.get<String>("otiId")?.isBlank() == true) UUID.randomUUID() else UUID.fromString(savedStateHandle.get<String>("otiId"))
    private val _oti = otisUseCases.getOti(currentOtiId)
    private val _otiConcepts = otisUseCases.getConceptsByOtiId(currentOtiId)
    val otiWithConcepts = combine(_oti, _otiConcepts) { oti, concepts ->
        if(oti != null){
            _state.value = _state.value.copy(
                description = oti.description,
                comments = oti.comments,
                date = if(oti.date == "") "Abrir" else oti.date,
                startDate = if(oti.startDate == "") "Abrir" else oti.startDate,
                signResident = oti.signResident,
                signAuditor = oti.signAuditor,
                endDate = if(oti.endDate == "") "Abrir" else oti.endDate)
        }
        _concepts.value = concepts as ArrayList<OtiConcepts>
        _state.value = _state.value.copy(concepts = _concepts.value)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    init {
        /*savedStateHandle.get<String>("otiId")?.let { otiId ->
            if(otiId.isNotBlank()) {
                viewModelScope.launch {
                    otisUseCases.getOti(UUID.fromString(otiId)).also { oti ->
                        currentOtiId = oti.id
                        _state.value = _state.value.copy(
                            description = oti.description,
                            comments = oti.comments,
                            date = oti.date,
                            startDate = oti.startDate,
                            endDate = oti.endDate)
                    }
                    otisUseCases.getConceptsByOtiId(UUID.fromString(otiId)).also { concepts ->
                        _concepts.value = concepts as ArrayList<OtiConcepts>
                        _state.value = _state.value.copy(
                            concepts = _concepts.value
                        )
                    }
                }
            }
        }*/
    }
    private fun  saveConcept(isAddingEditingConcept: Boolean){
        viewModelScope.launch {
            val result = otisUseCases.addOtiConcept(
                OtiConcepts(
                    concept = _state.value.concept,
                    unit = _state.value.unit,
                    unitPrice = if(_state.value.unitPrice.isBlank()) 0.0 else parseDouble(_state.value.unitPrice),
                    quantity = if(_state.value.quantity.isBlank()) 0.0 else parseDouble(_state.value.quantity),
                    subtotal = 0.0,
                    total = 0.0,
                    conceptType = _state.value.addingEditingConceptType.toString(),
                    otiId = currentOtiId,
                    parentConceptId = _state.value.parentConceptId,
                    id = _state.value.currentOtiConceptId
                    )
            )
            if(result is Resource.Error){
                if(result.field == "Concept"){
                    _state.value = _state.value.copy(
                        conceptError = result.message
                    )
                    return@launch
                }
                if(result.field == "Unit"){
                    _state.value = _state.value.copy(
                        unitError = result.message
                    )
                    return@launch
                }
            }
            else{
                _state.value = _state.value.copy(
                    isAddingEditingConcept = isAddingEditingConcept,
                    concept = "",
                    unitPrice = "",
                    unit = "",
                    amount = "",
                    quantity = "",
                    conceptError = null,
                    unitError = null)
            }
        }
    }
    fun onEvent(event: AddEditOtiEvent){
        when(event){
            is AddEditOtiEvent.SetDescription -> {
                _state.value = _state.value.copy(description = event.description)
            }
            is AddEditOtiEvent.SetComments -> {
                _state.value = _state.value.copy(comments = event.comments)
            }
            is AddEditOtiEvent.SetDate -> {
                _state.value = _state.value.copy(date = event.date)
            }
            is AddEditOtiEvent.SetStartDate -> {
                _state.value = _state.value.copy(startDate = event.startDate)
            }
            is AddEditOtiEvent.SetEndDate -> {
                _state.value = _state.value.copy(endDate = event.endDate)
            }
            is AddEditOtiEvent.AddConcept -> {
                _state.value = _state.value.copy(
                    isAddingEditingConcept = true,
                    addingEditingConceptType = event.conceptType,
                    parentConceptId = event.parentConceptId,
                    currentOtiConceptId = UUID.randomUUID())
            }
            is AddEditOtiEvent.CancelAddEditConcept -> {
                _state.value = _state.value.copy(isAddingEditingConcept = false)
            }
            is AddEditOtiEvent.AddOtiConceptsToolDefault -> {
                viewModelScope.launch {
                    otisUseCases.addOtiConceptsToolsDefault(currentOtiId, event.parentConceptId)
                }
            }
            is AddEditOtiEvent.SaveOti -> {
                val descriptionResult = otisUseCases.validationNotEmpty.execute(state.value.description)
                if(!descriptionResult.successful){
                    _state.value = _state.value.copy(
                        descriptionError = descriptionResult.errorMessage
                    )
                    return
                }
                viewModelScope.launch {
                    val result = otisUseCases.addOti(Oti(
                        "",
                        _state.value.description,
                        _state.value.comments,
                        if(state.value.date == "Abrir") "" else _state.value.date,
                        if(state.value.startDate == "Abrir") "" else _state.value.startDate,
                        if(state.value.endDate == "Abrir") "" else _state.value.endDate,
                        0.0,
                        signResident = _state.value.signResident,
                        signAuditor = _state.value.signAuditor,
                        id = currentOtiId,
                    ), _state.value.concepts)
                    if(result is Resource.Success){
                        saveEventChannel.send(UiEvent.SaveOti)
                    }
                }
            }
            is AddEditOtiEvent.SaveConcept -> {
                saveConcept(true)
            }
            is AddEditOtiEvent.SaveConceptAndClose -> {
                saveConcept(false)
            }
            is AddEditOtiEvent.EditConcept -> {
                _state.value = _state.value.copy(
                    concept = event.concepts.concept,
                    quantity = event.concepts.quantity.toString(),
                    unit = event.concepts.unit,
                    unitPrice = event.concepts.unitPrice.toString(),
                    currentOtiConceptId = event.concepts.id,
                    addingEditingConceptType = ConceptType.valueOf(event.concepts.conceptType) ,
                    parentConceptId = event.concepts.parentConceptId,
                    isAddingEditingConcept = true)
            }
            is AddEditOtiEvent.DeleteConcept -> {
                viewModelScope.launch {
                    otisUseCases.deleteOtiConcept(event.concepts)
                }
            }
            is AddEditOtiEvent.SetConcept -> {
                _state.value = _state.value.copy(concept = event.concept)
            }
            is AddEditOtiEvent.SetQuantity -> {
                _state.value = _state.value.copy(quantity = event.quantity)
            }
            is AddEditOtiEvent.SetUnit -> {
                _state.value = _state.value.copy(unit = event.unit)
            }
            is AddEditOtiEvent.SetUnitPrice -> {
                _state.value = _state.value.copy(unitPrice = event.unitPrice)
            }
            is AddEditOtiEvent.SetAmount -> {
                _state.value = _state.value.copy(amount = event.amount)
            }
            is AddEditOtiEvent.SingOti -> {
                _state.value = _state.value.copy(isSingning = true, signType = event.signType)
            }
            is AddEditOtiEvent.SaveSingOti -> {
                if(_state.value.signType == SignType.Resident){
                    _state.value = _state.value.copy(isSingning = false, signResident = event.path)
                }
                else{
                    _state.value = _state.value.copy(isSingning = false, signAuditor = event.path)
                }
            }
            is AddEditOtiEvent.CancelSingOti -> {
                _state.value = _state.value.copy(isSingning = false)
            }
        }
    }

    sealed class UiEvent {
        object SaveOti: UiEvent()
    }
}