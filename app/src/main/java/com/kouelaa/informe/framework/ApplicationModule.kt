package com.kouelaa.informe.framework

import com.kouelaa.informe.data.repository.SampleRepository
import com.kouelaa.informe.data.repository.SampleRepositoryImpl
import com.kouelaa.informe.presentation.dashboard.DashboardViewModel
import com.kouelaa.informe.presentation.home.HomeViewModel
import com.kouelaa.informe.presentation.notifications.NotificationsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val domainModule = module {

    single<SampleRepository> { SampleRepositoryImpl(get()) }
}


val vmModule = module {

    viewModel { DashboardViewModel() }
    viewModel { HomeViewModel() }
    viewModel { NotificationsViewModel() }
}

