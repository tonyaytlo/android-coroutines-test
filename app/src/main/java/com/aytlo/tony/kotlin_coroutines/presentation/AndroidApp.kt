package com.aytlo.tony.kotlin_coroutines.presentation

import android.app.Application
import com.aytlo.tony.kotlin_coroutines.BuildConfig
import com.aytlo.tony.kotlin_coroutines.di.ApplicationComponent
import com.aytlo.tony.kotlin_coroutines.di.ApplicationModule
import com.aytlo.tony.kotlin_coroutines.di.DaggerApplicationComponent
import com.aytlo.tony.kotlin_coroutines.presentation.core.extension.unsafeLazy
import timber.log.Timber


class AndroidApp : Application() {

    val appComponent: ApplicationComponent by unsafeLazy {
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        inject()
    }

    private fun inject() = appComponent.inject(this)
}