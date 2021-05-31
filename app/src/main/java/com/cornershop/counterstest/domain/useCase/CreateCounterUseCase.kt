package com.cornershop.counterstest.domain.useCase

import com.cornershop.counterstest.domain.base.BaseUseCase
import com.cornershop.counterstest.domain.repository.ICounterRepository
import com.cornershop.counterstest.domain.model.*

interface CreateCounterUseCase : BaseUseCase<Counter, List<Counter>> {
    override suspend operator fun invoke(counter: Counter): Result<List<Counter>>
}

class CreateCounterUseCaseImpl(private val counterRepository: ICounterRepository) : CreateCounterUseCase {
    override suspend operator fun  invoke(counter: Counter) = counterRepository.createCounter(counter)
}