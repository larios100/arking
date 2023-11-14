package com.example.arking.data.test

import com.example.arking.model.PartTest
import com.example.arking.model.PartTestItem
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestRepository @Inject constructor(
    private val testDao: TestDao
) {
    fun loadTestByPartIdAndTestId(partId: Int,testId: Int) = testDao.loadTestByPartIdAndTestIdFlow(partId,testId)
    suspend fun createPartTestItem(partTestItem: PartTestItem) {
        var registered = testDao.loadTestItemByPartIdAndTestItemId(partTestItem.partId,partTestItem.testItemId)
        if(registered == null){
            testDao.insertPartTestItem(partTestItem)
        }
        else{
            registered.testDate = partTestItem.testDate
            registered.fixDate = partTestItem.fixDate
            registered.result = partTestItem.result
            registered.validation = partTestItem.validation
            registered.modifiedOn = LocalDateTime.now()
            //registered.s = part.status
            testDao.updatePartTestItem(registered)
        }
    }
    //fun getAllTestItemByPartId(partId: Int) = testDao.getAllTestItemByPartId(partId)
    fun loadTestWithItemsByPartIdAndTestIdFlow(partId: Int,testId: Int) = testDao.loadTestWithItemsByPartIdAndTestIdFlow(partId,testId)
    companion object {

        // For Singleton instantiation
        @Volatile private var instance: TestRepository? = null

        fun getInstance(testDao: TestDao) =
            instance ?: synchronized(this) {
                instance ?: TestRepository(testDao).also { instance = it }
            }
    }
}