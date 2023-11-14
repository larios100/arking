package com.example.arking.data.part

import com.example.arking.data.contract.ContractDao
import com.example.arking.model.Contract
import com.example.arking.model.Part
import com.example.arking.model.PartAttachment
import com.example.arking.model.PartTask
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PartRepository @Inject constructor(
    private val partDao: PartDao
) {
    suspend fun createPart(part: Part) {
        var registered = partDao.loadAllById(part.id)
        if(registered == null){
            partDao.insert(part)
        }
        else{
            registered.name = part.name
            registered.description = part.description
            //registered.s = part.status
            partDao.update(registered)
        }
    }
    fun getAll() = partDao.getAll()
    fun getAllByContractId(contractId: Int) = partDao.getAllByContractId(contractId)
    fun loadById(id: Int) = partDao.loadAllByIdFlor(id)
    fun getAllPartTaskByPartId(partId: Int) = partDao.loadAllPartTaskByPartId(partId)
    fun getAllPartAttachmentByPartId(partId: Int) = partDao.loadAllPartAttachmentByPartId(partId)
    suspend fun createPartTask(partTask: PartTask) {
        var registered = partDao.loadPartTaskById(partTask.partId,partTask.taskId)
        if(registered == null){
            partDao.insertPartTask(partTask)
        }
        else{
            registered.isCompleted = partTask.isCompleted
            registered.modifiedOn = LocalDateTime.now()
            partDao.updatePartTask(registered)
        }
    }
    suspend fun createPartAttachment(partAttachment: PartAttachment) {
        partDao.insertPartAttachment(partAttachment)
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: PartRepository? = null

        fun getInstance(partDao: PartDao) =
            instance ?: synchronized(this) {
                instance ?: PartRepository(partDao).also { instance = it }
            }
    }
}