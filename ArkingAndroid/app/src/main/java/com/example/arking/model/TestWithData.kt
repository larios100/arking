package com.example.arking.model

data class TestWithData(
    val id:Int,
    val name: String,
    val comments: String)

data class TestWithDataItem(
    val id: Int,
    val name: String,
    val description: String,
    var testDate:String,
    var fixDate: String,
    var result:String,
    var validation: Boolean,
)
