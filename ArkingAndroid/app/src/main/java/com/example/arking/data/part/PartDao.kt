package com.example.arking.data.part

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.arking.model.Part
import com.example.arking.model.PartAttachment
import com.example.arking.model.PartTask
import com.example.arking.model.PartWithPrototype
import kotlinx.coroutines.flow.Flow

@Dao
interface PartDao {
    @Query("SELECT * FROM Part")
    fun getAll(): Flow<List<Part>>
    @Query("SELECT Part.id,Part.name,Part.description,Part.contract_id, Prototype.name as prototype FROM Part" +
            " JOIN Prototype ON Part.prototype_id = Prototype.id WHERE contract_id=:contractId")
    fun getAllByContractId(contractId: Int): Flow<List<PartWithPrototype>>

    @Query("SELECT * FROM Part WHERE id IN (:PartIds)")
    fun loadAllByIds(PartIds: IntArray): List<Part>

    @Query("SELECT * FROM Part WHERE id = :PartId")
    fun loadAllById(PartId: Int): Part
    @Query("SELECT * FROM Part WHERE id = :PartId")
    fun loadAllByIdFlor(PartId: Int): Flow<Part>
    @Query("SELECT * FROM Part WHERE name LIKE :name")
    fun loadAllByName(name: String): List<Part>

    @Insert
    fun insertAll(vararg Parts: Part)
    @Insert
    fun insert(Part: Part)
    @Update
    fun update(Part: Part)

    @Delete
    fun delete(Part: Part)

    @Query("SELECT * FROM PartTask WHERE part_id = :partId AND task_id= :taskId")
    fun loadPartTaskById(partId: Int, taskId:Int): PartTask
    @Query("SELECT * FROM PartTask WHERE part_id = :partId")
    fun loadAllPartTaskByPartId(partId: Int): Flow<List<PartTask>>
    @Insert
    fun insertPartTask(partTask: PartTask)
    @Update
    fun updatePartTask(partTask: PartTask)
    @Upsert
    suspend fun upsertPartTask(partTask: PartTask)
    @Insert
    fun insertPartAttachment(partAttachment: PartAttachment)
    @Query("SELECT * FROM PartAttachment WHERE part_id = :partId")
    fun loadAllPartAttachmentByPartId(partId: Int): Flow<List<PartAttachment>>
}