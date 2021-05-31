package com.cornershop.counterstest.domain.useCase

import com.cornershop.counterstest.domain.base.BaseUseCase
import com.cornershop.counterstest.domain.repository.ICounterRepository
import com.cornershop.counterstest.domain.model.*

interface UnSelectCounterUseCase : BaseUseCase<Counter, List<Counter>> {
    override suspend operator fun invoke(counter: Counter): Result<List<Counter>>
}

class UnSelectCounterUseCaseImpl(private val counterRepository: ICounterRepository) : UnSelectCounterUseCase {
    override suspend operator fun  invoke(counter: Counter) = counterRepository.unselectCounter(counter)
}