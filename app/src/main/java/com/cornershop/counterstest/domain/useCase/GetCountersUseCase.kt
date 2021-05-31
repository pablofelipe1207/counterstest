package com.cornershop.counterstest.domain.useCase

import com.cornershop.counterstest.domain.base.BaseUseCaseResult
import com.cornershop.counterstest.domain.repository.ICounterRepository
import com.cornershop.counterstest.domain.model.*

interface GetCountersUseCase : BaseUseCaseResult<List<Counter>> {
    override suspend operator fun invoke(): Result<List<Counter>>
}

class GetCountersUseCaseImpl(private val counterRepository: ICounterRepository) : GetCountersUseCase {
    override suspend operator fun  invoke() = counterRepository.getCounters()
}
