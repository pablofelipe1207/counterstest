package com.cornershop.counterstest.presentation.di

import com.cornershop.counterstest.presentation.ui.home.create.CreateItemViewModel
import com.cornershop.counterstest.presentation.ui.home.main.MainViewModel
import com.cornershop.counterstest.presentation.ui.home.search.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { CreateItemViewModel(androidContext(),get()) }
    viewModel { MainViewModel(androidContext(),get(), get(), get(), get(), get(), get()) }
    viewModel { SearchViewModel(androidContext(),get(), get(), get(), get()) }
}