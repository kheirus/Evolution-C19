package com.kouelaa.evolutionc19.data.repository

import com.kouelaa.evolutionc19.domain.entities.Global


/**
 * Created by kheirus on 2020-04-01.
 */

interface DialogInformationRepository {
    suspend fun getChoice() : Boolean
    suspend fun setChoice(choice: Boolean)
}