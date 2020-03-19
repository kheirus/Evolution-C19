package com.kouelaa.coronavirus.domain.entities

import com.google.gson.annotations.SerializedName


/**
 * Created by kheirus on 2020-03-11.
 */

data class Global(
    @SerializedName("Source")
    val source: String,
    @SerializedName("Information")
    val information: String,
    @SerializedName("GlobalData")
    val globalData: List<GlobalData>,
    @SerializedName("PaysData")
    val coutriesData: List<CountryData>
){
    fun toGlobalCards(): List<GlobalChartValue> {
        return mutableListOf<GlobalChartValue>().also {
            it.addAll(listOf(
                GlobalChartValue(
                    label = GlobalTypeEnum.CONFIRMED,
                    value = globalData[0].confirmed.toFloat()
                ),
                GlobalChartValue(
                    label = GlobalTypeEnum.DEATHS,
                    value = globalData[0].deaths.toFloat()
                ),
                GlobalChartValue(
                    label = GlobalTypeEnum.RECOVERED,
                    value = globalData[0].recovered.toFloat()
                ),
                GlobalChartValue(
                    label = GlobalTypeEnum.STILL_SICK,
                    value = (globalData[0].confirmed - (globalData[0].deaths + globalData[0].recovered)).toFloat()
                )

            ))
        }
    }

    fun toGlobalChart():  List<GlobalChartValue>{
        return mutableListOf<GlobalChartValue>().also {
            it.addAll(listOf(
                GlobalChartValue(
                    label = GlobalTypeEnum.DEATHS,
                    value = globalData[0].deaths.toFloat()
                ),
                GlobalChartValue(
                    label = GlobalTypeEnum.RECOVERED,
                    value = globalData[0].recovered.toFloat()
                ),
                GlobalChartValue(
                    label = GlobalTypeEnum.STILL_SICK,
                    value = (globalData[0].confirmed - (globalData[0].deaths + globalData[0].recovered)).toFloat()
                )
            ))
        }
    }

    fun toCountryLineChart(country: String): CountryChartValue{
        val listCountries =  this.coutriesData
            .filter { it.country == country }
            .sortedBy { it.date }

        val listChartValue = mutableListOf<CountryValue>()
        listCountries.forEachIndexed {index, data ->
            listChartValue.add(index, CountryValue(
                date = data.date,
                confirmed = data.confirmed,
                recovered = data.recovered,
                death = data.death
            ))
        }

        return CountryChartValue(country, listChartValue)
    }
}

data class GlobalData(
    @SerializedName("Date")
    val date: String,
    @SerializedName("Infection")
    val confirmed: Double,
    @SerializedName("Deces")
    val deaths: Double,
    @SerializedName("Guerisons")
    val recovered: Double
)

data class CountryData(
    @SerializedName("Date")
    val date: String,
    @SerializedName("Pays")
    val country: String,
    @SerializedName("Infection")
    val confirmed: Double,
    @SerializedName("Deces")
    val death: Double,
    @SerializedName("Guerisons")
    val recovered: Double
)

enum class GlobalTypeEnum{CONFIRMED, RECOVERED, DEATHS, STILL_SICK}
