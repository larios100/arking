package com.example.arking.presentation.contracts

import com.example.arking.model.Contract

data class ContractState(
    val contracts: List<Contract> = emptyList()
)