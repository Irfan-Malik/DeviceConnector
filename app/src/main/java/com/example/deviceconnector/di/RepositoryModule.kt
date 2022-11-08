package com.example.deviceconnector.di

import com.example.deviceconnector.endpoint.ApiService
import com.example.deviceconnector.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        apiService: ApiService
    ): Repository {
        return Repository(apiService)
    }
}