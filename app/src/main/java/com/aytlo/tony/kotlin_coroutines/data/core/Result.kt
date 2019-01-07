package com.aytlo.tony.kotlin_coroutines.data.core


sealed class Result<out Value, out Error : Throwable> {

    data class Success<out Value>(val value: Value) : Result<Value, Nothing>()
    data class Failure<out Error : Throwable>(val error: Error) : Result<Nothing, Error>()

    fun <Value> success(value: Value) = Success(value)
    fun <Error : Throwable> failure(error: Error) = Failure(error)

    fun isSuccess() = this is Success<Value>

    fun take(onSuccess: (Value) -> Any, onFailure: (Error) -> Any): Any =
            when (this) {
                is Success -> onSuccess(value)
                is Failure -> onFailure(error)
            }
}

fun <From, To, Error : Throwable> Result<From, Error>.map(transform: (From) -> To): Result<To, Error> =
        when (this) {
            is Result.Success -> Result.Success(transform(value))
            is Result.Failure -> this
        }

