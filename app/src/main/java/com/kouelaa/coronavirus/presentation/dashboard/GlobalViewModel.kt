package com.kouelaa.coronavirus.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kouelaa.coronavirus.data.usecases.GetGlobalUseCase
import com.kouelaa.coronavirus.domain.entities.CountryChartValue
import com.kouelaa.coronavirus.domain.entities.Global
import com.kouelaa.coronavirus.domain.entities.PaysData
import com.kouelaa.coronavirus.framework.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class GlobalViewModel(
    dispatcher: CoroutineDispatcher,
    val getGlobalUseCase: GetGlobalUseCase

) : BaseViewModel(dispatcher) {

    private val _global = MutableLiveData<Global>()
    val global: LiveData<Global> get() = _global

    private val _countryData = MutableLiveData<CountryChartValue>()
    val countryData: LiveData<CountryChartValue> get() = _countryData

    override fun handleException() {

    }

    fun getCoutriesForAdapter(countries: List<PaysData>): List<PaysData> {
        val dateNow = countries[0].Date
        return countries
            .filter { it.Pays != "Autres" }
            .filter { it.Date == dateNow }
            .sortedBy { it.Infection }
    }

    fun onClickedCountry(country: String) {
        _countryData.value = _global.value?.toCountryLineChart(country)
    }

    init {
        launch {
            _global.value = getGlobalUseCase()
        }
    }
}