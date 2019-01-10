package com.aytlo.tony.kotlin_coroutines.data.core.extension

import com.aytlo.tony.kotlin_coroutines.data.core.Error
import com.aytlo.tony.kotlin_coroutines.data.core.Result
import com.aytlo.tony.kotlin_coroutines.data.core.Result.Failure
import com.aytlo.tony.kotlin_coroutines.data.core.Result.Success
import com.aytlo.tony.kotlin_coroutines.data.core.ServerError
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resumeWithException


suspend fun <T : Any> Call<T>.await(): Result<T, Error> {
    return suspendCancellableCoroutine { continuation ->
        enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>?, response: Response<T?>) {
                continuation.resumeWith(runCatching {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            Success(response.body()!!)
                        } else {
                            Failure(ServerError())
                        }
                    } else {
                        Failure(ServerError())
                    }
                })
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                if (continuation.isCancelled) {
                    return
                }
                continuation.resumeWithException(t)
            }
        })

        registerOnCompletion(continuation)
    }
}


private fun Call<*>.registerOnCompletion(continuation: CancellableContinuation<*>) {
    continuation.invokeOnCancellation {
        try {
            cancel()
        } catch (ignored: Throwable) {
            //Ignore cancel exception
        }
    }
}
