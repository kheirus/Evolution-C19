package com.kouelaa.informe.framework

import com.kouelaa.informe.data.datasources.remote.ApiService
import com.kouelaa.informe.data.datasources.remote.TodoDataSource
import com.kouelaa.informe.domain.entities.Todo

/**
 * Created by kheirus on 2020-03-09.
 */
class TodoDataSourceImpl(private val apiService: ApiService) : TodoDataSource {

    override suspend fun getTodo(id: Int): Todo {
        return apiService.getTodo(id)
    }
}