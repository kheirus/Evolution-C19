package com.kouelaa.evolutionc19.data.repository

import com.kouelaa.evolutionc19.data.datasources.remote.GlobalDataSource
import com.kouelaa.evolutionc19.domain.entities.Global

/**
 * Created by kheirus on 2020-02-11.
 */

class GlobalRepositoryImpl(
    private val globalDataSource: GlobalDataSource
) : GlobalRepository {
    override suspend fun getGlobal(): Global? {
        return globalDataSource.getGlobal()
    }

}