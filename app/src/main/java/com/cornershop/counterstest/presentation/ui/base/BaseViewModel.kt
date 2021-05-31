package com.cornershop.counterstest.presentation.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cornershop.counterstest.data.common.corutine.CoroutineContextProvider
import com.cornershop.counterstest.data.common.utils.Connectivity
import com.cornershop.counterstest.presentation.ui.common.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseViewModel<T : Any, E> : ViewModel(), KoinComponent {

    protected val coroutineContext: CoroutineContextProvider by inject()
    private val connectivity: Connectivity by inject()

    protected val _countersViewState = MutableLiveData<ViewState<T>>()
    val countersViewState: LiveData<ViewState<T>>
        get() = _countersViewState

    protected fun executeUseCase(action: suspend () -> Unit, noInternetAction: () -> Unit) {
        _countersViewState.value = Loading()
        if (connectivity.hasNetworkAccess()) {
            launch { action() }
        } else {
            noInternetAction()
        }
    }

    protected fun executeUseCase(action: suspend () -> Unit) {
        _countersViewState.value = Loading()
        launch { action() }
    }

}