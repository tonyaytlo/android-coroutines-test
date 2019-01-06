package com.aytlo.tony.kotlin_coroutines.di

import com.aytlo.tony.kotlin_coroutines.di.domain.DomainModule
import com.aytlo.tony.kotlin_coroutines.di.repository.RepositoryModule
import com.aytlo.tony.kotlin_coroutines.di.viewmodel.ViewModelModule
import com.aytlo.tony.kotlin_coroutines.presentation.AndroidApp
import com.aytlo.tony.kotlin_coroutines.presentation.ui.news_feed.NewsFeedFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class, RepositoryModule::class, DomainModule::class])
interface ApplicationComponent {

    fun inject(application: AndroidApp)

    fun inject(fragment: NewsFeedFragment)
}