package com.aytlo.tony.kotlin_coroutines.data.core

sealed class Result<out Value, out Error : Throwable> {

    data class Success<out Value>(val value: Value) : Result<Value, Nothing>()
    data class Failure<out Error : Throwable>(val error: Error) : Result<Nothing, Error>()

    fun <Value> success(value: Value) = Success(value)
    fun <Error : Throwable> failure(error: Error) = Failure(error)

    fun isSuccess() = this is Success<Value>

    fun take(success: (Value) -> Any, failure: (Error) -> Any): Any =
        when (this) {
            is Success -> success(value)
            is Failure -> failure(error)
        }
}
