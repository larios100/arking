package com.example.arking.feature_login.presentation.login

import com.example.arking.utils.UiText

data class LoginState(
    val userName: String = "juanjoselarios85@gmail.com",
    val password: String = "12345678",
    val error: UiText? = null,
    val loading: Boolean = false
)