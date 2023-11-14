package com.example.arking.feature_otis.presentation.otis

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arking.feature_otis.domain.uses_cases.OtisUseCases
import com.example.arking.ui.contracts.OtisState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OtisViewModel @Inject constructor(
    private val otisUseCases: OtisUseCases
) : ViewModel() {
    private val _state = mutableStateOf(OtisState())
    val state: State<OtisState> = _state
    private var getOtisJob: Job? = null
    init {
        getOtis()
    }
    fun onEvent(event: OtisEvent){
        when(event){
            is OtisEvent.Get -> {
                getOtis()
            }
        }
    }
    private fun getOtis() {
        getOtisJob?.cancel()
        getOtisJob = otisUseCases.getOtis()
            .onEach { otis ->
                _state.value = state.value.copy(
                    otis = otis
                )
            }
            .launchIn(viewModelScope)
    }
}