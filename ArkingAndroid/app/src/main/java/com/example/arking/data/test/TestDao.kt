package com.example.arking.data.test

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.arking.model.PartTest
import com.example.arking.model.PartTestAttachment
import com.example.arking.model.PartTestItem
import com.example.arking.model.PartTestItemAttachment
import kotlinx.coroutines.flow.Flow

@Dao
interface TestDao {
    @Query("SELECT * FROM PartTestAttachment WHERE part_id=:partId AND test_id=:testId")
    fun getAllAttachmentByTestId(partId: Int,testId: Int): Flow<List<PartTestAttachment>>
    @Insert
    suspend fun insertTestAttachment(partTestAttachment: PartTestAttachment)
    @Delete
    fun deleteTestAttachment(partTestAttachment: PartTestAttachment)
    @Query("SELECT * FROM PartTest WHERE part_id=:partId AND test_id=:testId")
    fun loadTestByPartIdAndTestIdFlow(partId: Int,testId: Int): Flow<PartTest>
    @Query(
        "SELECT * FROM PartTest " +
                "LEFT JOIN PartTestItem ON PartTest.id = PartTestItem.test_id WHERE PartTest.part_id=:partId AND PartTest.test_id=:testId"
    )
    fun loadTestWithItemsByPartIdAndTestIdFlow(partId: Int,testId: Int): Flow<Map<PartTest, List<PartTestItem>>>

    @Query("SELECT * FROM PartTest WHERE part_id=:partId AND test_id=:testId")
    fun loadTestByPartIdAndTestId(partId: Int,testId: Int): Flow<PartTest?>
    @Upsert
    suspend  fun upsertPartTest(partTest: PartTest)
    @Insert
    fun insertPartTest(partTest: PartTest)
    @Update
    suspend fun updatePartTest(partTest: PartTest)
    @Query("SELECT * FROM PartTestItem WHERE part_id=:partId AND test_id=:testId")
    fun getAllTestItemByPartId(partId: Int, testId: String): Flow<List<PartTestItem>>
    @Query("SELECT * FROM PartTestItem WHERE part_id=:partId AND test_item_id=:testItemId")
    fun loadTestItemByPartIdAndTestItemId(partId: Int,testItemId: Int): PartTestItem
    @Insert
    fun insertPartTestItem(partTestItem: PartTestItem)
    @Update
    fun updatePartTestItem(partTestItem: PartTestItem)
    @Upsert
    suspend fun upsertPartTestItem(partTestItem: PartTestItem)
    @Insert
    suspend fun insertPartTestItemAttachment(partTestItemAttachment: PartTestItemAttachment)
    @Query("SELECT * FROM PartTestItemAttachment WHERE part_id=:partId AND test_item_id=:testItemId")
    fun getAllPartTestItemAttachmentByTestItemId(partId: Int,testItemId: Int): Flow<List<PartTestItemAttachment>>
    //Sync
    @Query("SELECT * FROM PartTest WHERE modified_on >= :startDate")
    suspend fun loadPartTestToSync(startDate: String): List<PartTest>
    @Query("SELECT * FROM PartTestItem WHERE modified_on >= :startDate")
    suspend fun loadPartTestItemToSync(startDate: String): List<PartTestItem>
    @Query("SELECT * FROM PartTestItemAttachment WHERE modified_on >= :startDate")
    suspend fun loadPartTestItemAttachmentToSync(startDate: String): List<PartTestItemAttachment>
}