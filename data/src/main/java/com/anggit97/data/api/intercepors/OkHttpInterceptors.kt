package com.anggit97.data.api.intercepors

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

object OkHttpInterceptors {

    private const val HEADER_CACHE_CONTROL = "Cache-Control"
    private const val HEADER_CACHE_MAX_AGE = "public, max-age=${5 * 60}" // 5 minutes

    private const val HEADER_USE_CACHE_PREFIX = "Use-Cache"

    private fun Request.useCache(): Boolean {
        return header(HEADER_USE_CACHE_PREFIX) != null
    }

    fun createOkHttpInterceptor(): Interceptor {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logging
    }

    fun createOkHttpNetworkInterceptor() = Interceptor {
        val request = it.request()
        val useCache = request.useCache()

        it.proceed(request).apply {
            if (useCache) {
                newBuilder()
                    .header(HEADER_CACHE_CONTROL, HEADER_CACHE_MAX_AGE)
                    .removeHeader(HEADER_USE_CACHE_PREFIX)
                    .build()
            }
        }
    }
}
