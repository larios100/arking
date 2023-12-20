package com.example.arking.utils

import android.content.Context
import android.util.Log
import com.example.arking.utils.Constants.PREF_TOKEN
import com.example.arking.utils.Constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    private var prefs = context.getSharedPreferences(PREF_TOKEN, Context.MODE_PRIVATE)

    fun saveToken(token: String){
        Log.i("TokenManager", token)
        val editor = prefs.edit()
        editor.putString(USER_TOKEN,token)
        editor.apply()
    }

    fun getToken(): String?{
        return prefs.getString(USER_TOKEN,null)
    }
}