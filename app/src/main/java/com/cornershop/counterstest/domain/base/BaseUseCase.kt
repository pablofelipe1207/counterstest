package com.cornershop.counterstest.domain.base

import com.cornershop.counterstest.domain.model.*

interface BaseUseCase<T : Any, R: Any> {
    suspend operator fun invoke(param: T): Result<R>
}

interface BaseUseCaseResult<R: Any> {
    suspend operator fun invoke(): Result<R>
}