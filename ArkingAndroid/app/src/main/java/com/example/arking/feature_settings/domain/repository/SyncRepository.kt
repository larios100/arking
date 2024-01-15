package com.example.arking.feature_settings.domain.repository

import com.example.arking.feature_settings.domain.model.SyncRequest
import com.example.arking.feature_settings.domain.model.SyncResponse
import com.example.arking.feature_settings.domain.services.SyncApiService
import okhttp3.MultipartBody
import retrofit2.Response

class SyncRepository constructor(private val syncApiService: SyncApiService) {
    suspend fun download(startDate: String?): Response<SyncResponse>
    {
        return syncApiService.download(startDate)
    }
    suspend fun upload(syncRequest: SyncRequest): Response<Unit>{
        return syncApiService.upload(syncRequest)
    }

    suspend fun uploadFile(fileId: String, file: MultipartBody.Part,metadata: MultipartBody.Part,fileType: MultipartBody.Part): Response<Unit>{
        return  syncApiService.createFile(fileId,file, metadata, fileType)
    }
}