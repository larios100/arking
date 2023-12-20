package com.example.arking.feature_login.presentation.login

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arking.feature_login.domain.uses_cases.LoginUsesCases
import com.example.arking.feature_otis.presentation.add_edit_oti.AddEditOtiViewModel
import com.example.arking.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUsesCases: LoginUsesCases
): ViewModel() {
    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state
    private val eventChannel = Channel<UiEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onEvent(event: LoginEvent){
        when(event){
            is LoginEvent.SetUserName -> {
                _state.value = _state.value.copy(userName = event.userName)
            }
            is LoginEvent.SetPassword -> {
                _state.value = _state.value.copy(password = event.password)
            }
            is LoginEvent.Login -> {
                _state.value = _state.value.copy(loading = true)
                viewModelScope.launch {
                    val response = loginUsesCases.validateCredentials(_state.value.userName,_state.value.password)
                    _state.value = _state.value.copy(loading = false)
                    if(response is Resource.Success){
                        _state.value = _state.value.copy(error = null)
                        eventChannel.send(UiEvent.LoginSuccess)
                    }
                    else
                        _state.value = _state.value.copy(error = response.message)
                }
            }
        }
    }
}
sealed class UiEvent {
    object LoginSuccess: UiEvent()
}


