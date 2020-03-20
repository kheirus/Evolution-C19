package com.kouelaa.evolutionc19.framework.remote

import com.kouelaa.evolutionc19.data.datasources.remote.GlobalDataSource
import com.kouelaa.evolutionc19.domain.entities.Global

/**
 * Created by kheirus on 2020-03-09.
 */
class GlobalDataSourceImpl(private val apiService: ApiService) : GlobalDataSource {

    override suspend fun getGlobal(): Global? {
        val response = apiService.getGlobal()
        return when {
            response.isSuccessful -> response.body()
            else -> null
        }
    }

}