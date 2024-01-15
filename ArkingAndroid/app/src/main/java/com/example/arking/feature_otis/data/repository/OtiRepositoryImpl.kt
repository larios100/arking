package com.example.arking.feature_otis.data.repository

import com.example.arking.feature_otis.data.data_source.OtiDao
import com.example.arking.feature_otis.domain.model.Oti
import com.example.arking.feature_otis.domain.model.OtiConcepts
import com.example.arking.feature_otis.domain.repository.OtiRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class OtiRepositoryImpl(
    private val dao: OtiDao
): OtiRepository {
    override fun getOtis(): Flow<List<Oti>> {
        return dao.getOtis()
    }

    override fun getOti(id: UUID): Flow<Oti> {
        return dao.getOtiById(id)
    }

    override suspend fun upsetOti(oti: Oti) {
        dao.upsertOtu(oti)
    }

    override suspend fun getOtiConceptWithChild(id: UUID): List<OtiConcepts> {
        return dao.getOtiConceptWithChild(id)
    }

    override fun getOtiConceptsByOti(otiId: UUID): Flow<List<OtiConcepts>> {
        return dao.getOtiConceptsByOtiId(otiId)
    }

    override suspend fun upsertOtiConcept(otiConcepts: OtiConcepts) {
        dao.upsertOtiConcept(otiConcepts)
    }

    override suspend fun deleteOtiConcept(otiConcepts: OtiConcepts) {
        dao.deleteOtiConcept(otiConcepts)
    }

    override suspend fun getOtisToSync(startDate: String): List<Oti>{
        return dao.getOtisToSync(startDate)
    }

    override suspend fun getOtiConceptsByOtiIdSuspend(otiId: UUID): List<OtiConcepts>{
        return dao.getOtiConceptsByOtiIdSuspend(otiId)
    }
}