package com.cornershop.counterstest.domain.useCase

import com.cornershop.counterstest.domain.base.BaseUseCase
import com.cornershop.counterstest.domain.repository.ICounterRepository
import com.cornershop.counterstest.domain.model.*

interface IncreaseCounterUseCase : BaseUseCase<Counter, List<Counter>> {
    override suspend operator fun invoke(counter: Counter): Result<List<Counter>>
}

class IncreaseCounterUseCaseImpl(private val counterRepository: ICounterRepository) : IncreaseCounterUseCase {
    override suspend operator fun  invoke(counter: Counter) = counterRepository.increaseCounter(counter)
}
