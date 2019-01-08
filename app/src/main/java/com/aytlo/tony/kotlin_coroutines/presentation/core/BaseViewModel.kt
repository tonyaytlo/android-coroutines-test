package com.aytlo.tony.kotlin_coroutines.presentation.core

import androidx.lifecycle.ViewModel
import com.aytlo.tony.kotlin_coroutines.presentation.core.extension.unsafeLazy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(internal val foregroundContext: CoroutineContext = Dispatchers.Main,
                             internal val backgroundContext: CoroutineContext = Dispatchers.IO)
    : ViewModel(), CoroutineScope {

    internal val compositeJob by unsafeLazy { CompositeJob() }

    override val coroutineContext: CoroutineContext
        get() = foregroundContext

    override fun onCleared() {
        super.onCleared()
        compositeJob.cancel()
    }

    fun addJob(job: Job) {
        compositeJob.add(job)
    }
}