package com.anggit97.data.repository

import com.anggit97.data.api.AlbuminApiService
import com.anggit97.domain.repository.PostRepository
import com.anggit97.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Created by Anggit Prayogo on 25,July,2021
 * GitHub : https://github.com/anggit97
 */
@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun providePostRepository(apiService: AlbuminApiService): PostRepository =
        PostRepositoryImpl(apiService)

    @Singleton
    @Provides
    fun provideUserRepository(apiService: AlbuminApiService): UserRepository =
        UserRepositoryImpl(apiService)
}