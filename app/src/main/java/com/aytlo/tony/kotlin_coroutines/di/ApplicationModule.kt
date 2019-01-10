package com.aytlo.tony.kotlin_coroutines.di

import android.content.Context
import com.aytlo.tony.kotlin_coroutines.BuildConfig
import com.aytlo.tony.kotlin_coroutines.data.source.ApiKeyProvider
import com.aytlo.tony.kotlin_coroutines.data.source.remote.NewsApi
import com.aytlo.tony.kotlin_coroutines.data.source.remote.NewsService
import com.aytlo.tony.kotlin_coroutines.data.source.remote.interceptor.AuthHeaderInterceptor
import com.aytlo.tony.kotlin_coroutines.presentation.AndroidApp
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AndroidApp) {

    fun provideBaseUrl() = "https://content.guardianapis.com/"

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsApi =
            NewsService(retrofit.create(NewsApi::class.java))

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(provideBaseUrl())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun provideClient(interceptor: Interceptor): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        okHttpClientBuilder.addInterceptor(interceptor)
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(apiKeyProvider: ApiKeyProvider): Interceptor =
            AuthHeaderInterceptor(apiKeyProvider)

}