package com.example.arking.feature_otis.presentation.add_edit_oti

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.arking.feature_otis.domain.model.OtiConcepts
import com.example.arking.feature_otis.domain.uses_cases.OtisUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
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
                _state.value = _state.value.copy(isAddingEditingConcept = true, addingEditingConceptType = event.conceptType)
            }
            is AddEditOtiEvent.CancelAddEditConcept -> {
                _state.value = _state.value.copy(isAddingEditingConcept = false)
            }
            is AddEditOtiEvent.SaveConcept -> {
                _concepts.value.add(
                    OtiConcepts(
                        _state.value.concept,
                        _state.value.unit,
                        _state.value.unitPrice ?: 0.0,
                        _state.value.quantity ?: 0,
                        _state.value.amount ?: 0.0,
                        _state.value.addingEditingConceptType.toString(),
                        UUID.randomUUID()
                    )
                )
                _state.value = _state.value.copy(concepts = _concepts.value)
                _state.value = _state.value.copy(
                    concept = "",
                    unitPrice = null,
                    unit = "",
                    amount = null,
                    quantity = null)
            }
            is AddEditOtiEvent.SaveConceptAndClose -> {
                _concepts.value.add(
                    OtiConcepts(
                        _state.value.concept,
                        _state.value.unit,
                        _state.value.unitPrice ?: 0.0,
                        _state.value.quantity ?: 0,
                        _state.value.amount ?: 0.0,
                        _state.value.addingEditingConceptType.toString(),
                        UUID.randomUUID()
                    )
                )
                _state.value = _state.value.copy(concepts = _concepts.value)
                _state.value = _state.value.copy(
                    isAddingEditingConcept = false,
                    concept = "",
                    unitPrice = null,
                    unit = "",
                    amount = null,
                    quantity = null)
            }
            is AddEditOtiEvent.SetConcept -> {
                _state.value = _state.value.copy(concept = event.concept)
            }
            is AddEditOtiEvent.SetQuantity -> {
                _state.value = _state.value.copy(quantity = parseInt(event.quantity))
            }
            is AddEditOtiEvent.SetUnit -> {
                _state.value = _state.value.copy(unit = event.unit)
            }
            is AddEditOtiEvent.SetUnitPrice -> {
                _state.value = _state.value.copy(unitPrice = parseDouble(event.unitPrice))
            }
            is AddEditOtiEvent.SetAmount -> {
                _state.value = _state.value.copy(amount = parseDouble(event.amount))
            }

        }
    }

    sealed class UiEvent {
        object SaveOti: UiEvent()
    }
}