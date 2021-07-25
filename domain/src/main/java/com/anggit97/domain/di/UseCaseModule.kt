package com.anggit97.domain.di

import com.anggit97.domain.repository.PostRepository
import com.anggit97.domain.repository.UserRepository
import com.anggit97.domain.usecase.PostUseCase
import com.anggit97.domain.usecase.PostUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton


/**
 * Created by Anggit Prayogo on 25,July,2021
 * GitHub : https://github.com/anggit97
 */
@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {

    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun providePostUseCase(
        postRepository: PostRepository,
        userRepository: UserRepository
    ): PostUseCase = PostUseCaseImpl(
        postRepository, userRepository
    )
}