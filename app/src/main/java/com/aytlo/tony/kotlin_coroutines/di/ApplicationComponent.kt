package com.aytlo.tony.kotlin_coroutines.di

import com.aytlo.tony.kotlin_coroutines.di.repository.RepositoryModule
import com.aytlo.tony.kotlin_coroutines.di.viewmodel.ViewModelModule
import com.aytlo.tony.kotlin_coroutines.presentation.AndroidApp
import com.aytlo.tony.kotlin_coroutines.presentation.main.MainActivity
import com.aytlo.tony.kotlin_coroutines.presentation.main.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class, RepositoryModule::class])
interface ApplicationComponent {

    fun inject(application: AndroidApp)

    fun inject(fragment: MainFragment)
}