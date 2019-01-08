package com.aytlo.tony.kotlin_coroutines.di.domain

import com.aytlo.tony.kotlin_coroutines.domain.core.Paginator
import com.aytlo.tony.kotlin_coroutines.domain.model.News
import com.aytlo.tony.kotlin_coroutines.domain.paginator.NewsFeedPaginator
import com.aytlo.tony.kotlin_coroutines.repository.NewsRepository
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideNewsFeedPaginator(newsRepository: NewsRepository): Paginator<News> = NewsFeedPaginator(newsRepository)

}