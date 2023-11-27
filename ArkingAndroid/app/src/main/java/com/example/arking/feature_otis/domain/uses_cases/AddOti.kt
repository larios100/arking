package com.example.arking.feature_otis.domain.uses_cases

import com.example.arking.R
import com.example.arking.feature_otis.domain.model.Oti
import com.example.arking.feature_otis.domain.model.OtiConcepts
import com.example.arking.feature_otis.domain.repository.OtiRepository
import com.example.arking.feature_otis.domain.utils.Resource
import com.example.arking.feature_otis.domain.utils.UiText

class AddOti(
    private val otiRepository: OtiRepository
) {
    suspend operator fun invoke(oti: Oti, concepts: List<OtiConcepts>): Resource<Unit> {
        if(oti.description.isBlank())
            return Resource.Error(UiText.StringResource(R.string.description_required))
        oti.total = concepts.sumOf { it.total }
        otiRepository.upsetOti(oti)
        concepts.forEach{
            it.otiId = oti.id
            otiRepository.upsertOtiConcept(it)
        }
        return Resource.Success(null)
    }
}