package com.example.ny_times_articles

import android.app.Application
import com.example.ny_times_articles.data.remote.RemoteDataSource
import com.example.ny_times_articles.service.repository.ArticlesRepository
import com.example.ny_times_articles.data.local.LocalDataSource
import com.example.ny_times_articles.view.ArticlesUseCase
import com.example.ny_times_articles.view.viewmodel.ArticlesViewModelFactory
import com.example.ny_times_articles.utilities.NetworkConnectionInterceptor
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
        bind() from singleton { RemoteDataSource(instance()) }
        bind() from singleton { LocalDataSource(instance()) }
        bind() from singleton { ArticlesRepository(instance(), instance()) }
        bind() from singleton { ArticlesUseCase(instance()) }
        bind() from provider { ArticlesViewModelFactory(instance()) }
    }
}