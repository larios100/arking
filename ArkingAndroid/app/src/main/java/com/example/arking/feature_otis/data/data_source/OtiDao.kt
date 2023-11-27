package com.example.arking.feature_otis.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.arking.feature_otis.domain.model.Oti
import com.example.arking.feature_otis.domain.model.OtiConcepts
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface OtiDao {
    @Query("SELECT * FROM Oti ORDER BY date DESC")
    fun getOtis(): Flow<List<Oti>>

    @Query("SELECT * FROM Oti WHERE id = :id")
    fun getOtiById(id: UUID): Flow<Oti>

    @Upsert
    suspend fun upsertOtu(oti: Oti)
    @Query("SELECT * FROM OtiConcepts WHERE oti_id=:otiId")
    fun getOtiConceptsByOtiId(otiId: UUID): Flow<List<OtiConcepts>>
    @Query("SELECT * FROM OtiConcepts WHERE parent_concept_id=:id OR id=:id")
    suspend fun getOtiConceptWithChild(id: UUID) : List<OtiConcepts>
    @Upsert
    suspend fun upsertOtiConcept(otiConcepts: OtiConcepts)
    @Delete
    suspend fun deleteOtiConcept(otiConcepts: OtiConcepts)
}