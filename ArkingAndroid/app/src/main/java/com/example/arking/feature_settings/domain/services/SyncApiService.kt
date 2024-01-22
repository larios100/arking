package com.example.arking.feature_settings.domain.services

import com.example.arking.feature_settings.domain.model.SyncRequest
import com.example.arking.feature_settings.domain.model.SyncResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface SyncApiService {
    @POST("sync")
    suspend fun upload(@Body syncRequest: SyncRequest): Response<Unit>
    @GET("sync")
    suspend fun download(@Query("startDate") startDate: String?): Response<SyncResponse>
    @Multipart
    @POST("file/{fileId}")
    suspend fun createFile(@Path("fileId") fileId: String, @Part file: MultipartBody.Part, @Part metadata: MultipartBody.Part, @Part fileType: MultipartBody.Part): Response<Unit>
    @Multipart
    @POST("file/{fileId}")
    suspend fun createFile(@Path("fileId") fileId: String, @Part file: MultipartBody.Part): Response<Unit>
}