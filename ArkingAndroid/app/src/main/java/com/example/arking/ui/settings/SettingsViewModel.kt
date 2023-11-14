package com.example.arking.ui.settings

import androidx.lifecycle.ViewModel
import com.example.arking.data.contract.ContractRepository
import com.example.arking.data.part.PartRepository
import com.example.arking.data.prototype.PrototypeRepository
import com.example.arking.model.Contract
import com.example.arking.model.Part
import com.example.arking.model.Prototype
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor (
    private val contractRepository: ContractRepository,
    private val partRepository: PartRepository,
    private val prototypeRepository: PrototypeRepository
    ) : ViewModel() {
    val contracts: Flow<List<Contract>> = contractRepository.getAll()

     fun insertContract(contract: Contract) {
         CoroutineScope(Dispatchers.IO).launch {
            contractRepository.createContract(contract)
        }
    }
    fun insertPart(part: Part) {
        CoroutineScope(Dispatchers.IO).launch {
            partRepository.createPart(part)
        }
    }
    fun insertPrototype(prototype: Prototype){
        CoroutineScope(Dispatchers.IO).launch {
            prototypeRepository.createContract(prototype)
        }
    }
}