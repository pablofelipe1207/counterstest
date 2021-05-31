package com.cornershop.counterstest.domain

import com.cornershop.counterstest.domain.repository.ICounterRepository
import com.cornershop.counterstest.domain.useCase.GetCountersUseCaseImpl
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetCountersUseCaseTest {

    private val counterRepository : ICounterRepository = mock()
    private val getCountersUseCase by lazy { GetCountersUseCaseImpl(counterRepository) }

    @Test
    fun `test GetCountersUseCase calls CounterRepository`() {
        runBlocking {
            getCountersUseCase()
            verify(counterRepository).getCounters()
        }
    }

}