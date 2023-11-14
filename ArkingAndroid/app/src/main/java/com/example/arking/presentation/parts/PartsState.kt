package com.example.arking.presentation.parts

import com.example.arking.model.Contract
import com.example.arking.model.PartWithPrototype

data class PartsState(
    val parts: List<PartWithPrototype> = emptyList(),
    val contract: Contract? = null
)