package com.aytlo.tony.kotlin_coroutines.presentation.core.extension

import com.aytlo.tony.kotlin_coroutines.presentation.core.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun BaseViewModel.launchComposite(context: CoroutineContext = EmptyCoroutineContext,
                                  start: CoroutineStart = CoroutineStart.DEFAULT,
                                  block: suspend CoroutineScope.() -> Unit): Job {
    return launch(context, start, block).apply { addJob(this) }
}