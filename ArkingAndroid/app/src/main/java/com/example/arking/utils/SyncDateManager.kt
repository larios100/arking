package com.example.arking.utils

import android.content.Context
import com.example.arking.utils.Constants.SYNC_DATE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SyncDateManager @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE)

    var SyncDate: String?
        get() = sharedPreferences.getString(SYNC_DATE, null)
        set(value) {
            sharedPreferences.edit().putString(SYNC_DATE, value).apply()
        }
}