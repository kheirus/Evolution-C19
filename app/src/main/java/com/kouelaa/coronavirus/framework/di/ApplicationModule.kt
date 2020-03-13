package com.kouelaa.coronavirus.framework.di

import com.google.gson.Gson
import com.kouelaa.coronavirus.BuildConfig
import com.kouelaa.coronavirus.data.datasources.remote.GlobalDataSource
import com.kouelaa.coronavirus.data.repository.GlobalRepository
import com.kouelaa.coronavirus.data.repository.GlobalRepositoryImpl
import com.kouelaa.coronavirus.data.usecases.GetGlobalUseCase
import com.kouelaa.coronavirus.framework.remote.*
import com.kouelaa.coronavirus.presentation.dashboard.GlobalViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val domainModule = module {

    single<GlobalRepository> { GlobalRepositoryImpl(get()) }
    single<GlobalDataSource> { GlobalDataSourceImpl(get()) }
    single { createOkHttpClient() }
    single<ApiService> { createRetrofitClient(get(), BuildConfig.ENDPOINT) }
    single { Gson() }

    factory { GetGlobalUseCase(get()) }

    single<CoroutineDispatcher> { Dispatchers.Main }
}


val vmModule = module {
    viewModel { GlobalViewModel(get(), get()) }
}

