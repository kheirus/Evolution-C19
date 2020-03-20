package com.kouelaa.evolutionc19.data.repository

import com.kouelaa.evolutionc19.domain.entities.Global


/**
 * Created by kheirus on 2020-02-11.
 */

interface GlobalRepository {
    suspend fun getGlobal() : Global?
}