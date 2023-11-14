package com.example.arking.presentation.tests

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arking.data.JsonDataHelper
import com.example.arking.data.test.TestDao
import com.example.arking.model.GalleryItem
import com.example.arking.model.PartTask
import com.example.arking.model.PartTest
import com.example.arking.model.PartTestAttachment
import com.example.arking.model.PartTestItem
import com.example.arking.model.Test
import com.example.arking.model.TestWithData
import com.example.arking.model.TestWithDataItem
import com.example.arking.presentation.parts.PartEvent
import com.example.arking.presentation.parts.PartState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Integer.parseInt
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor (
    private val testDao: TestDao,
    savedStateHandle: SavedStateHandle,
    application: Application,
) : AndroidViewModel(application){
    private val _jsonDataHelper = JsonDataHelper(application.applicationContext)
    private val _partId =  parseInt(savedStateHandle.get<String>("partId"))
    private val _testId =  parseInt(savedStateHandle.get<String>("testId"))
    private val _data = _jsonDataHelper.getTasks()
    private val _partTest = testDao.loadTestByPartIdAndTestId(_partId, _testId)
    private val _partTestAttachment = testDao.getAllAttachmentByTestId(_partId,_testId)
    private val _partTestItem = testDao.getAllTestItemByPartId(_partId,_partId.toString() +"_"+_testId.toString())
    private val _state = MutableStateFlow<TestState>(TestState())
    private val saveEventChannel = Channel<Boolean>()
    val saveEvent = saveEventChannel.receiveAsFlow()
    var test: Test = Test(0,"", emptyList())
        private set
    val parts = combine(_partTest,_partTestItem, _partTestAttachment) { partTest,partTestItem, partTestAttachment ->
        Log.i("combine",state.toString())
        _state.update {
            it.copy(
                comments = if(partTest == null) it.comments else partTest.comments,
                test =  mergeData(partTest),
                items = mergeData(partTestItem),
                photos = mergeData(partTestAttachment)
            )
        }
    }.stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000), null)
    val state = _state.asStateFlow()
    private fun mergeData(
        partTest: PartTest?
    ): TestWithData {
        var comments = ""
        if (partTest?.comments != null)
            comments = partTest?.comments ?: ""
        return TestWithData(test.id, test.name, comments)
    }
    private fun mergeData(
        partTestAttacment: List<PartTestAttachment>
    ): List<GalleryItem> {
        var result: ArrayList<GalleryItem> = ArrayList<GalleryItem>()
        partTestAttacment.forEach(){ attach ->
            result.add(GalleryItem(attach.partId,attach.path))
        }
        return result
    }
    private fun mergeData(
        partTestItem: List<PartTestItem>
    ): ArrayList<TestWithDataItem> {
        val items = ArrayList<TestWithDataItem>()
        test.items.forEach { item ->
            val tmp = partTestItem.firstOrNull { it.testItemId == item.id }
            if (tmp != null)
                items.add(
                    TestWithDataItem(
                        item.id,
                        item.name,
                        item.description,
                        tmp.testDate,
                        tmp.fixDate,
                        tmp.result,
                        tmp.validation
                    )
                )
            else
                items.add(TestWithDataItem(item.id, item.name, item.description, "Abrir calendario", "Abrir calendario", "", false))
        }
        return items
    }
    fun onEvent(event: TestEvent){
        when(event){
            is TestEvent.SetComments -> {
                _state.update {
                    it.copy(comments = event.comments)
                }
            }
            is TestEvent.ToggleGallery -> {
                _state.update {
                    it.copy(showGallery = !it.showGallery)
                }
            }
            is TestEvent.save -> {
                viewModelScope.launch {
                    val complexId = _partId.toString() + "_" + _testId.toString()
                    testDao.upsertPartTest(
                        PartTest(complexId,
                        _testId,
                        _partId,
                        _state.value.comments,
                        null,
                        null,
                        )
                    )
                    event.testItems.forEach {
                        testDao.upsertPartTestItem(PartTestItem(it.id,
                            _partId,
                            complexId,
                            it.testDate,
                            it.fixDate,
                            it.result,
                            it.validation))
                    }
                    saveEventChannel.send(true)
                }
            }
        }
    }
    init {
        _data.headers.forEach() {header ->
            if(header.tests != null){
                header.tests.forEach(){item ->
                    if(item.id == _testId){
                        test = item
                    }
                }
            }
        }
    }
}