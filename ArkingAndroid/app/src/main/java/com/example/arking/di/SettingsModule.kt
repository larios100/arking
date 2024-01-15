package com.example.arking.di

import com.example.arking.data.contract.ContractRepository
import com.example.arking.data.part.PartRepository
import com.example.arking.data.prototype.PrototypeRepository
import com.example.arking.data.task.TaskRepository
import com.example.arking.data.test.TestRepository
import com.example.arking.feature_otis.domain.repository.OtiRepository
import com.example.arking.feature_settings.domain.repository.SyncRepository
import com.example.arking.feature_settings.domain.services.SyncApiService
import com.example.arking.feature_settings.domain.uses_cases.SettingsUsesCases
import com.example.arking.feature_settings.domain.uses_cases.Sync
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {
    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): SyncApiService = retrofit.create(SyncApiService::class.java)

    @Singleton
    @Provides
    fun providesRepository(apiService: SyncApiService) = SyncRepository(apiService)

    @Provides
    @Singleton
    fun provideLoginUseCases(syncRepository: SyncRepository,
                             contractRepository: ContractRepository,
                             partRepository: PartRepository,
                             prototypeRepository: PrototypeRepository,
                             testRepository: TestRepository,
                             taskRepository: TaskRepository,
                             otiRepository: OtiRepository
    ): SettingsUsesCases {
        return SettingsUsesCases(sync = Sync(syncRepository, contractRepository, partRepository, prototypeRepository,testRepository,taskRepository,otiRepository))
    }
}