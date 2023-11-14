package com.example.arking.presentation.contracts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arking.data.contract.ContractDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ContractViewModel @Inject constructor (
    private val contractDao: ContractDao
) : ViewModel(){
    private val _contracts = contractDao.getAll()
    private val _state = MutableStateFlow(ContractState())
    val state = combine(_state,_contracts) { state, contracts ->
        state.copy(
            contracts = contracts
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContractState())
}