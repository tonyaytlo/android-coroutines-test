package com.aytlo.tony.kotlin_coroutines.presentation

import android.app.Application
import com.aytlo.tony.kotlin_coroutines.di.ApplicationComponent
import com.aytlo.tony.kotlin_coroutines.di.ApplicationModule
import com.aytlo.tony.kotlin_coroutines.di.DaggerApplicationComponent
import com.aytlo.tony.kotlin_coroutines.presentation.core.extension.unsafeLazy


class AndroidApp : Application() {

    val appComponent: ApplicationComponent by unsafeLazy {
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