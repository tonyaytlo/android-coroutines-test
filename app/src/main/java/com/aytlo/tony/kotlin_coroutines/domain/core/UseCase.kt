package com.aytlo.tony.kotlin_coroutines.domain.core

import com.aytlo.tony.kotlin_coroutines.data.core.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


abstract class UseCase<out Type : Any, in Params>(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO),
    private val postScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {

    abstract suspend fun run(params: Params): Result<Type, Throwable>

    operator fun invoke(params: Params, onResult: (Result<Type, Throwable>) -> Unit = {}) {
        val job = scope.async { run(params) }
        postScope.launch { onResult(job.await()) }
    }

    class None
}
