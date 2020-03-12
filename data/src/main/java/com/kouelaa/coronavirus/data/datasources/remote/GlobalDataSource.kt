package com.kouelaa.coronavirus.data.datasources.remote

import com.kouelaa.coronavirus.domain.entities.Global

/**
 * Created by kheirus on 2020-03-11.
 */
interface GlobalDataSource {
    suspend fun getGlobal() : Global
}