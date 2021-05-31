package com.cornershop.counterstest.presentation

import android.app.Application
import com.cornershop.counterstest.BuildConfig
import com.cornershop.counterstest.data.di.databaseModule
import com.cornershop.counterstest.data.di.networkingModule
import com.cornershop.counterstest.data.di.repositoryModule
import com.cornershop.counterstest.domain.di.interactionModule
import com.cornershop.counterstest.presentation.di.appModule
import com.cornershop.counterstest.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    companion object {
        lateinit var instance: Application
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin {
            androidContext(this@App)
            if (BuildConfig.DEBUG) androidLogger(Level.DEBUG)
            modules(appModules + domainModules + dataModules)
        }
    }
}

val appModules = listOf(presentationModule, appModule)
val domainModules = listOf(interactionModule)
val dataModules = listOf(networkingModule, repositoryModule, databaseModule)