package com.example.arking.feature_otis.domain.uses_cases

import com.example.arking.feature_otis.domain.model.Oti
import com.example.arking.feature_otis.domain.repository.OtiRepository

class AddOti(
    private val otiRepository: OtiRepository
) {
    suspend operator fun invoke(oti: Oti){
        otiRepository.upsetOti(oti)
    }
}