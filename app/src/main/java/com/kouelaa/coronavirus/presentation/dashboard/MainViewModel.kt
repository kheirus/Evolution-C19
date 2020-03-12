package com.kouelaa.coronavirus.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kouelaa.coronavirus.data.usecases.GetGlobalUseCase
import com.kouelaa.coronavirus.domain.entities.Global
import com.kouelaa.coronavirus.domain.entities.GlobalChartValue
import com.kouelaa.coronavirus.framework.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch


class MainViewModel(
    dispatcher: CoroutineDispatcher,
    val getGlobalUseCase: GetGlobalUseCase

) : BaseViewModel(dispatcher) {

    private val _global = MutableLiveData<Global>()
    val global: LiveData<Global> get() = _global

    override fun handleException() {

    }
    init {
        launch {
            _global.value = getGlobalUseCase()
        }
    }
}