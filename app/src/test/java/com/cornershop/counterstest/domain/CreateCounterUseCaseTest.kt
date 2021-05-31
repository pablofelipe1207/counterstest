package com.cornershop.counterstest.domain

import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.domain.repository.ICounterRepository
import com.cornershop.counterstest.domain.useCase.CreateCounterUseCaseImpl
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CreateCounterUseCaseTest {

    private val counterRepository : ICounterRepository = mock()
    private val createCounterUseCase by lazy { CreateCounterUseCaseImpl(counterRepository) }

    @Test
    fun `test CreateCounterUseCase calls CounterRepository`() {
        runBlocking {
            val counter = Counter(id = "1", title = "t", count = 0)
            createCounterUseCase(counter)
            verify(counterRepository).createCounter(counter)
        }
    }

}