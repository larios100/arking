package com.example.arking.data.task

import com.example.arking.model.TaskAttachment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    suspend fun createTaskAttachment(taskAttachment: TaskAttachment) {
        taskDao.insertTaskAttachment(taskAttachment)
    }
    fun getAllPartAttachmentByTaskId(partId: Int,taskId: Int) = taskDao.getAllAttachmentByTaskId(partId,taskId)
    suspend fun loadPartAttachmentToSync(startDate: String) = taskDao.loadPartAttachmentToSync(startDate)
    companion object {

        // For Singleton instantiation
        @Volatile private var instance: TaskRepository? = null

        fun getInstance(taskDao: TaskDao) =
            instance ?: synchronized(this) {
                instance ?: TaskRepository(taskDao).also { instance = it }
            }
    }
}