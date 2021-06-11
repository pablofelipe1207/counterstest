package com.cornershop.counterstest.domain.useCase

import com.cornershop.counterstest.domain.base.BaseUseCase
import com.cornershop.counterstest.domain.repository.ICounterRepository
import com.cornershop.counterstest.domain.model.*

interface DeleteCounterUseCase : BaseUseCase<List<Counter>, List<Counter>> {
    override suspend operator fun invoke(counters: List<Counter>): Result<List<Counter>>
}

class DeleteCounterUseCaseImpl(private val counterRepository: ICounterRepository) : DeleteCounterUseCase {
    override suspend operator fun  invoke(counters: List<Counter>) = counterRepository.deleteCounter(counters)
}