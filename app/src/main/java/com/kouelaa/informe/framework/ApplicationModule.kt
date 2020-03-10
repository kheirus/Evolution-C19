package com.kouelaa.informe.framework

import com.google.gson.Gson
import com.kouelaa.informe.BuildConfig
import com.kouelaa.informe.data.datasources.remote.ApiService
import com.kouelaa.informe.data.datasources.remote.TodoDataSource
import com.kouelaa.informe.data.repository.SampleRepository
import com.kouelaa.informe.data.repository.SampleRepositoryImpl
import com.kouelaa.informe.data.repository.TodoRepository
import com.kouelaa.informe.data.repository.TodoRepositoryImpl
import com.kouelaa.informe.domain.usecases.GetTodoUseCase
import com.kouelaa.informe.presentation.dashboard.MainViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import timber.log.Timber


val domainModule = module {

    single<SampleRepository> { SampleRepositoryImpl(get()) }
    single<TodoRepository> { TodoRepositoryImpl(get()) }
    single<TodoDataSource> { TodoDataSourceImpl(get()) }
    single { createOkHttpClient() }
    single<ApiService> { createWebService(get(), BuildConfig.ENDPOINT) }
    single { Gson() }
    factory { GetTodoUseCase(get()) }
    single<CoroutineDispatcher> { Dispatchers.Main }
}


val vmModule = module {
    viewModel { MainViewModel(get(), get()) }
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, endpoint: String): T {
    return Retrofit.Builder()
        .baseUrl(endpoint)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create()
}

fun createOkHttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Timber.tag("OkHttp").d(message)
        }
    }).apply {
        level = (HttpLoggingInterceptor.Level.BODY)
    }


    return OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .addNetworkInterceptor(logging)
        .build()

}
