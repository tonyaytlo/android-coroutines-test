package com.aytlo.tony.kotlin_coroutines.data.source.remote.interceptor

import android.content.Context
import com.aytlo.tony.kotlin_coroutines.R
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationInterceptor
@Inject constructor(private val context: Context) : Interceptor {

    companion object {
        private const val QUERY_API_KEY = "api-key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest
                .newBuilder()
                .addHeader(QUERY_API_KEY, getApiKey())
                .build()
        return chain.proceed(modifiedRequest)
    }

    private fun getApiKey() = context.getString(R.string.API_KEY)
}