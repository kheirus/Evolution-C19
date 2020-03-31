package com.kouelaa.evolutionc19.framework.di


import com.google.gson.Gson
import com.kouelaa.evolutionc19.BuildConfig
import com.kouelaa.evolutionc19.data.datasources.remote.GlobalDataSource
import com.kouelaa.evolutionc19.data.repository.GlobalRepository
import com.kouelaa.evolutionc19.data.repository.GlobalRepositoryImpl
import com.kouelaa.evolutionc19.data.usecases.GetGlobalUseCase
import com.kouelaa.evolutionc19.framework.remote.*
import com.kouelaa.evolutionc19.presentation.dashboard.GlobalViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val domainModule = module {

    single<GlobalRepository> { GlobalRepositoryImpl(get()) }
    single<GlobalDataSource> { GlobalDataSourceImpl(get()) }

    single { initOkHttpClient() }
    single { initRemoteConfig() }
    single<ApiService> { initRetrofitClient(get(), BuildConfig.ENDPOINT) }

    single { Gson() }

    factory { GetGlobalUseCase(get()) }

    single<CoroutineDispatcher> { Dispatchers.Main }
}


val vmModule = module {
    viewModel { GlobalViewModel(get(), get(), get(), get()) }
}