package com.cornershop.counterstest.presentation.ui.home.create

import android.content.Context
import com.cornershop.counterstest.R
import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.domain.model.onFailure
import com.cornershop.counterstest.domain.model.onSuccess
import com.cornershop.counterstest.domain.useCase.CreateCounterUseCase
import com.cornershop.counterstest.presentation.ui.base.BaseViewModel
import com.cornershop.counterstest.presentation.ui.base.*

class CreateItemViewModel(
    private val context: Context,
    private val createCounterUseCase: CreateCounterUseCase
) : BaseViewModel<List<Counter>, Counter>() {

    fun createCounter(counter: Counter) = executeUseCase {
        createCounterUseCase(counter)
            .onSuccess { _countersViewState.value = Success(it) }
            .onFailure { _countersViewState.value = DialogError(context.getString(R.string.error_creating_counter_title)) }
    }
}