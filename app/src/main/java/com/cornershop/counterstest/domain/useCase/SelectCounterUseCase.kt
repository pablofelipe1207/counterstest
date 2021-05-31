package com.cornershop.counterstest.domain.useCase

import com.cornershop.counterstest.domain.base.BaseUseCase
import com.cornershop.counterstest.domain.repository.ICounterRepository
import com.cornershop.counterstest.domain.model.*

interface SelectCounterUseCase : BaseUseCase<Counter, List<Counter>> {
    override suspend operator fun invoke(counter: Counter): Result<List<Counter>>
}

class SelectCounterUseCaseImpl(private val counterRepository: ICounterRepository) : SelectCounterUseCase {
    override suspend operator fun  invoke(counter: Counter) = counterRepository.selectCounter(counter)
}