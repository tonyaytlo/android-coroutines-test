package com.aytlo.tony.kotlin_coroutines.data.core.extension

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.resumeWithException


/**
 * Suspend extension that allows suspend [Call] inside of a coroutine.
 *
 * @return Result of request or throw exception
 */
public suspend fun <T : Any> Call<T>.await(): T {
    return suspendCancellableCoroutine { continuation ->
        enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>?, response: Response<T?>) {
                continuation.resumeWith(runCatching {
                    if (response.isSuccessful) {
                        response.body()
                            ?: throw NullPointerException("Response body is null: $response")
                    } else {
                        throw HttpException(response)
                    }
                })
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                // Don't bother with resuming the continuation if it is already cancelled.
                if (continuation.isCancelled) return
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
        } catch (ex: Throwable) {
            //Ignore cancel exception
        }
    }
}