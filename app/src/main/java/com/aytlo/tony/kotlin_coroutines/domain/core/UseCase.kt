package com.aytlo.tony.kotlin_coroutines.domain.core

import com.aytlo.tony.kotlin_coroutines.data.core.Result
import kotlinx.coroutines.*


abstract class UseCase<out Type, in Params>(
    private val scope: CoroutineScope = GlobalScope,
    private val potScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) where Type : Any {

    abstract suspend fun run(params: Params): Result<Type, Throwable>

    operator fun invoke(params: Params, onResult: (Result<Type, Throwable>) -> Unit = {}) {
        val job = scope.async { run(params) }
        potScope.launch { onResult(job.await()) }
    }

    class None
}
