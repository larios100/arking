package com.example.arking.ui.prototypes

import androidx.lifecycle.ViewModel
import com.example.arking.data.prototype.PrototypeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PrototypesViewModel @Inject constructor (
    private val prototypeRepository: PrototypeRepository
) : ViewModel() {
    val prototypes = prototypeRepository.getAll()
}