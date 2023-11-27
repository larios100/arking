package com.example.arking.feature_otis.domain.uses_cases

import com.example.arking.feature_otis.domain.model.Oti
import com.example.arking.feature_otis.domain.repository.OtiRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class GetOti (
    private val otiRepository: OtiRepository
) {

    operator fun invoke(id: UUID): Flow<Oti> {
        return otiRepository.getOti(id)
    }
}