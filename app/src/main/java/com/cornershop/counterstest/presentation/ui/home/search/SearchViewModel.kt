package com.cornershop.counterstest.presentation.ui.home.search

import android.content.Context
import com.cornershop.counterstest.R
import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.domain.model.onFailure
import com.cornershop.counterstest.domain.model.onSuccess
import com.cornershop.counterstest.domain.useCase.*
import com.cornershop.counterstest.presentation.ui.base.BaseViewModel
import com.cornershop.counterstest.presentation.ui.base.*

class SearchViewModel(
    private val context: Context,
    private val searchCounterByTitleUseCase: SearchCounterByTitleUseCase,
    private val increaseCounterUseCase: IncreaseCounterUseCase,
    private val decrementCounterUseCase: DecrementCounterUseCase,
    private val getCountersUseCase: GetCountersUseCase
) : BaseViewModel<List<Counter>, Counter>() {

    fun searchCounterByTitle(title: String) = executeUseCase {
        searchCounterByTitleUseCase(title)
            .onSuccess { _countersViewState.value = Success(it) }
            .onFailure { _countersViewState.value = Error(it.throwable) }
    }

    fun increaseCounter(counter: Counter,search: String) = executeUseCase {
        increaseCounterUseCase(counter)
            .onSuccess { searchCounterByTitle(search) }
            .onFailure { _countersViewState.value = DialogError(context.getString(R.string.error_updating_counter_title,counter.title,counter.increment()))  }
    }

    fun decrementCounter(counter: Counter,search: String) = executeUseCase {
        decrementCounterUseCase(counter)
            .onSuccess { searchCounterByTitle(search) }
            .onFailure { _countersViewState.value = DialogError(context.getString(R.string.error_updating_counter_title,counter.title,counter.decrement())) }
    }

    fun getCounters() = executeUseCase {
        getCountersUseCase()
            .onSuccess { _countersViewState.value = Success(it) }
            .onFailure { _countersViewState.value = Error(it.throwable) }
    }

}