package com.aytlo.tony.kotlin_coroutines.di.repository

import com.aytlo.tony.kotlin_coroutines.data.source.remote.NewsService
import com.aytlo.tony.kotlin_coroutines.presentation.util.NetworkHandler
import com.aytlo.tony.kotlin_coroutines.repository.NewsRepository
import com.aytlo.tony.kotlin_coroutines.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideNewsRepository(newsService: NewsService, networkHandler: NetworkHandler): NewsRepository =
            NewsRepositoryImpl(newsService, networkHandler)
}