package com.example.arking.di

import android.content.Context
import com.example.arking.data.AppDatabase
import com.example.arking.data.contract.ContractDao
import com.example.arking.data.part.PartDao
import com.example.arking.data.prototype.PrototypeDao
import com.example.arking.data.task.TaskDao
import com.example.arking.data.test.TestDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideContractDao(appDatabase: AppDatabase): ContractDao {
        return appDatabase.ContractDao()
    }
    @Provides
    fun providePartDao(appDatabase: AppDatabase): PartDao {
        return appDatabase.PartDao()
    }
    @Provides
    fun providePrototypeDao(appDatabase: AppDatabase): PrototypeDao {
        return appDatabase.PrototypeDao()
    }
    @Provides
    fun provideTaskDao(appDatabase: AppDatabase): TaskDao {
        return appDatabase.TaskDao()
    }
    @Provides
    fun provideTestDao(appDatabase: AppDatabase): TestDao {
        return appDatabase.TestDao()
    }
}
