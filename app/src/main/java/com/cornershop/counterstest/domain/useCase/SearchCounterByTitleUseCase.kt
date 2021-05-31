package com.cornershop.counterstest.domain.useCase

import com.cornershop.counterstest.domain.base.BaseUseCase
import com.cornershop.counterstest.domain.repository.ICounterRepository
import com.cornershop.counterstest.domain.model.*


interface SearchCounterByTitleUseCase : BaseUseCase<String, List<Counter>> {
    override suspend operator fun invoke(title: String): Result<List<Counter>>
}

class SearchCounterByTitleUseCaseImpl(private val counterRepository: ICounterRepository) : SearchCounterByTitleUseCase {
    override suspend operator fun  invoke(title: String) = counterRepository.searchCounterByTitle(title)
}
