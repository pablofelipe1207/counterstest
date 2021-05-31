package com.cornershop.counterstest.domain.useCase

import com.cornershop.counterstest.domain.base.BaseUseCase
import com.cornershop.counterstest.domain.repository.ICounterRepository
import com.cornershop.counterstest.domain.model.*

interface DecrementCounterUseCase : BaseUseCase<Counter, List<Counter>> {
    override suspend operator fun invoke(counter: Counter): Result<List<Counter>>
}

class DecrementCounterUseCaseImpl(private val counterRepository: ICounterRepository) : DecrementCounterUseCase {
    override suspend operator fun  invoke(counter: Counter) = counterRepository.decreaseCounter(counter)
}