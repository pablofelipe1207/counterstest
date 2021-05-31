package com.cornershop.counterstest.data

import com.cornershop.counterstest.data.common.corutine.CoroutineContextProvider
import com.cornershop.counterstest.data.common.utils.Connectivity
import com.cornershop.counterstest.data.database.dao.CounterDao
import com.cornershop.counterstest.data.model.CounterEntity
import com.cornershop.counterstest.data.networking.CounterApi
import com.cornershop.counterstest.data.networking.entities.IdRequest
import com.cornershop.counterstest.data.networking.entities.TitleRequest
import com.cornershop.counterstest.data.repository.CounterRepositoryImpl
import com.cornershop.counterstest.domain.model.*
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

class CounterRepositoryTest : KoinTest {

    private val counterApi: CounterApi = mock()
    private val counterDao: CounterDao = mock()
    private val connectivity: Connectivity = mock()
    private val contextProvider: CoroutineContextProvider = mock()

    private val counterRepository = CounterRepositoryImpl(counterApi, counterDao, connectivity, contextProvider)

    @Before
    fun setUp() {
        startKoin { modules(arrayListOf()) }
    }

    @After
    fun closedKoin() {
        stopKoin()
    }

    @Test
    fun getCountersHasNetworkAccessTest() {
        runBlocking {
            val counterModel = CounterEntity(id = "kn59lxol", title = "prueba", count = 2 )

            whenever(connectivity.hasNetworkAccess()).thenReturn(true)
            whenever(contextProvider.io).thenReturn(Dispatchers.IO)
            whenever(counterApi.getAllCounter()).thenReturn(arrayListOf(counterModel))
            val result = counterRepository.getCounters()

            verify(counterApi).getAllCounter()
            verify(counterDao).insert(arrayListOf(counterModel))
            Assert.assertTrue(result == Success(arrayListOf(counterModel.mapToDomainModel())))
        }
    }

    @Test
    fun getCountersNoNetworkAccessTest() {
        runBlocking {
            val counterModel = CounterEntity(id = "kn59lxol", title = "prueba", count = 2 )

            whenever(connectivity.hasNetworkAccess()).thenReturn(false)
            whenever(contextProvider.io).thenReturn(Dispatchers.IO)
            whenever(counterDao.selectAllItems()).thenReturn(arrayListOf(counterModel))
            val result = counterRepository.getCounters()

            verify(counterDao).selectAllItems()
            verify(counterApi, never()).getAllCounter()
            Assert.assertTrue(result == Success(arrayListOf(counterModel.mapToDomainModel())))
        }
    }

    @Test
    fun createCounterNoNetworkAccessTest() {
        runBlocking {
            val counterModel = CounterEntity(id = "kn59lxol", title = "prueba", count = 2)

            whenever(connectivity.hasNetworkAccess()).thenReturn(false)
            whenever(contextProvider.io).thenReturn(Dispatchers.IO)
            whenever(counterDao.selectAllItems()).thenReturn(arrayListOf(counterModel))
            val result = counterRepository.createCounter(counterModel.mapToDomainModel())

            verify(counterDao).insert(counterModel)
            verify(counterDao).selectAllItems()

            Assert.assertTrue(result == Success(arrayListOf(counterModel.mapToDomainModel())))
        }
    }

    @Test
    fun createCounterHasNetworkAccessTest() {
        runBlocking {
            val counterModel = CounterEntity(id = "kn59lxol", title = "prueba", count = 2)

            whenever(connectivity.hasNetworkAccess()).thenReturn(true)
            whenever(contextProvider.io).thenReturn(Dispatchers.IO)
            whenever(counterApi.createCounter(TitleRequest(counterModel.title))).thenReturn(arrayListOf(counterModel))
            val result = counterRepository.createCounter(counterModel.mapToDomainModel())

            verify(counterApi).createCounter(TitleRequest(counterModel.title))
            verify(counterDao).insert(arrayListOf(counterModel))

            Assert.assertTrue(result == Success(arrayListOf(counterModel.mapToDomainModel())))

        }
    }

    @Test
    fun increaseCounterNoNetworkAccessTest() {
        runBlocking {
            val counterModel = CounterEntity(id = "kn59lxol", title = "prueba", count = 2)
            val increaseModel = counterModel.copy(count = 3)

            whenever(connectivity.hasNetworkAccess()).thenReturn(false)
            whenever(contextProvider.io).thenReturn(Dispatchers.IO)
            whenever(counterDao.selectAllItems()).thenReturn(arrayListOf(increaseModel))
            val result = counterRepository.increaseCounter(counterModel.mapToDomainModel())

            verify(counterDao).update(increaseModel)
            verify(counterDao).selectAllItems()

            Assert.assertTrue(result == Success(arrayListOf(increaseModel.mapToDomainModel())))
        }
    }

    @Test
    fun increaseCounterHasNetworkAccessTest() {
        runBlocking {
            val counterModel = CounterEntity(id = "kn59lxol", title = "prueba", count = 2)
            val increaseModel = counterModel.copy(count = 3)

            whenever(connectivity.hasNetworkAccess()).thenReturn(true)
            whenever(contextProvider.io).thenReturn(Dispatchers.IO)
            whenever(counterApi.increaseCounter(IdRequest(counterModel.id))).thenReturn(arrayListOf(increaseModel))
            val result = counterRepository.increaseCounter(counterModel.mapToDomainModel())

            verify(counterApi).increaseCounter(IdRequest(counterModel.id))
            verify(counterDao).insert(arrayListOf(increaseModel))

            Assert.assertTrue(result == Success(arrayListOf(increaseModel.mapToDomainModel())))
        }
    }

    @Test
    fun decreaseCounterNoNetworkAccessTest() {
        runBlocking {
            val counterModel = CounterEntity(id = "kn59lxol", title = "prueba", count = 2)
            val decreaseModel = counterModel.copy(count = 3)

            whenever(connectivity.hasNetworkAccess()).thenReturn(false)
            whenever(contextProvider.io).thenReturn(Dispatchers.IO)
            whenever(counterDao.selectAllItems()).thenReturn(arrayListOf(decreaseModel))
            val result = counterRepository.decreaseCounter(counterModel.mapToDomainModel())

            verify(counterDao).update(decreaseModel)
            verify(counterDao).selectAllItems()

            Assert.assertTrue(result == Success(arrayListOf(decreaseModel.mapToDomainModel())))
        }
    }

    @Test
    fun decreaseCounterHasNetworkAccessTest() {
        runBlocking {
            val counterModel = CounterEntity(id = "kn59lxol", title = "prueba", count = 2)
            val decreaseModel = counterModel.copy(count = 1)

            whenever(connectivity.hasNetworkAccess()).thenReturn(true)
            whenever(contextProvider.io).thenReturn(Dispatchers.IO)
            whenever(counterApi.decrementCounter(IdRequest(counterModel.id))).thenReturn(arrayListOf(decreaseModel))
            val result = counterRepository.decreaseCounter(counterModel.mapToDomainModel())

            verify(counterApi).decrementCounter(IdRequest(counterModel.id))
            verify(counterDao).insert(arrayListOf(decreaseModel))

            Assert.assertTrue(result == Success(arrayListOf(decreaseModel.mapToDomainModel())))
        }
    }

    @Test
    fun deleteCounterHasNetworkAccessTest() {
        runBlocking {
            val counterModel = CounterEntity(id = "kn59lxol", title = "prueba", count = 2)

            whenever(connectivity.hasNetworkAccess()).thenReturn(true)
            whenever(contextProvider.io).thenReturn(Dispatchers.IO)
            whenever(counterApi.deleteCounter(IdRequest(counterModel.id))).thenReturn(arrayListOf())
            val result = counterRepository.deleteCounter(counterModel.mapToDomainModel())

            verify(counterApi).deleteCounter(IdRequest(counterModel.id))
            verify(counterDao).insert(arrayListOf())

            Assert.assertTrue(result == Success(arrayListOf<Counter>()))
        }
    }

    @Test
    fun deleteCounterNoNetworkAccessTest() {
        runBlocking {
            val counterModel = CounterEntity(id = "kn59lxol", title = "prueba", count = 2)

            whenever(connectivity.hasNetworkAccess()).thenReturn(false)
            whenever(contextProvider.io).thenReturn(Dispatchers.IO)
            whenever(counterDao.selectAllItems()).thenReturn(arrayListOf())
            val result = counterRepository.deleteCounter(counterModel.mapToDomainModel())

            verify(counterDao).delete(counterModel.title)
            verify(counterDao).selectAllItems()

            Assert.assertTrue(result == Success(arrayListOf<Counter>()))
        }
    }

    @Test
    fun searchCounterByTitleTest() {
        runBlocking {
            val counterModel = CounterEntity(id = "kn59lxol", title = "prueba", count = 2)

            whenever(contextProvider.io).thenReturn(Dispatchers.IO)
            whenever(counterDao.findUserWithTitle(counterModel.title)).thenReturn(arrayListOf(counterModel))
            val result = counterRepository.searchCounterByTitle(counterModel.title)

            verify(counterDao).findUserWithTitle(counterModel.title)

            Assert.assertTrue(result == Success(arrayListOf(counterModel.mapToDomainModel())))

        }
    }

    @Test
    fun selectCounterTest() {
        runBlocking {
            val counterModel = CounterEntity(id = "kn59lxol", title = "prueba", count = 2)
            val selectModel = counterModel.copy(isSelected = true)

            whenever(contextProvider.io).thenReturn(Dispatchers.IO)
            whenever(counterDao.selectAllItems()).thenReturn(arrayListOf(selectModel))
            val result = counterRepository.selectCounter(counterModel.mapToDomainModel())

            verify(counterDao).updateCounterIsSelected(counterModel.title, true)
            verify(counterDao).selectAllItems()

            Assert.assertTrue(result == Success(arrayListOf(selectModel.mapToDomainModel())))

        }
    }

    @Test
    fun unselectCounterTest() {
        runBlocking {
            val counterModel = CounterEntity(id = "kn59lxol", title = "prueba", count = 2)
            val unselectModel = counterModel.copy(isSelected = true)

            whenever(contextProvider.io).thenReturn(Dispatchers.IO)
            whenever(counterDao.selectAllItems()).thenReturn(arrayListOf(unselectModel))
            val result = counterRepository.unselectCounter(counterModel.mapToDomainModel())

            verify(counterDao).updateCounterIsSelected(counterModel.title, false)
            verify(counterDao).selectAllItems()

            Assert.assertTrue(result == Success(arrayListOf(unselectModel.mapToDomainModel())))

        }
    }

}