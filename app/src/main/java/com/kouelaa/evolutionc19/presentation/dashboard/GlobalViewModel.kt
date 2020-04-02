package com.kouelaa.evolutionc19.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import com.kouelaa.evolutionc19.common.VERSION_CODE
import com.kouelaa.evolutionc19.common.normalize
import com.kouelaa.evolutionc19.data.usecases.GlobalUseCase
import com.kouelaa.evolutionc19.data.usecases.DialogInfoUseCase
import com.kouelaa.evolutionc19.domain.entities.CountryChartValue
import com.kouelaa.evolutionc19.domain.entities.Global
import com.kouelaa.evolutionc19.domain.entities.CountryData
import com.kouelaa.evolutionc19.domain.entities.CountryValue
import com.kouelaa.evolutionc19.framework.remote.REMOTE_FAILED_LOG
import com.kouelaa.evolutionc19.framework.remote.REMOTE_KEY
import com.kouelaa.evolutionc19.framework.remote.REMOTE_SUCCESS_LOG
import com.kouelaa.evolutionc19.framework.viewmodel.BaseViewModel
import com.kouelaa.evolutionc19.presentation.models.DialogUpdateModel
import com.kouelaa.evolutionc19.presentation.models.ExtraDataCountry
import com.kouelaa.evolutionc19.presentation.models.SearchedCountry
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import timber.log.Timber

class GlobalViewModel(
    private val globalUseCase: GlobalUseCase,
    private val dialogInfoUseCase: DialogInfoUseCase,
    private val remoteConfig: FirebaseRemoteConfig,
    private val gson: Gson,
    dispatcher: CoroutineDispatcher
) : BaseViewModel(dispatcher) {

    private val _global = MutableLiveData<Global?>()
    val global: LiveData<Global?> get() = _global

    private val _countryChartData = MutableLiveData<CountryChartValue>()
    val countryChartData: LiveData<CountryChartValue> get() = _countryChartData

    private val _searchCountry = MutableLiveData<SearchedCountry>()
    val searchCountry: LiveData<SearchedCountry> get() = _searchCountry

    private val _selectHighlightValues = MutableLiveData<CountryValue>()
    val selectHighlightValues: LiveData<CountryValue> get() = _selectHighlightValues

    private val _countryExtraValues = MutableLiveData<ExtraDataCountry>()
    val countryExtraValues: LiveData<ExtraDataCountry> get() = _countryExtraValues

    private val _dialogUpdate = MutableLiveData<DialogUpdateModel>()
    val dialogUpdate: LiveData<DialogUpdateModel> get() = _dialogUpdate

    private lateinit var coutriesForAdapter: List<CountryData>

    init {
        launch {
            _global.value = globalUseCase()
        }

        initDialogUpdate()
    }

    override fun handleException() {
        // TODO-(31/03/20)-kheirus: handle exception
    }

    private fun initDialogUpdate(){

        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val updated = task.result
                Timber.d("%s: %s", REMOTE_SUCCESS_LOG, updated)

                val json = remoteConfig.getString(REMOTE_KEY)
                val dialogModel = gson.fromJson<DialogUpdateModel>(json, DialogUpdateModel::class.java)

                if (dialogModel.version > VERSION_CODE){
                    _dialogUpdate.value = dialogModel
                }

            }else{
                Timber.d(REMOTE_FAILED_LOG)
            }
        }
    }

    fun getCoutriesForAdapter(countries: List<CountryData>): List<CountryData> {
        val dateNow = countries[0].date
        coutriesForAdapter = countries
            .filter { it.country != SearchedCountry.OTHER_COUNTRIES }
            .filter { it.date == dateNow }
            .sortedBy { it.confirmed }

        return coutriesForAdapter
    }

    fun onClickedCountry(country: String) {
        _countryChartData.value = _global.value?.toCountryLineChart(country)
    }

    fun onClickFirstCountry() {
        val country= coutriesForAdapter.reversed()[0].country
        _countryChartData.value = _global.value?.toCountryLineChart(country)
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

    fun calculateValuesForHighlight(lastValue: CountryValue){
        val listValues = _countryChartData.value?.values
        val indexHighlighted = listValues?.indexOf(lastValue)

        if (indexHighlighted != null && indexHighlighted > 1){
            val beforeLastValue = listValues[indexHighlighted - 1]
            val countryExtraValue = ExtraDataCountry(
                countryValue = lastValue,
                newConfirmed = lastValue.confirmed - beforeLastValue.confirmed,
                newRecovered = lastValue.recovered - beforeLastValue.recovered,
                newDeath = lastValue.death - beforeLastValue.death
            )
            _countryExtraValues.value = countryExtraValue
        }
    }

    /**
     * To lighten the computation in the listener setOnChartValueSelectedListener ()
     * we prefer to pass through a LiveData that can notify change to the ui and
     * the calculation will be done on another function in viewModel
     */
    fun onChangeSelectHighlight(value: CountryValue){
        _selectHighlightValues.value = value
    }

    fun onClickPositiveButtonDialogInfo() {
        launch {
            dialogInfoUseCase(true)
        }
    }
}