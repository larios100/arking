package com.example.arking.feature_otis.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.UUID

@Entity
data class Oti(
    val number: String,
    val description: String,
    val comments: String,
    val date: String,
    val startDate: String,
    val endDate: String,
    var total: Double,
    @ColumnInfo(name = "sign_resident") var signResident: String? = null,
    @ColumnInfo(name = "sign_auditor") var signAuditor: String? = null,
    @ColumnInfo(name = "part_id") var partId: Int? = null,
    @ColumnInfo(name = "modified_on") var modifiedOn: LocalDateTime = LocalDateTime.now(),
    @PrimaryKey val id: UUID = UUID.randomUUID()
)
@Entity
data class OtiConcepts(
    val concept: String,
    val unit: String,
    var unitPrice: Double,
    val quantity: Double,
    var subtotal: Double,
    var total: Double,
    @ColumnInfo(name = "concept_type") val conceptType: String,
    @ColumnInfo(name = "oti_id") var otiId: UUID,
    @ColumnInfo(name = "parent_concept_id") var parentConceptId: UUID? = null,
    @ColumnInfo(name = "modified_on") var modifiedOn: LocalDateTime = LocalDateTime.now(),
    @PrimaryKey val id: UUID = UUID.randomUUID()
)