package com.example.arking.di

import android.app.Application
import androidx.room.Room
import com.example.arking.feature_otis.data.data_source.OtisDatabase
import com.example.arking.feature_otis.data.repository.OtiRepositoryImpl
import com.example.arking.feature_otis.domain.repository.OtiRepository
import com.example.arking.feature_otis.domain.uses_cases.AddOti
import com.example.arking.feature_otis.domain.uses_cases.AddOtiConcept
import com.example.arking.feature_otis.domain.uses_cases.AddOtiConceptsToolsDefault
import com.example.arking.feature_otis.domain.uses_cases.DeleteOtiConcept
import com.example.arking.feature_otis.domain.uses_cases.EditOtiConcept
import com.example.arking.feature_otis.domain.uses_cases.GetConceptsByOtiId
import com.example.arking.feature_otis.domain.uses_cases.GetOti
import com.example.arking.feature_otis.domain.uses_cases.GetOtis
import com.example.arking.feature_otis.domain.uses_cases.OtisUseCases
import com.example.arking.feature_otis.domain.uses_cases.ValidationDecimal
import com.example.arking.feature_otis.domain.uses_cases.ValidationNotEmpty
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OtisModule {

    @Provides
    @Singleton
    fun provideOtisDatabase(app: Application): OtisDatabase {
        return Room.databaseBuilder(
            app,
            OtisDatabase::class.java,
            OtisDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: OtisDatabase): OtiRepository {
        return OtiRepositoryImpl(db.otiDao)
    }

    @Provides
    @Singleton
    fun provideOtisUseCases(repository: OtiRepository): OtisUseCases {
        return OtisUseCases(
            getOtis = GetOtis(repository),
            addOti = AddOti(repository),
            getOti = GetOti(repository),
            addOtiConcept = AddOtiConcept(repository),
            addOtiConceptsToolsDefault = AddOtiConceptsToolsDefault(repository),
            editOtiConcept = EditOtiConcept(repository),
            deleteOtiConcept = DeleteOtiConcept(repository),
            getConceptsByOtiId = GetConceptsByOtiId(repository),
            validationDecimal = ValidationDecimal(),
            validationNotEmpty =  ValidationNotEmpty()
        )
    }
}