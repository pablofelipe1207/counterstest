package com.cornershop.counterstest.domain

import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.domain.repository.ICounterRepository
import com.cornershop.counterstest.domain.useCase.DecrementCounterUseCaseImpl
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DecrementCounterUseCaseTest {

    private val counterRepository : ICounterRepository = mock()
    private val decrementCounterUseCase by lazy { DecrementCounterUseCaseImpl(counterRepository) }

    @Test
    fun `test DecrementCounterUseCase calls CounterRepository`() {
        runBlocking {
            val counter = Counter(id = "1", title = "t", count = 0)
            decrementCounterUseCase(counter)
            verify(counterRepository).decreaseCounter(counter)
        }
    }

}