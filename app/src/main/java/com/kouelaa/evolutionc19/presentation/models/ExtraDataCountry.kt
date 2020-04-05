package com.kouelaa.evolutionc19.presentation.models

import com.kouelaa.evolutionc19.domain.entities.CountryValue

/**
 * Created by kheirus on 23/03/2020.
 */
data class ExtraDataCountry (
    val countryValue: CountryValue,
    val newConfirmed: Double,
    val newRecovered: Double,
    val newDeath: Double
)