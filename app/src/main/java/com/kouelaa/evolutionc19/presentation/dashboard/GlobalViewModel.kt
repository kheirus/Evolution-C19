package com.kouelaa.evolutionc19.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kouelaa.evolutionc19.common.normalize
import com.kouelaa.evolutionc19.data.usecases.GetGlobalUseCase
import com.kouelaa.evolutionc19.domain.entities.CountryChartValue
import com.kouelaa.evolutionc19.domain.entities.Global
import com.kouelaa.evolutionc19.domain.entities.CountryData
import com.kouelaa.evolutionc19.framework.viewmodel.BaseViewModel
import com.kouelaa.evolutionc19.presentation.models.SearchedCountry
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

    private val _searchCountry = MutableLiveData<SearchedCountry>()
    val searchCountry: LiveData<SearchedCountry> get() = _searchCountry

    private lateinit var coutriesForAdapter: List<CountryData>

    init {
        launch {
            _global.value = getGlobalUseCase()
        }
    }

    override fun handleException() {

    }

    fun getCoutriesForAdapter(countries: List<CountryData>): List<CountryData> {
        val dateNow = countries[0].date
        coutriesForAdapter = countries
            .filter { it.country != "Autres" }
            .filter { it.date == dateNow }
            .sortedBy { it.confirmed }

        return coutriesForAdapter
    }

    fun onClickedCountry(country: String) {
        _countryData.value = _global.value?.toCountryLineChart(country)
    }

    /**
     * @param countrySearched Can be like "Alg" | "algeri" | "algérie" for Algérie
     */
    fun onSearchCountry(countrySearched: String) {
        coutriesForAdapter.reversed().forEach {
            val normalizedCountry = it.country.normalize()
            if (normalizedCountry.contains(countrySearched.normalize(), ignoreCase = true)){
                _searchCountry.value = SearchedCountry(true, getIndexCountry(it.country))
                onClickedCountry(it.country)
                return
            }
        }
        _searchCountry.value = SearchedCountry(false, 0)
    }

    /**
     * Allow layout manager to scroll to index
     */
    private fun getIndexCountry(country: String): Int{
        coutriesForAdapter.reversed().forEachIndexed { index, countryData ->
            if (countryData.country == country){
                return index
            }
        }
        return 0
    }
}