package com.example.arking.presentation.tests

import com.example.arking.feature_otis.domain.utils.SignType
import com.example.arking.model.TestWithDataItem

sealed class TestEvent{
    data class SetComments(val comments:String):TestEvent()
    data class Save(val testItems: List<TestWithDataItem>): TestEvent()
    data class SingTest(val signType: SignType): TestEvent()
    data class SaveSing(val path: String): TestEvent()
    object ToggleGallery:TestEvent()
    object  CancelSing: TestEvent()
}
