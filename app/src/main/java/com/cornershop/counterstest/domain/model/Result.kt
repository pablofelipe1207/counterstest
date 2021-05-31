package com.cornershop.counterstest.domain.model

sealed class Result<out T : Any>
data class Success<out T : Any>(val data: T) : Result<T>()
data class Failure(val counterError: CounterError) : Result<Nothing>()

class CounterError(val throwable: Throwable, val errorCode: Int = 0)

inline fun <T : Any> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    if (this is Success) action(data)
    return this
}

inline fun <T : Any> Result<T>.onFailure(action: (CounterError) -> Unit) {
    if (this is Failure) action(counterError)
}