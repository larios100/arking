package com.example.arking.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.LocalDateTime
import java.util.UUID

@Entity(tableName = "Contract")
data class Contract (
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "status") var status: String,
)

@Entity(tableName = "Part")
data class Part(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "contract_id") val contractId: Int,
    @ColumnInfo(name = "prototype_id") var prototypeId: Int

)
data class ContractsWithParts(
    @Embedded val contract: Contract,
    @Relation(
        entity = Part::class,
        parentColumn = "id",
        entityColumn = "contract_id"
    )
    val parts: List<Part>
)
@Entity(tableName = "PartTask",primaryKeys = ["part_id", "task_id"])
data class PartTask(
    @ColumnInfo(name = "part_id") val partId: Int,
    @ColumnInfo(name = "task_id") val taskId: Int,
    @ColumnInfo(name = "is_completed") var isCompleted: Boolean,
    @ColumnInfo(name = "modified_on") var modifiedOn: LocalDateTime = LocalDateTime.now(),
)

@Entity(tableName = "PartAttachment",primaryKeys = ["file_id"])
data class PartAttachment(
    @ColumnInfo(name = "part_id") val partId: Int,
    @ColumnInfo(name = "file_id") val fileId: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "path") val path: String,
    @ColumnInfo(name = "modified_on") val modifiedOn: LocalDateTime = LocalDateTime.now(),
)

@Entity(tableName = "TaskAttachment",primaryKeys = ["file_id"])
data class TaskAttachment(
    @ColumnInfo(name = "task_id") val taskId: Int,
    @ColumnInfo(name = "part_id") val partId: Int,
    @ColumnInfo(name = "file_id") val fileId: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "path") val path: String,
    @ColumnInfo(name = "modified_on") val modifiedOn: LocalDateTime = LocalDateTime.now(),
)

@Entity(tableName = "PartTest")
data class PartTest(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "test_id") val testId: Int,
    @ColumnInfo(name = "part_id") val partId: Int,
    @ColumnInfo(name = "comments") var comments: String,
    @ColumnInfo(name = "file_sing_resident_id") var fileSingResidentId: UUID?,
    @ColumnInfo(name = "file_sing_company_id") var fileSingCompanyId: UUID?,
    @ColumnInfo(name = "modified_on") var modifiedOn: LocalDateTime = LocalDateTime.now(),
)
@Entity(tableName = "PartTestItem",primaryKeys = ["test_item_id", "part_id"])
data class PartTestItem(
    @ColumnInfo(name = "test_item_id") val testItemId: Int,
    @ColumnInfo(name = "part_id") val partId: Int,
    @ColumnInfo(name = "test_id") val testId: String,
    @ColumnInfo(name = "test_date") var testDate: String,
    @ColumnInfo(name = "fix_date") var fixDate: String,
    @ColumnInfo(name = "result") var result: String,
    @ColumnInfo(name = "validation") var validation: Boolean,
    @ColumnInfo(name = "modified_on") var modifiedOn: LocalDateTime = LocalDateTime.now(),
)

@Entity(tableName = "PartTestItemAttachment",primaryKeys = ["file_id"])
data class PartTestItemAttachment(
    @ColumnInfo(name = "test_item_id") val testItemId: Int,
    @ColumnInfo(name = "part_id") val partId: Int,
    @ColumnInfo(name = "file_id") val fileId: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "path") val path: String,
    @ColumnInfo(name = "modified_on") val modifiedOn: LocalDateTime = LocalDateTime.now(),
)


/*data class PartTestWithItems(
    @Embedded val partTest: PartTest,
    @Relation(
        entity = PartTest::class,
        parentColumn = "id",
        entityColumn = "test_id"
    )
    val items: List<PartTestItem>
)*/

@Entity(tableName = "PartTestAttachment",primaryKeys = ["file_id"])
data class PartTestAttachment(
    @ColumnInfo(name = "test_id") val testId: Int,
    @ColumnInfo(name = "part_id") val partId: Int,
    @ColumnInfo(name = "file_id") val fileId: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "path") val path: String,
    @ColumnInfo(name = "modified_on") val modifiedOn: LocalDateTime = LocalDateTime.now(),
)