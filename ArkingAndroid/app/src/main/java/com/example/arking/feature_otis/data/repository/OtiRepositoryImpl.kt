package com.example.arking.feature_otis.data.repository

import com.example.arking.feature_otis.data.data_source.OtiDao
import com.example.arking.feature_otis.domain.model.Oti
import com.example.arking.feature_otis.domain.repository.OtiRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class OtiRepositoryImpl(
    private val dao: OtiDao
): OtiRepository {
    override fun getOtis(): Flow<List<Oti>> {
        return dao.getOtis()
    }

    override suspend fun getOti(id: UUID): Oti {
        return dao.getOtiById(id)
    }

    override suspend fun upsetOti(oti: Oti) {
        dao.upsertOtu(oti)
    }
}