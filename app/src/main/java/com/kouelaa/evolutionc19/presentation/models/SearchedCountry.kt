package com.kouelaa.evolutionc19.presentation.models

/**
 * Created by kheirus on 23/03/2020.
 */
data class SearchedCountry (
    val found: Boolean,
    val index: Int
){
    companion object {
        const val OTHER_COUNTRIES = "Autres"
    }
}