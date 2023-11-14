package com.example.arking.presentation.tests

import com.example.arking.model.TestWithDataItem

sealed class TestEvent{
    data class SetComments(val comments:String):TestEvent()
    data class save(val testItems: List<TestWithDataItem>): TestEvent()
    object ToggleGallery:TestEvent()
}
