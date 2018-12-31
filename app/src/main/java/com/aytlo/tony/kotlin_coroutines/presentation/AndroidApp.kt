package com.aytlo.tony.kotlin_coroutines.presentation

import android.app.Application
import com.aytlo.tony.kotlin_coroutines.di.ApplicationComponent
import com.aytlo.tony.kotlin_coroutines.di.ApplicationModule
import com.aytlo.tony.kotlin_coroutines.di.DaggerApplicationComponent


class AndroidApp : Application() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        inject()
    }

    private fun inject() = appComponent.inject(this)
}