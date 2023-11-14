package com.example.arking.presentation.parts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arking.data.contract.ContractDao
import com.example.arking.data.part.PartDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PartsViewModel @Inject constructor (
    partDao: PartDao,
    contractDao: ContractDao,
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    private val _contractId =  savedStateHandle.get<String>("id")
    private val _parts = partDao.getAllByContractId(Integer.parseInt(_contractId))
    private val _contract = contractDao.loadAllByIdFlow(Integer.parseInt(_contractId))
    private val _state = MutableStateFlow(PartsState())
    val state = combine(_state,_parts,_contract) { state, parts, contract ->
        state.copy(
            parts = parts,
            contract = contract
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PartsState())
}