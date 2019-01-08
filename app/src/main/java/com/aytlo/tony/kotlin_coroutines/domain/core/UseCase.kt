package com.aytlo.tony.kotlin_coroutines.domain.core

import com.aytlo.tony.kotlin_coroutines.data.core.Result
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


abstract class UseCase<out Type : Any, in Params>(
        private val foregroundContext: CoroutineContext = Dispatchers.Main,
        private val backgroundContext: CoroutineContext = Dispatchers.IO
) : CoroutineScope {
    

    private var parentJob: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = foregroundContext + parentJob

    abstract suspend fun run(params: Params): Result<Type, Throwable>

    operator fun invoke(params: Params, onResult: (Result<Type, Throwable>) -> Unit = {}) {
        launch {
            val result = withContext(backgroundContext) {
                run(params)
            }
            onResult(result)
        }
    }

    fun unsubscribeAll() {
        parentJob.cancel()
    }

    class None
}
