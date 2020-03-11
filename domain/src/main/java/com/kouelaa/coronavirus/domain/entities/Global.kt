package com.kouelaa.coronavirus.domain.entities

/**
 * Created by kheirus on 2020-03-11.
 */

data class Global(
    val Source: String,
    val Information: String,
    val GlobalData: List<GlobalData>,
    val PaysData: List<PaysData>
)

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
    val Pays: String,
    val GlobalData: GlobalData
)