package com.example.arking.data

import android.content.Context
import com.example.arking.model.TaskRoot
import com.example.arking.utils.ReadJSONFromAssets
import com.google.gson.Gson
import javax.inject.Inject

class JsonDataHelper(private val  context: Context) {

    fun getTasks(): TaskRoot {
        val json = ReadJSONFromAssets(context,"TaskList.json")
        return Gson().fromJson(json, TaskRoot::class.java)
    }
}