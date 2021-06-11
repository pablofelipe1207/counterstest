package com.cornershop.counterstest.presentation.ui.home.main

import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.domain.model.onFailure
import com.cornershop.counterstest.domain.model.onSuccess
import com.cornershop.counterstest.domain.useCase.*
import com.cornershop.counterstest.presentation.ui.base.BaseViewModel
import com.cornershop.counterstest.presentation.ui.base.*

class MainViewModel(
    private val getCountersUseCase: GetCountersUseCase,
    private val increaseCounterUseCase: IncreaseCounterUseCase,
    private val decrementCounterUseCase: DecrementCounterUseCase,
    private val selectCounterUseCase: SelectCounterUseCase,
    private val unSelectCounterUseCase: UnSelectCounterUseCase,
    private val deleteCounterUseCase: DeleteCounterUseCase
) : BaseViewModel<List<Counter>, Counter>() {

    fun getCounters() = executeUseCase {
        getCountersUseCase()
            .onSuccess { _countersViewState.value = Success(it) }
            .onFailure { _countersViewState.value = Error(it.throwable) }
    }

    fun increaseCounter(counter: Counter) = executeUseCase {
        increaseCounterUseCase(counter)
            .onSuccess { _countersViewState.value = Success(it) }
            .onFailure { _countersViewState.value = Error(it.throwable) }
    }

    fun decrementCounter(counter: Counter) = executeUseCase {
        decrementCounterUseCase(counter)
            .onSuccess { _countersViewState.value = Success(it) }
            .onFailure { _countersViewState.value = Error(it.throwable) }
    }

    fun selectCounter(counter: Counter) = executeUseCase {
        selectCounterUseCase(counter)
            .onSuccess { _countersViewState.value = Success(it) }
            .onFailure { _countersViewState.value = Error(it.throwable) }
    }

    fun unSelectCounter(counter: Counter) = executeUseCase {
        unSelectCounterUseCase(counter)
            .onSuccess { _countersViewState.value = Success(it) }
            .onFailure { _countersViewState.value = Error(it.throwable) }
    }

    fun deleteCounter(counters: List<Counter>) = executeUseCase {
        deleteCounterUseCase(counters)
            .onSuccess { _countersViewState.value = Success(it) }
            .onFailure { _countersViewState.value = Error(it.throwable) }
    }

}