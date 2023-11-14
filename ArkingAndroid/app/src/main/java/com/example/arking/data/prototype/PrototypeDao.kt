package com.example.arking.data.prototype

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.arking.model.Prototype
import kotlinx.coroutines.flow.Flow

@Dao
interface PrototypeDao {
    @Query("SELECT * FROM Prototype ORDER BY name")
    fun getAll(): Flow<List<Prototype>>


    @Query("SELECT * FROM Prototype WHERE id IN (:PrototypeIds)")
    fun loadAllByIds(PrototypeIds: IntArray): List<Prototype>

    @Query("SELECT * FROM Prototype WHERE id = :PrototypeId")
    fun loadAllById(PrototypeId: Int): Prototype

    @Query("SELECT * FROM Prototype WHERE name LIKE :name")
    fun loadAllByName(name: String): List<Prototype>

    @Insert
    fun insertAll(vararg Prototypes: Prototype)
    @Insert
    fun insert(Prototype: Prototype)
    @Update
    fun upadate(Prototype: Prototype)

    @Delete
    fun delete(Prototype: Prototype)
}