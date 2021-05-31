package com.cornershop.counterstest.presentation.di

import com.cornershop.counterstest.data.common.corutine.CoroutineContextProvider
import org.koin.dsl.module

val appModule = module {
    single { CoroutineContextProvider() }
}
