package com.example.arking.ui.part

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.arking.data.contract.ContractRepository
import com.example.arking.data.part.PartRepository
import com.example.arking.data.task.TaskRepository
import com.example.arking.model.Contract
import com.example.arking.model.Part
import com.example.arking.model.PartAttachment
import com.example.arking.model.PartTask
import com.example.arking.model.PartWithPrototype
import com.example.arking.model.TaskAttachment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartsViewModel @Inject constructor (
    private val contractRepository: ContractRepository,
    private val partRepository: PartRepository,
    private  val taskRepository: TaskRepository
) : ViewModel() {

    fun insertPartTask(partTask: PartTask) {
        CoroutineScope(Dispatchers.IO).launch {
            partRepository.createPartTask(partTask)
        }
    }
    fun getContractbyId(id: Int): Flow<Contract> {
        return contractRepository.getByIdFlow(id)
    }
    fun getPartsByContractId(contractId: Int): Flow<List<PartWithPrototype>> {
        return partRepository.getAllByContractId(contractId)
    }
    fun getPartById(id: Int): Flow<Part>{
        return partRepository.loadById(id)
    }
    fun getAllPartTaskByPartId(partId: Int): Flow<List<PartTask>>{
        return partRepository.getAllPartTaskByPartId(partId)
    }
    fun getAllPartAttachmentByPartId(partId: Int): Flow<List<PartAttachment>>{
        return partRepository.getAllPartAttachmentByPartId(partId)
    }
    fun getAllTaskAttachmentByPartId(partId: Int, taskId: Int): Flow<List<TaskAttachment>>{
        return taskRepository.getAllPartAttachmentByTaskId(partId,taskId)
    }
}