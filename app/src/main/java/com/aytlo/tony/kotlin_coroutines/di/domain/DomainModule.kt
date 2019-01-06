package com.aytlo.tony.kotlin_coroutines.di.domain

import com.aytlo.tony.kotlin_coroutines.data.model.News
import com.aytlo.tony.kotlin_coroutines.domain.core.Paginator
import com.aytlo.tony.kotlin_coroutines.domain.interactor.NewsFeedInteractor
import com.aytlo.tony.kotlin_coroutines.domain.interactor.NewsFeedInteractorImpl
import com.aytlo.tony.kotlin_coroutines.domain.paginator.NewsFeedPaginator
import com.aytlo.tony.kotlin_coroutines.repository.NewsRepository
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideNewsFeedPaginator(newsRepository: NewsRepository): Paginator<News> = NewsFeedPaginator(newsRepository)

    @Provides
    fun provideNewsFeedInteractor(newsPaginator: Paginator<News>): NewsFeedInteractor =
            NewsFeedInteractorImpl(newsPaginator)

}