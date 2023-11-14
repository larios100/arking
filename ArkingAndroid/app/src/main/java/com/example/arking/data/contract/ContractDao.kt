package com.example.arking.data.contract

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.arking.model.Contract
import kotlinx.coroutines.flow.Flow

@Dao
interface ContractDao {
    @Query("SELECT * FROM Contract")
    fun getAll(): Flow<List<Contract>>


    @Query("SELECT * FROM Contract WHERE id IN (:contractIds)")
    fun loadAllByIds(contractIds: IntArray): List<Contract>

    @Query("SELECT * FROM Contract WHERE id = :contractId")
    fun loadAllByIdFlow(contractId: Int): Flow<Contract>
    @Query("SELECT * FROM Contract WHERE id = :contractId")
    fun loadAllById(contractId: Int): Contract

    @Query("SELECT * FROM Contract WHERE name LIKE :name")
    fun loadAllByName(name: String): List<Contract>

    @Insert
    fun insertAll(vararg contracts: Contract)
    @Insert
    fun insert(contract: Contract)
    @Update
    fun upadate(contract: Contract)

    @Delete
    fun delete(contract: Contract)
}