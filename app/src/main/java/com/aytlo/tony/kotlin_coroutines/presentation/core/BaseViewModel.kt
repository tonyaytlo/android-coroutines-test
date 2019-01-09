package com.aytlo.tony.kotlin_coroutines.presentation.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel(
        internal val foregroundContext: CoroutineContext = Dispatchers.Main,
        internal val backgroundContext: CoroutineContext = Dispatchers.IO
) : ViewModel(), CoroutineScope {

    private val compositeJob = CompositeJob()

    override val coroutineContext: CoroutineContext
        get() = foregroundContext

    override fun onCleared() {
        compositeJob.cancel()
    }

    fun addJob(job: Job) {
        compositeJob.add(job)
    }
}

fun BaseViewModel.launchAware(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
): Job {
    return launch(context, start, block).apply { addJob(this) }
}