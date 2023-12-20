package com.example.arking.feature_settings.domain.uses_cases

import android.graphics.BitmapFactory
import android.util.Log
import com.example.arking.R
import com.example.arking.data.contract.ContractRepository
import com.example.arking.data.part.PartRepository
import com.example.arking.data.prototype.PrototypeRepository
import com.example.arking.data.task.TaskRepository
import com.example.arking.data.test.TestRepository
import com.example.arking.feature_settings.domain.model.ItemRequest
import com.example.arking.feature_settings.domain.model.PartRequest
import com.example.arking.feature_settings.domain.model.SyncRequest
import com.example.arking.feature_settings.domain.model.TakRequest
import com.example.arking.feature_settings.domain.model.TestRequest
import com.example.arking.feature_settings.domain.repository.SyncRepository
import com.example.arking.model.Contract
import com.example.arking.model.Part
import com.example.arking.model.PartTask
import com.example.arking.model.PartTest
import com.example.arking.model.PartTestItem
import com.example.arking.model.Prototype
import com.example.arking.utils.Constants.ERROR_FORBIDDEN
import com.example.arking.utils.Resource
import com.example.arking.utils.UiText
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.time.LocalDate
import java.util.Date

class Sync constructor(
    private val syncRepository: SyncRepository,
    private val contractRepository: ContractRepository,
    private val partRepository: PartRepository,
    private val prototypeRepository: PrototypeRepository,
    private val testRepository: TestRepository,
    private val taskRepository: TaskRepository
    ) {
    suspend operator fun invoke(startDate: String): Resource<Unit>{
        try {
            if (startDate != null) {
                Log.i("Sync", startDate)
            }
            //Sync files
            val responseAttach = uploadAttach(startDate)
            if(responseAttach is Resource.Error)
                return responseAttach
            //Sync local information
            val responseUpload = uploadInfo(startDate)
            if(responseUpload is Resource.Error)
                return responseUpload
            //Download server information
            val response = syncRepository.download(startDate)
            if(response.isSuccessful){
                val syncData = response.body()
                syncData?.contracts?.forEach { contract ->
                    contractRepository.upsertContract(Contract(
                        contract.contractId,
                        contract.name,
                        contract.description,
                        contract.status
                    ))
                }
                syncData?.parts?.forEach { part ->
                    partRepository.upsertPart(Part(
                        part.partId,
                        part.name,
                        part.description,
                        part.contractId,
                        part.prototypeId
                    ))
                }
                syncData?.partTasks?.forEach { task ->
                    partRepository.upsertPartTask(PartTask(
                        task.partId,//IMPORTANTE CHECAR API
                        task.taskId,
                        task.isCompleted
                    ))
                }
                syncData?.partTestItems?.forEach { item ->
                    testRepository.upsertTestItem(
                        PartTestItem(
                            item.testItemId,
                            item.partId,
                            item.testId.toString(),
                            item.testDate,
                            item.fixDate,
                            item.result,
                            item.validation
                        )
                    )
                }
                syncData?.partTests?.forEach { item ->
                    testRepository.upsertPartTest(
                        PartTest(
                            "",
                            item.testId,
                            item.partId,
                            item.comments,
                            null,
                            null
                        )
                    )
                }
                syncData?.prototypes?.forEach { prototype ->
                    prototypeRepository.upsertPrototype(Prototype(
                        prototype.prototypeId,
                        prototype.name,
                    ))
                }
                return Resource.Success(null)
            }
            else{
                if(response.code() == ERROR_FORBIDDEN){
                    return Resource.Error(UiText.StringResource(R.string.error_forbidden))
                }
                return Resource.Error(UiText.StringResource(R.string.error_500))
            }
        }
        catch (ex:Exception){
            Log.e("Sync", ex.toString())
            return  Resource.Error(UiText.DynamicString(ex.toString()))
        }
    }
    private suspend fun uploadAttach(startDate: String):Resource<Unit>{
        val taskAttach = taskRepository.loadPartAttachmentToSync(startDate)
        taskAttach.forEach { photo ->
            val path = photo.path.removePrefix("file://")
            Log.i("uploadAttach", photo.path)
            val file = File(path)
            val requestFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
            val response = syncRepository.uploadFile(photo.fileId.toString(),body)
            if(!response.isSuccessful)
                return Resource.Error(UiText.StringResource(R.string.error_500))
        }
        return Resource.Success(null)
    }
    private suspend fun uploadInfo(startDate: String):Resource<Unit>{
        val partTask = partRepository.loadPastTaskToSync(startDate)
        val partTest = testRepository.loadPartTestToSync(startDate)
        val partTestItem = testRepository.loadPartTestItemToSync(startDate)
        Log.i("Sync", partTask.toString())
        val partIds = partTask.map { it.partId } as ArrayList
        partIds.addAll(partTest.map { it.partId })
        partIds.addAll(partTestItem.map { it.partId })
        val partsToSync = ArrayList<PartRequest>()
        partIds.distinct().forEach { id ->
            val tasksToSync = ArrayList<TakRequest>()
            val filterTasks = partTask.filter { it.partId == id }
            filterTasks.forEach { task ->
                tasksToSync.add(TakRequest(
                    task.taskId,
                    task.isCompleted
                ))
            }
            val testToSync = partTest.filter { it.partId == id }.map { test ->
                TestRequest(
                    test.testId,
                    test.comments
                )
            }
            val testItemToSync = partTestItem.filter { it.partId == id }.map { item ->
                ItemRequest(
                    item.testItemId,
                    "",
                    if(item.testDate == "Abrir calendario") "" else item.testDate,
                    if(item.fixDate == "Abrir calendario") "" else item.fixDate,
                    item.result,
                    item.validation
                )
            }
            partsToSync.add(
                PartRequest(
                    id,
                    tasksToSync,
                    testToSync,
                    testItemToSync
                )
            )
        }
        val responseUpload = syncRepository.upload(SyncRequest(
            partsToSync,
            emptyList()
        ))
        if(!responseUpload.isSuccessful){
            return Resource.Error(UiText.StringResource(R.string.error_500))
        }
        return  Resource.Success(null)
    }
}