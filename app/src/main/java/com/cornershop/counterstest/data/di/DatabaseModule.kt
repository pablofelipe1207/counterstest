package com.cornershop.counterstest.data.di

import androidx.room.Room
import com.cornershop.counterstest.data.database.CounterDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val MOVIES_DB = "counters-database"

val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), CounterDatabase::class.java, MOVIES_DB).fallbackToDestructiveMigration().build()
    }
    factory { get<CounterDatabase>().counterDao() }
}