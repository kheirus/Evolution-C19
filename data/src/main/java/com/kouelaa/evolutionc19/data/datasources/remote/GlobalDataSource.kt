package com.kouelaa.evolutionc19.data.datasources.remote

import com.kouelaa.evolutionc19.domain.entities.Global

/**
 * Created by kheirus on 2020-03-11.
 */
interface GlobalDataSource {
    suspend fun getGlobal() : Global?
}