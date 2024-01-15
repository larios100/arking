package com.example.arking.feature_settings.domain.model


data class SyncResponse(
    val contracts: List<ContractResponse>,
    val parts: List<PartResponse>,
    val partTasks: List<PartTaskResponse>,
    val partTests: List<PartTestResponse>,
    val partTestItems: List<PartTestItemResponse>,
    val prototypes: List<PrototypeResponse>,
)

data class ContractResponse(
    val contractId: Int,
    val name: String,
    val description: String,
    val status: String,
    val budget: Long?,
)

data class PartResponse(
    val contractId: Int,
    val partId: Int,
    val name: String,
    val description: String,
    val status: String,
    val prototypeId: Int,
    val ownerUserId: Any?,
)
data class PartTaskResponse(
    val taskId: Int,
    val partId: Int,
    val isCompleted: Boolean,
    val attachments: String,
)

data class PartTestResponse(
    val testId: Int,
    val partId: Int,
    val comments: String,
)

data class PartTestItemResponse(
    val testItemId: Int,
    val partId: Int,
    val testId: Int,
    val attachments: String,
    val testDate: String?,
    val fixDate: String?,
    val result: String,
    val validation: Boolean,
)

data class PrototypeResponse(
    val prototypeId: Int,
    val name: String,
    val description: String,
    val fileId: String,
)