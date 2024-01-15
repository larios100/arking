package com.example.arking.feature_settings.domain.model

import java.util.UUID


data class SyncRequest(
    val parts: List<PartRequest>,
    val otis: List<OtiRequest>,
)

data class PartRequest(
    val id: Int,
    val taks: List<TakRequest>,
    val test: List<TestRequest>,
    val items: List<ItemRequest>,
)

data class TakRequest(
    val taskId: Int,
    val isCompleted: Boolean,
)

data class TestRequest(
    val testId: Int,
    val comments: String,
)

data class ItemRequest(
    val testItemId: Int,
    val attachments: String,
    val testDate: String,
    val fixDate: String,
    val result: String,
    val validation: Boolean,
)

data class OtiRequest(
    val id: String,
    val comments: String,
    val description: String,
    val date: String,
    val startDate: String,
    val endDate: String,
    val signAuditorId: String?,
    val signResidentId: String?,
    val total: Double,
    val concepts: List<ConceptRequest>,
)

data class ConceptRequest(
    val id: String,
    val concept: String,
    val unit: String,
    val unitPrice: Double,
    val quantity: Double,
    val total: Double,
    val otiConceptType: String,
    val parentId: UUID?
)

