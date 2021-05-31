package com.cornershop.counterstest.domain

import com.cornershop.counterstest.domain.repository.ICounterRepository
import com.cornershop.counterstest.domain.useCase.SearchCounterByTitleUseCaseImpl
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SearchCounterByTitleUseCaseTest {

    private val counterRepository : ICounterRepository = mock()
    private val searchCounterByTitleUseCase by lazy { SearchCounterByTitleUseCaseImpl(counterRepository) }

    @Test
    fun `test SearchCounterByTitleUseCase calls CounterRepository`() {
        runBlocking {
            val title = "test"
            searchCounterByTitleUseCase(title)
            verify(counterRepository).searchCounterByTitle(title)
        }
    }

}