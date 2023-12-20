package com.example.arking.feature_login.domain.services

import com.example.arking.feature_login.domain.model.Credentials
import com.example.arking.feature_login.domain.model.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface LoginApiService {
    @POST("user/login")
    suspend fun login(@Body user: Credentials): Response<TokenResponse>
}
