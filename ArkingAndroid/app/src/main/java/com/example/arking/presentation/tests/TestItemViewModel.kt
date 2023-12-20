package com.example.arking.presentation.tests

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.arking.data.JsonDataHelper
import com.example.arking.data.test.TestDao
import com.example.arking.model.GalleryItem
import com.example.arking.model.PartTestItemAttachment
import com.example.arking.ui.contracts.OtisState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TestItemViewModel @Inject constructor (
    private val testDao: TestDao,
    savedStateHandle: SavedStateHandle,
    application: Application
) : AndroidViewModel(application){
    private val _jsonDataHelper = JsonDataHelper(application.applicationContext)
    private val _partId = Integer.parseInt(savedStateHandle.get<String>("partId"))
    private val _testItemId = Integer.parseInt(savedStateHandle.get<String>("testItemId"))
    private val _data = _jsonDataHelper.getTasks()
    private val _state = mutableStateOf(TestItemState())
    val state: State<TestItemState> = _state
    private var getPhotosJob: Job? = null
    init {
        getGallery()
    }
    private fun mergeData(
        partTestAttachment: List<PartTestItemAttachment>
    ): List<GalleryItem> {
        Log.i("mergeData", partTestAttachment.toString())
        var result: ArrayList<GalleryItem> = ArrayList<GalleryItem>()
        partTestAttachment.forEach(){ attach ->
            result.add(GalleryItem(attach.partId,attach.path))
        }
        return result
    }
    public  fun getTestItemName(): String{
        _data.headers.forEach { header ->
            header.tests.forEach { test ->
                test.items.forEach { item ->
                    if(item.id == _testItemId)
                        return item.name
                }
            }
        }
        return "";
    }
    private fun getGallery() {
        getPhotosJob?.cancel()
        getPhotosJob = testDao.getAllPartTestItemAttachmentByTestItemId(_partId,_testItemId)
            .onEach { photos ->
                _state.value = state.value.copy(
                    photos = mergeData(photos)
                )
            }
            .launchIn(viewModelScope)
    }
}