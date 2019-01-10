package com.aytlo.tony.kotlin_coroutines.data.source.remote.interceptor

import com.aytlo.tony.kotlin_coroutines.data.source.ApiKeyProvider
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthHeaderInterceptor
@Inject constructor(private val apiKeyProvider: ApiKeyProvider) : Interceptor {

    companion object {
        private const val QUERY_API_KEY = "api-key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest
            .newBuilder()
            .addHeader(QUERY_API_KEY, apiKeyProvider.getApiKey())
            .build()
        return chain.proceed(modifiedRequest)
    }
}