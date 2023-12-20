package com.example.arking.ui.camera

import androidx.lifecycle.ViewModel
import com.example.arking.data.contract.ContractRepository
import com.example.arking.data.part.PartRepository
import com.example.arking.data.task.TaskRepository
import com.example.arking.data.test.TestDao
import com.example.arking.model.PartAttachment
import com.example.arking.model.PartTask
import com.example.arking.model.PartTestAttachment
import com.example.arking.model.PartTestItemAttachment
import com.example.arking.model.TaskAttachment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel  @Inject constructor (
    private val partRepository: PartRepository,
    private val taskRepository: TaskRepository,
    private val testDao: TestDao
) : ViewModel() {
    fun insertPartAttachment(partAttachment: PartAttachment) {
        CoroutineScope(Dispatchers.IO).launch {
            partRepository.createPartAttachment(partAttachment)
        }
    }
    fun insertTaskAttachment(taskAttachment: TaskAttachment){
        CoroutineScope(Dispatchers.IO).launch {
            taskRepository.createTaskAttachment(taskAttachment)
        }
    }
    fun insertTestAttachment(testAttachment: PartTestAttachment){
        CoroutineScope(Dispatchers.IO).launch {
            testDao.insertTestAttachment(testAttachment)
        }
    }
    fun insertTestItemAttachment(testItemAttachment: PartTestItemAttachment){
        CoroutineScope(Dispatchers.IO).launch {
            testDao.insertPartTestItemAttachment(testItemAttachment)
        }
    }
}