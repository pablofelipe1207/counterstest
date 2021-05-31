package com.cornershop.counterstest.domain.di

import com.cornershop.counterstest.domain.useCase.*
import org.koin.dsl.module

val interactionModule = module {
    factory<CreateCounterUseCase> { CreateCounterUseCaseImpl(get()) }
    factory<DecrementCounterUseCase> { DecrementCounterUseCaseImpl(get()) }
    factory<DeleteCounterUseCase> { DeleteCounterUseCaseImpl(get()) }
    factory<GetCountersUseCase> { GetCountersUseCaseImpl(get()) }
    factory<IncreaseCounterUseCase> { IncreaseCounterUseCaseImpl(get()) }
    factory<SearchCounterByTitleUseCase> { SearchCounterByTitleUseCaseImpl(get()) }
    factory<SelectCounterUseCase> { SelectCounterUseCaseImpl(get()) }
    factory<UnSelectCounterUseCase> { UnSelectCounterUseCaseImpl(get()) }
}