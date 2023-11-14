package com.example.arking.feature_otis.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Oti(
    val number: String,
    val description: String,
    val comments: String,
    val date: String,
    val startDate: String,
    val endDate: String,
    @PrimaryKey val id: UUID = UUID.randomUUID()
)
@Entity
data class OtiConcepts(
    val concept: String,
    val unit: String,
    val unitPrice: Double,
    val quantity: Int,
    val amount: Double,
    @ColumnInfo(name = "concept_type") val conceptType: String,
    @ColumnInfo(name = "oti_id") val otiId: UUID,
    @PrimaryKey val id: UUID = UUID.randomUUID()
)