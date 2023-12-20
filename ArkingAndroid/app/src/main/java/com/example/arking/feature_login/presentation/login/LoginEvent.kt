package com.example.arking.feature_login.presentation.login


sealed class LoginEvent {
    data class SetUserName(val userName:String): LoginEvent()
    data class SetPassword(val password:String): LoginEvent()
    object Login: LoginEvent()
}