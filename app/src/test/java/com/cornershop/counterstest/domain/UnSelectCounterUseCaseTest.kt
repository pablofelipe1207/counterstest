package com.cornershop.counterstest.domain

import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.domain.repository.ICounterRepository
import com.cornershop.counterstest.domain.useCase.UnSelectCounterUseCaseImpl
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test

class UnSelectCounterUseCaseTest {

    private val counterRepository : ICounterRepository = mock()
    private val unSelectCounterUseCase by lazy { UnSelectCounterUseCaseImpl(counterRepository) }

    @Test
    fun `test SelectCounterUseCase calls CounterRepository`() {
        runBlocking {
            val counter = Counter(id = "1", title = "t", count = 0)
            unSelectCounterUseCase(counter)
            verify(counterRepository).unselectCounter(counter)
        }
    }

}