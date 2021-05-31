package com.cornershop.counterstest.data.repository

import com.cornershop.counterstest.data.common.corutine.CoroutineContextProvider
import com.cornershop.counterstest.data.common.utils.Connectivity
import com.cornershop.counterstest.data.database.dao.CounterDao
import com.cornershop.counterstest.data.model.CounterEntity
import com.cornershop.counterstest.data.networking.CounterApi
import com.cornershop.counterstest.data.networking.GENERAL_ERROR
import com.cornershop.counterstest.data.networking.entities.IdRequest
import com.cornershop.counterstest.data.networking.entities.TitleRequest
import com.cornershop.counterstest.domain.model.*
import com.cornershop.counterstest.domain.repository.ICounterRepository
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class CounterRepositoryImpl(private val counterApi: CounterApi, private val counterDao: CounterDao, private val connectivity: Connectivity, private val contextProvider: CoroutineContextProvider) : KoinComponent,
    ICounterRepository {

    override suspend fun getCounters(): Result<List<Counter>> {
        val counters = arrayListOf<Counter>()
        return try {
            if (connectivity.hasNetworkAccess()) {
                withContext(contextProvider.io) {
                    val response = counterApi.getAllCounter()
                    counterDao.insert(response)
                    counters.addAll(response.map { it.mapToDomainModel() })
                }
            } else {
                counters.addAll(withContext(contextProvider.io) {
                    counterDao.selectAllItems().map { it.mapToDomainModel() }
                })
            }
            Success(counters)
        } catch (ex: Exception) {
            Failure(CounterError(Throwable(GENERAL_ERROR)))
        }
    }

    override suspend fun createCounter(counter: Counter): Result<List<Counter>> {
        val counters = arrayListOf<Counter>()
        return try {
            if (connectivity.hasNetworkAccess()) {
                withContext(contextProvider.io) {
                    val response = counterApi.createCounter(TitleRequest(counter.title))
                    counterDao.insert(response)
                    counters.addAll(response.map { it.mapToDomainModel() })
                }
            } else {
                withContext(contextProvider.io) {
                    counterDao.insert(CounterEntity.mapToCounterEntity(counter))
                    counters.addAll(counterDao.selectAllItems().map { it.mapToDomainModel() })
                }
            }
            Success(counters)
        } catch (ex: Exception) {
            Failure(CounterError(Throwable(GENERAL_ERROR)))
        }
    }

    override suspend fun increaseCounter(counter: Counter): Result<List<Counter>> {
        val counters = arrayListOf<Counter>()
        return try {
            if (connectivity.hasNetworkAccess()) {
                withContext(contextProvider.io) {
                    if (counter.id.isEmpty()) {
                        counterApi.createCounter(TitleRequest(counter.title))
                            .find { it.title == counter.title }?.let {
                                val response = counterApi.increaseCounter(IdRequest(it.id))
                                counterDao.insert(response)
                                counters.addAll(response.map { it.mapToDomainModel() })
                            } ?: run {
                            Failure(CounterError(Throwable(GENERAL_ERROR)))
                        }
                    } else {
                        val response = counterApi.increaseCounter(IdRequest(counter.id))
                        counterDao.insert(response)
                        counters.addAll(response.map { it.mapToDomainModel() })
                    }
                }
            } else {
                withContext(contextProvider.io) {
                    counter.increment()
                    counterDao.update(CounterEntity.mapToCounterEntity(counter))
                    counters.addAll(counterDao.selectAllItems().map { it.mapToDomainModel() })
                }
            }
            Success(counters)
        } catch (ex: Exception) {
            Failure(CounterError(Throwable(GENERAL_ERROR)))
        }
    }

    override suspend fun decreaseCounter(counter: Counter): Result<List<Counter>> {
        val counters = arrayListOf<Counter>()
        return try {
            if (connectivity.hasNetworkAccess()) {
                withContext(contextProvider.io) {
                    if (counter.id.isEmpty()) {
                        counterApi.createCounter(TitleRequest(counter.title))
                            .find { it.title == counter.title }?.let {
                                val response = counterApi.decrementCounter(IdRequest(it.id))
                                counterDao.insert(response)
                                counters.addAll(response.map { it.mapToDomainModel() })
                            } ?: run {
                            Failure(CounterError(Throwable(GENERAL_ERROR)))
                        }
                    } else {
                        val response = counterApi.decrementCounter(IdRequest(counter.id))
                        counterDao.insert(response)
                        counters.addAll(response.map { it.mapToDomainModel() })
                    }
                }
            } else {
                withContext(contextProvider.io) {
                    counter.decrement()
                    counterDao.update(CounterEntity.mapToCounterEntity(counter))
                    counters.addAll(counterDao.selectAllItems().map { it.mapToDomainModel() })
                }
            }
            Success(counters)
        } catch (ex: Exception) {
            Failure(CounterError(Throwable(GENERAL_ERROR)))
        }
    }

    override suspend fun deleteCounter(counter: Counter): Result<List<Counter>> {
        val counters = arrayListOf<Counter>()
        return try {
            if (connectivity.hasNetworkAccess()) {
                withContext(contextProvider.io) {
                    if(counter.id.isEmpty()){
                        counterDao.delete(counter.title)
                        counters.addAll(counterDao.selectAllItems().map { it.mapToDomainModel() })
                    }else{
                        val response = counterApi.deleteCounter(IdRequest(counter.id))
                        counterDao.insert(response)
                        counters.addAll(response.map { it.mapToDomainModel() })
                    }
                }
            } else {
                withContext(contextProvider.io) {
                    counterDao.delete(counter.title)
                    counters.addAll(counterDao.selectAllItems().map { it.mapToDomainModel() })
                }
            }
            Success(counters)
        } catch (ex: Exception) {
            Failure(CounterError(Throwable(GENERAL_ERROR)))
        }
    }

    override suspend fun searchCounterByTitle(title: String): Result<List<Counter>> {
        val counters = arrayListOf<Counter>()
        return try {
            withContext(contextProvider.io) {
                counters.addAll(counterDao.findUserWithTitle(title).map { it.mapToDomainModel() })
            }
            Success(counters)
        } catch (ex: Exception) {
            Failure(CounterError(Throwable(GENERAL_ERROR)))
        }
    }

    override suspend fun selectCounter(counter: Counter): Result<List<Counter>> {
        val counters = arrayListOf<Counter>()
        return try {
            withContext(contextProvider.io) {
                counterDao.updateCounterIsSelected(counter.title,true)
                counters.addAll(counterDao.selectAllItems().map { it.mapToDomainModel() })
            }
            Success(counters)
        } catch (ex: Exception) {
            Failure(CounterError(Throwable(GENERAL_ERROR)))
        }
    }

    override suspend fun unselectCounter(counter: Counter): Result<List<Counter>> {
        val counters = arrayListOf<Counter>()
        return try {
            withContext(contextProvider.io) {
                counterDao.updateCounterIsSelected(counter.title,false)
                counters.addAll(counterDao.selectAllItems().map { it.mapToDomainModel() })
            }
            Success(counters)
        } catch (ex: Exception) {
            Failure(CounterError(Throwable(GENERAL_ERROR)))
        }
    }
}