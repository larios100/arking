package com.example.arking.feature_login.domain.repository

import com.example.arking.feature_login.domain.model.Credentials
import com.example.arking.feature_login.domain.model.TokenResponse
import com.example.arking.feature_login.domain.services.LoginApiService
import retrofit2.Response

class LoginRepository(private val loginApi: LoginApiService) {
    suspend fun login(credentials: Credentials): Response<TokenResponse> {
        return loginApi.login(credentials)
    }
}