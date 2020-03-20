package com.kouelaa.coronavirus.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kouelaa.coronavirus.data.usecases.GetGlobalUseCase
import com.kouelaa.coronavirus.domain.entities.CountryChartValue
import com.kouelaa.coronavirus.domain.entities.Global
import com.kouelaa.coronavirus.domain.entities.CountryData
import com.kouelaa.coronavirus.framework.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class GlobalViewModel(
    dispatcher: CoroutineDispatcher,
    val getGlobalUseCase: GetGlobalUseCase

) : BaseViewModel(dispatcher) {

    private val _global = MutableLiveData<Global?>()
    val global: LiveData<Global?> get() = _global

    private val _countryData = MutableLiveData<CountryChartValue>()
    val countryData: LiveData<CountryChartValue> get() = _countryData

    override fun handleException() {

    }

    fun getCoutriesForAdapter(countries: List<CountryData>): List<CountryData> {
        val dateNow = countries[0].date
        return countries
            .filter { it.country != "Autres" }
            .filter { it.date == dateNow }
            .sortedBy { it.confirmed }
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