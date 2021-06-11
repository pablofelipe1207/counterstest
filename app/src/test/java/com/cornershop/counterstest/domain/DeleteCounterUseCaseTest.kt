package com.cornershop.counterstest.domain

import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.domain.repository.ICounterRepository
import com.cornershop.counterstest.domain.useCase.DeleteCounterUseCaseImpl
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DeleteCounterUseCaseTest {

    private val counterRepository : ICounterRepository = mock()
    private val deleteCounterUseCase by lazy { DeleteCounterUseCaseImpl(counterRepository) }

    @Test
    fun `test DeleteCounterUseCase calls CounterRepository`() {
        runBlocking {
            val counter = Counter(id = "1", title = "t", count = 0)
            deleteCounterUseCase(arrayListOf(counter))
            verify(counterRepository).deleteCounter(arrayListOf(counter))
        }
    }

}