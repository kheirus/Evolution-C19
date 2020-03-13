package com.kouelaa.coronavirus.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kouelaa.coronavirus.data.usecases.GetGlobalUseCase
import com.kouelaa.coronavirus.domain.entities.Global
import com.kouelaa.coronavirus.domain.entities.PaysData
import com.kouelaa.coronavirus.framework.viewmodel.BaseViewModel
import com.kouelaa.coronavirus.framework.viewmodel.getYesterdayDate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import timber.log.Timber


class GlobalViewModel(
    dispatcher: CoroutineDispatcher,
    val getGlobalUseCase: GetGlobalUseCase

) : BaseViewModel(dispatcher) {

    private val _global = MutableLiveData<Global>()
    val global: LiveData<Global> get() = _global

    override fun handleException() {

    }

    fun getCoutriesForAdapter(countries: List<PaysData>): List<PaysData> {
        val dateNow = getYesterdayDate().toString().split("T")
        return countries
            .filter { it.Pays != "Autres" }
            .filter { it.Date.split("T")[0] == dateNow[0] }
            .sortedBy { it.Infection }
    }

    init {
        launch {
            _global.value = getGlobalUseCase()
        }
    }
}