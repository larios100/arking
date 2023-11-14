package com.example.arking.feature_otis.domain.repository

import com.example.arking.feature_otis.domain.model.Oti
import java.util.UUID
import kotlinx.coroutines.flow.Flow

interface OtiRepository {
    fun getOtis(): Flow<List<Oti>>
    suspend fun getOti(id: UUID): Oti
    suspend fun upsetOti(oti: Oti)
}