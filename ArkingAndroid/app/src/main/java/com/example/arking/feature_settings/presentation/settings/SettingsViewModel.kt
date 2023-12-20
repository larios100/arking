package com.example.arking.feature_settings.presentation.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arking.feature_settings.domain.uses_cases.SettingsUsesCases
import com.example.arking.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel  @Inject constructor(
    private val settingsUsesCases: SettingsUsesCases
): ViewModel() {
    private val _state = mutableStateOf(SettingsState())
    val state: State<SettingsState> = _state
    private val eventChannel = Channel<UiEvent>()
    val events = eventChannel.receiveAsFlow()
    fun onEvent(events: SettingsEvents){
        when(events){
            is SettingsEvents.Sync -> {
                _state.value = _state.value.copy(loading = true)
                viewModelScope.launch {
                    val response = settingsUsesCases.sync(LocalDate.now().minusYears(1).toString())
                    _state.value = _state.value.copy(loading = false)
                    if(response is Resource.Success){
                        _state.value = _state.value.copy(error = null)
                        eventChannel.send(UiEvent.SyncSuccess)
                    }
                    else{
                        _state.value = _state.value.copy(error = response.message)
                    }
                }
            }
        }
    }
}
sealed class UiEvent {
    object SyncSuccess: UiEvent()
}