package com.example.arking.feature_otis.data.data_source

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.arking.feature_otis.domain.model.Oti
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface OtiDao {
    @Query("SELECT * FROM Oti")
    fun getOtis(): Flow<List<Oti>>

    @Query("SELECT * FROM Oti WHERE id = :id")
    suspend fun getOtiById(id: UUID): Oti

    @Upsert
    suspend fun upsertOtu(oti: Oti)
}