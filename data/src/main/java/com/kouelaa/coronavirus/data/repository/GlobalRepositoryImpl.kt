package com.kouelaa.coronavirus.data.repository

import com.kouelaa.coronavirus.data.datasources.remote.GlobalDataSource
import com.kouelaa.coronavirus.domain.entities.Global

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