package com.kouelaa.coronavirus.domain.entities

/**
 * Created by kheirus on 2020-03-11.
 */

data class CountryChartValue(
    val country: String,
    val values: List<CountryValue>
)

class CountryValue(
    val date: String,
    val confirmed: Double,
    val recovered: Double,
    val death: Double
)