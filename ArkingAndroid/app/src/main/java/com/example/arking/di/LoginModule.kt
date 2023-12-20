package com.example.arking.di

import com.example.arking.feature_login.domain.repository.LoginRepository
import com.example.arking.feature_login.domain.services.LoginApiService
import com.example.arking.feature_login.domain.uses_cases.LoginUsesCases
import com.example.arking.feature_login.domain.uses_cases.ValidateCredentials
import com.example.arking.utils.TokenManager
import com.example.arking.utils.UnsafeOkHttpClient
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): LoginApiService = retrofit.create(LoginApiService::class.java)

    @Singleton
    @Provides
    fun providesRepository(apiService: LoginApiService) = LoginRepository(apiService)

    @Provides
    @Singleton
    fun provideLoginUseCases(loginRepository: LoginRepository, tokenManager: TokenManager): LoginUsesCases{
        return LoginUsesCases(validateCredentials = ValidateCredentials(loginRepository,tokenManager))
    }
}