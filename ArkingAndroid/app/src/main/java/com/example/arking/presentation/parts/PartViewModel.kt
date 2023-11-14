package com.example.arking.presentation.parts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arking.data.part.PartDao
import com.example.arking.model.PartTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class PartViewModel @Inject constructor (
    private val partDao: PartDao,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){
    private val _partId =  savedStateHandle.get<String>("id")
    private val _part = partDao.loadAllByIdFlor(Integer.parseInt(_partId))
    private val _partTasks = partDao.loadAllPartTaskByPartId(Integer.parseInt(_partId))
    private val _partTAttachments = partDao.loadAllPartAttachmentByPartId(Integer.parseInt(_partId))
    private val _state = MutableStateFlow(PartState())
    val state = combine(_state,_part,_partTasks,_partTAttachments) { state, part, partTasks,partAttachments ->
        state.copy(
            part = part,
            partTasks = partTasks,
            partAttachments = partAttachments
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PartState())

    fun onEvent(event: PartEvent){
        when(event){
            is PartEvent.SetCompleted ->{
                viewModelScope.launch {
                    partDao.upsertPartTask(PartTask(Integer.parseInt(_partId),event.taskId, event.completed))
                }
            }
            PartEvent.ToggleGallery -> {
                _state.update {
                    it.copy(showGallery = !it.showGallery)
                }
            }
        }
    }
}