package com.example.arking.data.test

import com.example.arking.model.PartTest
import com.example.arking.model.PartTestAttachment
import com.example.arking.model.PartTestItem
import com.example.arking.model.PartTestItemAttachment
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestRepository @Inject constructor(
    private val testDao: TestDao
) {
    suspend fun loadPartTestToSync(startDate: String) = testDao.loadPartTestToSync(startDate)
    suspend fun loadPartTestItemToSync(startDate: String) = testDao.loadPartTestItemToSync(startDate)
    suspend fun upsertTestItem(partTestItem: PartTestItem) = testDao.upsertPartTestItem(partTestItem)
    suspend fun upsertPartTest(partTest: PartTest) = testDao.upsertPartTest(partTest)
    fun getAllPartTestItemAttachmentByTestItemId(partId: Int,testItemId: Int) = testDao.getAllPartTestItemAttachmentByTestItemId(partId,testItemId)
    suspend fun insertPartTestItemAttachment(partTestItemAttachment: PartTestItemAttachment) = testDao.insertPartTestItemAttachment(partTestItemAttachment)
    companion object {

        // For Singleton instantiation
        @Volatile private var instance: TestRepository? = null

        fun getInstance(testDao: TestDao) =
            instance ?: synchronized(this) {
                instance ?: TestRepository(testDao).also { instance = it }
            }
    }
}