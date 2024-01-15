package com.example.arking.feature_settings.domain.uses_cases

import android.util.Log
import com.example.arking.R
import com.example.arking.data.contract.ContractRepository
import com.example.arking.data.part.PartRepository
import com.example.arking.data.prototype.PrototypeRepository
import com.example.arking.data.task.TaskRepository
import com.example.arking.data.test.TestRepository
import com.example.arking.feature_otis.domain.repository.OtiRepository
import com.example.arking.feature_settings.domain.model.ConceptRequest
import com.example.arking.feature_settings.domain.model.FileTaskAttachmentMetadataRequest
import com.example.arking.feature_settings.domain.model.FileTestItemAttachmentMetadataRequest
import com.example.arking.feature_settings.domain.model.ItemRequest
import com.example.arking.feature_settings.domain.model.OtiRequest
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
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class Sync constructor(
    private val syncRepository: SyncRepository,
    private val contractRepository: ContractRepository,
    private val partRepository: PartRepository,
    private val prototypeRepository: PrototypeRepository,
    private val testRepository: TestRepository,
    private val taskRepository: TaskRepository,
    private val otiRepository: OtiRepository
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
                            item.testDate ?: "",
                            item.fixDate ?: "",
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

            val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val fileBody = MultipartBody.Part.createFormData("file", file.name, requestFile)
            val metadata = MultipartBody.Part.createFormData("metadata", Gson().toJson(FileTaskAttachmentMetadataRequest(photo.partId,photo.taskId)))
            val fileType = MultipartBody.Part.createFormData("fileType", "task")
            val response = syncRepository.uploadFile(photo.fileId.toString(),fileBody, metadata, fileType)
            if(!response.isSuccessful)
                return Resource.Error(UiText.StringResource(R.string.error_500))
        }
        val testAttach = testRepository.loadPartTestItemAttachmentToSync(startDate)
        testAttach.forEach { photo ->
            val path = photo.path.removePrefix("file://")
            Log.i("uploadAttach", photo.path)
            val file = File(path)

            val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val fileBody = MultipartBody.Part.createFormData("file", file.name, requestFile)
            val metadata = MultipartBody.Part.createFormData("metadata", Gson().toJson(
                FileTestItemAttachmentMetadataRequest(photo.partId,photo.testItemId)
            ))
            val fileType = MultipartBody.Part.createFormData("fileType", "TestItem")
            val response = syncRepository.uploadFile(photo.fileId.toString(),fileBody, metadata, fileType)
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
        partIds.distinct().filter { it > 0 }.forEach { id ->
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
                    if(item.testDate == "Abrir calendario") "" else ParseDateToSync(item.testDate),
                    if(item.fixDate == "Abrir calendario") "" else ParseDateToSync(item.fixDate),
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
        val otisToSync = ArrayList<OtiRequest>()
        val otis = otiRepository.getOtisToSync(startDate)
        otis.forEach { oti ->
            val concepts = otiRepository.getOtiConceptsByOtiIdSuspend(oti.id)
            val tmpOti = OtiRequest(
                oti.id.toString(),
                oti.comments,
                oti.description,
                ParseDateToSync(oti.date),
                ParseDateToSync(oti.startDate),
                ParseDateToSync(oti.endDate),
                null,
                null,
                oti.total,
                concepts.map {
                    item ->
                    ConceptRequest(
                        item.id.toString(),
                        item.concept,
                        item.unit,
                        item.unitPrice,
                        item.quantity,
                        item.total,
                        item.conceptType,
                        item.parentConceptId
                    )
                }
            )
            otisToSync.add(tmpOti)
        }
        val responseUpload = syncRepository.upload(SyncRequest(
            partsToSync,
            otisToSync
        ))
        if(!responseUpload.isSuccessful){
            return Resource.Error(UiText.StringResource(R.string.error_500))
        }
        return  Resource.Success(null)
    }
    private fun ParseDateToSync(date: String): String
    {
        if(date.isNullOrEmpty())
            return ""
        return try{
            val arr = date.split("/")
            Log.i("ParseDateToSync", arr.toString())
            arr[2] + "/" + arr[1] + "/" + arr[0]
        } catch (ex: Exception){
            Log.e("ParseDateToSync", ex.toString())
            ""
        }

    }
}