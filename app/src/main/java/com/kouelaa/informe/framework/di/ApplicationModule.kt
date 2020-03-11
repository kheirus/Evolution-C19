package com.kouelaa.informe.framework.di

import com.google.gson.Gson
import com.kouelaa.informe.BuildConfig
import com.kouelaa.informe.data.datasources.remote.TodoDataSource
import com.kouelaa.informe.data.repository.SampleRepository
import com.kouelaa.informe.data.repository.SampleRepositoryImpl
import com.kouelaa.informe.data.repository.TodoRepository
import com.kouelaa.informe.data.repository.TodoRepositoryImpl
import com.kouelaa.informe.data.usecases.GetTodoUseCase
import com.kouelaa.informe.framework.remote.TodoApiService
import com.kouelaa.informe.framework.remote.TodoDataSourceImpl
import com.kouelaa.informe.framework.remote.createOkHttpClient
import com.kouelaa.informe.framework.remote.createRetrofitClient
import com.kouelaa.informe.presentation.dashboard.MainViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val domainModule = module {

    single<SampleRepository> { SampleRepositoryImpl(get()) }
    single<TodoRepository> { TodoRepositoryImpl(get()) }
    single<TodoDataSource> {
        TodoDataSourceImpl(
            get()
        )
    }
    single { createOkHttpClient() }
    single<TodoApiService> { createRetrofitClient(get(), BuildConfig.ENDPOINT) }
    single { Gson() }
    factory { GetTodoUseCase(get()) }
    single<CoroutineDispatcher> { Dispatchers.Main }
}


val vmModule = module {
    viewModel { MainViewModel(get(), get()) }
}

