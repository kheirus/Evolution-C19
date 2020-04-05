package com.kouelaa.evolutionc19.framework.di


import com.google.gson.Gson
import com.kouelaa.evolutionc19.BuildConfig
import com.kouelaa.evolutionc19.data.datasources.local.LocalDataSource
import com.kouelaa.evolutionc19.data.datasources.remote.GlobalDataSource
import com.kouelaa.evolutionc19.data.repository.GlobalRepository
import com.kouelaa.evolutionc19.data.repository.GlobalRepositoryImpl
import com.kouelaa.evolutionc19.data.repository.DialogInformationRepository
import com.kouelaa.evolutionc19.data.repository.DialogInformationRepositoryImpl
import com.kouelaa.evolutionc19.data.usecases.GlobalUseCase
import com.kouelaa.evolutionc19.data.usecases.DialogInfoUseCase
import com.kouelaa.evolutionc19.framework.local.LocalDataSourceImpl
import com.kouelaa.evolutionc19.framework.local.initSharedPreferences
import com.kouelaa.evolutionc19.framework.remote.*
import com.kouelaa.evolutionc19.presentation.dashboard.GlobalViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val domainModule = module {

    single<GlobalRepository> { GlobalRepositoryImpl(get()) }
    single<DialogInformationRepository> { DialogInformationRepositoryImpl(get()) }

    single<GlobalDataSource> { GlobalDataSourceImpl(get()) }
    single<LocalDataSource> { LocalDataSourceImpl(get()) }

    single { initOkHttpClient() }
    single { initRemoteConfig() }
    single { initSharedPreferences(androidContext()) }
    single<ApiService> { initRetrofitClient(get(), BuildConfig.ENDPOINT) }

    factory { GlobalUseCase(get()) }
    factory { DialogInfoUseCase(get()) }

    single { Gson() }
    single<CoroutineDispatcher> { Dispatchers.Main }
}

val vmModule = module {
    viewModel { GlobalViewModel(get(), get(), get(), get(), get()) }
}