package com.kouelaa.coronavirus.domain.entities


/**
 * Created by kheirus on 2020-03-11.
 */

data class Global(
    val Source: String,
    val Information: String,
    val GlobalData: List<GlobalData>,
    val PaysData: List<PaysData>
){
    fun toGlobalCards(): List<GlobalChartValue> {
        return mutableListOf<GlobalChartValue>().also {
            it.addAll(listOf(
                GlobalChartValue(
                    label = GlobalTypeEnum.CONFIRMED,
                    value = GlobalData[0].Infection.toFloat()
                ),
                GlobalChartValue(
                    label = GlobalTypeEnum.DEATHS,
                    value = GlobalData[0].Deces.toFloat()
                ),
                GlobalChartValue(
                    label = GlobalTypeEnum.RECOVERED,
                    value = GlobalData[0].Guerisons.toFloat()
                ),
                GlobalChartValue(
                    label = GlobalTypeEnum.STILL_SICK,
                    value = (GlobalData[0].Infection - (GlobalData[0].Deces + GlobalData[0].Guerisons)).toFloat()
                )

            ))
        }
    }

    fun toGlobalChart():  List<GlobalChartValue>{
        return mutableListOf<GlobalChartValue>().also {
            it.addAll(listOf(
                GlobalChartValue(
                    label = GlobalTypeEnum.DEATHS,
                    value = GlobalData[0].Deces.toFloat()
                ),
                GlobalChartValue(
                    label = GlobalTypeEnum.RECOVERED,
                    value = GlobalData[0].Guerisons.toFloat()
                ),
                GlobalChartValue(
                    label = GlobalTypeEnum.STILL_SICK,
                    value = (GlobalData[0].Infection - (GlobalData[0].Deces + GlobalData[0].Guerisons)).toFloat()
                )
            ))
        }
    }

    fun toCountryLineChart(country: String): CountryChartValue{
       val listCountries =  this.PaysData
           .filter { it.Pays == country }
           .sortedBy { it.Date }

        val listChartValue = mutableListOf<CountryValue>()
       listCountries.forEachIndexed {index, data ->
           listChartValue.add(index, CountryValue(
               date = data.Date,
               confirmed = data.Infection,
               recovered = data.Guerisons,
               death = data.Deces
               ))
       }

        return CountryChartValue(country, listChartValue)

    }
}

data class GlobalData(
    val Date: String,
    val Infection: Double,
    val Deces: Double,
    val Guerisons: Double,
    val TauxDeces: Double,
    val TauxGuerison: Double,
    val TauxInfection: Double
)

data class PaysData(
    val Date: String,
    val Pays: String,
    val Infection: Double,
    val Deces: Double,
    val Guerisons: Double,
    val TauxDeces: Double,
    val TauxGuerison: Double,
    val TauxInfection: Double
)

enum class GlobalTypeEnum{CONFIRMED, RECOVERED, DEATHS, STILL_SICK}
