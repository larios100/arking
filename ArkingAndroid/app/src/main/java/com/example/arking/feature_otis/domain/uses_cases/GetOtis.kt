package com.example.arking.feature_otis.domain.uses_cases

import com.example.arking.feature_otis.domain.model.Oti
import com.example.arking.feature_otis.domain.repository.OtiRepository
import kotlinx.coroutines.flow.Flow


class GetOtis(
    private val otiRepository: OtiRepository
) {

    operator fun invoke(): Flow<List<Oti>> {
        return otiRepository.getOtis()
    }
}