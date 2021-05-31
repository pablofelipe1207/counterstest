package com.cornershop.counterstest.domain

import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.domain.repository.ICounterRepository
import com.cornershop.counterstest.domain.useCase.SelectCounterUseCaseImpl
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SelectCounterUseCaseTest {

    private val counterRepository : ICounterRepository = mock()
    private val selectCounterUseCase by lazy { SelectCounterUseCaseImpl(counterRepository) }

    @Test
    fun `test SelectCounterUseCase calls CounterRepository`() {
        runBlocking {
            val counter = Counter(id = "1", title = "t", count = 0)
            selectCounterUseCase(counter)
            verify(counterRepository).selectCounter(counter)
        }
    }

}