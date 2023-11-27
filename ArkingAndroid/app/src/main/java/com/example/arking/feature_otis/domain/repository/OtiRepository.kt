package com.example.arking.feature_otis.domain.repository

import com.example.arking.feature_otis.domain.model.Oti
import com.example.arking.feature_otis.domain.model.OtiConcepts
import java.util.UUID
import kotlinx.coroutines.flow.Flow

interface OtiRepository {
    fun getOtis(): Flow<List<Oti>>
    fun getOti(id: UUID): Flow<Oti>
    suspend fun upsetOti(oti: Oti)
    suspend fun getOtiConceptWithChild(id: UUID): List<OtiConcepts>
    fun getOtiConceptsByOti(otiId: UUID): Flow<List<OtiConcepts>>
    suspend fun upsertOtiConcept(otiConcepts: OtiConcepts)
    suspend fun deleteOtiConcept(otiConcepts: OtiConcepts)
}