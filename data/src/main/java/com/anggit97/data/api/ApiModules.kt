package com.anggit97.data.api

import android.content.Context
import com.anggit97.data.BuildConfig
import com.anggit97.data.api.intercepors.OkHttpInterceptors.createOkHttpInterceptor
import com.anggit97.data.api.intercepors.OkHttpInterceptors.createOkHttpNetworkInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton


/**
 * Created by Anggit Prayogo on 25,July,2021
 * GitHub : https://github.com/anggit97
 */
@Module(includes = [ApiModule.Providers::class])
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideMovieApiService(
        okHttpClient: OkHttpClient
    ): AlbuminApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                }.asConverterFactory("application/json".toMediaType())
            )
            .client(okHttpClient)
            .build()
            .create(AlbuminApiService::class.java)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    internal object Providers {

        @Provides
        @Singleton
        fun provideOkHttpClient(
            @ApplicationContext context: Context
        ): OkHttpClient = OkHttpClient.Builder()
            .cache(Cache(context.cacheDir, 1 * 1024 * 1024)) // 1 MB
            .addInterceptor(createOkHttpInterceptor())
            .addNetworkInterceptor(createOkHttpNetworkInterceptor())
            .build()
    }
}