package com.example.ny_times_articles

import android.app.Application
import com.example.ny_times_articles.db.AppDatabase
import com.example.ny_times_articles.data.repositories.ArticlesRepository
import com.example.ny_times_articles.data.network.MyApi
import com.example.ny_times_articles.ui.articles.ArticlesViewModelFactory
import com.example.ny_times_articles.utils.NetworkConnectionInterceptor

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MyApp : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyApp))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { ArticlesRepository(instance(), instance()) }
        bind() from provider { ArticlesViewModelFactory(instance()) }
    }
}