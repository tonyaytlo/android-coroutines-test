package com.aytlo.tony.kotlin_coroutines.domain.core

import com.aytlo.tony.kotlin_coroutines.data.core.Result
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext


abstract class UseCase<out Type : Any, in Params>(
        protected var parentJob: Job = Job(),
        private val backgroundContext: CoroutineContext = Dispatchers.IO
) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + parentJob

    abstract suspend fun run(params: Params): Result<Type, Throwable>

    operator fun invoke(params: Params, onResult: (Result<Type, Throwable>) -> Unit = {}) {
        launch {
            val result = withContext(backgroundContext) {
                delay(1_000)
                run(params)
            }
            onResult(result)
        }
    }

    fun unsubscribe() {
        parentJob.cancel()
    }

    class None
}
