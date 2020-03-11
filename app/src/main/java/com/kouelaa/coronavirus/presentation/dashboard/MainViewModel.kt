package com.kouelaa.coronavirus.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kouelaa.coronavirus.data.usecases.GetGlobalUseCase
import com.kouelaa.coronavirus.domain.entities.Global
import com.kouelaa.coronavirus.domain.entities.GlobalData
import com.kouelaa.coronavirus.framework.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch


class MainViewModel(
    dispatcher: CoroutineDispatcher,
    val useCase: GetGlobalUseCase

) : BaseViewModel(dispatcher) {

    private val _globalData = MutableLiveData<Global>()
    val globalData: LiveData<Global> get() = _globalData

    override fun handleException() {

    }

    init {
        launch {
            _globalData.value = useCase()
        }

    }
}