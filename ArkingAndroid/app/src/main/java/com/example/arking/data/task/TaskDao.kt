package com.example.arking.data.task

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.arking.model.Part
import com.example.arking.model.TaskAttachment
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM TaskAttachment WHERE part_id=:partId AND task_id=:taskId")
    fun getAllAttachmentByTaskId(partId: Int,taskId: Int): Flow<List<TaskAttachment>>
    @Insert
    fun insertTaskAttachment(taskAttachment: TaskAttachment)
    @Delete
    fun deleteTaskAttachment(taskAttachment: TaskAttachment)
    @Query("SELECT * FROM TaskAttachment WHERE modified_on >= :startDate")
    suspend fun loadPartAttachmentToSync(startDate: String): List<TaskAttachment>
}