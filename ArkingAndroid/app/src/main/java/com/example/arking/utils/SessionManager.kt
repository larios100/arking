package com.example.arking.utils

import android.content.Context

class SessionManager(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE)

    var isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean("isLoggedIn", false)
        set(value) {
            sharedPreferences.edit().putBoolean("isLoggedIn", value).apply()
        }
}