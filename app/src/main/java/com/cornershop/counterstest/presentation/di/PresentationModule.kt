package com.cornershop.counterstest.presentation.di

import com.cornershop.counterstest.presentation.ui.home.create.CreateItemViewModel
import com.cornershop.counterstest.presentation.ui.home.main.MainViewModel
import com.cornershop.counterstest.presentation.ui.home.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { CreateItemViewModel(get()) }
    viewModel { MainViewModel(get(), get(), get()) }
    viewModel { SearchViewModel(get(), get(), get(), get()) }
}