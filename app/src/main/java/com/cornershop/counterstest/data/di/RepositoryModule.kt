package com.cornershop.counterstest.data.di

import com.cornershop.counterstest.data.common.utils.Connectivity
import com.cornershop.counterstest.data.common.utils.ConnectivityImpl
import com.cornershop.counterstest.data.repository.CounterRepositoryImpl
import com.cornershop.counterstest.domain.repository.ICounterRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory<Connectivity> { ConnectivityImpl(androidContext()) }
    factory<ICounterRepository> { CounterRepositoryImpl(get(), get(), get(), get()) }
}