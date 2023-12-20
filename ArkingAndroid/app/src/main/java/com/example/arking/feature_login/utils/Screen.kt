package com.example.arking.feature_login.utils

sealed class Screen(val route: String) {
    object LoginScreen: Screen("login")
}
