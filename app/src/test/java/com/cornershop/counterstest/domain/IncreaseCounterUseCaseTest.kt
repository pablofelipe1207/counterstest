package com.cornershop.counterstest.domain

import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.domain.repository.ICounterRepository
import com.cornershop.counterstest.domain.useCase.IncreaseCounterUseCaseImpl
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test

class IncreaseCounterUseCaseTest {

    private val counterRepository : ICounterRepository = mock()
    private val increaseCounterUseCase by lazy { IncreaseCounterUseCaseImpl(counterRepository) }

    @Test
    fun `test IncreaseCounterUseCase calls CounterRepository`() {
        runBlocking {
            val counter = Counter(id = "1", title = "t", count = 0)
            increaseCounterUseCase(counter)
            verify(counterRepository).increaseCounter(counter)
        }
    }

}