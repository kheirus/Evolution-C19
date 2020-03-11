package com.kouelaa.informe.framework.remote

import com.kouelaa.informe.data.datasources.remote.TodoDataSource
import com.kouelaa.informe.domain.entities.Todo

/**
 * Created by kheirus on 2020-03-09.
 */
class TodoDataSourceImpl(private val todoApiService: TodoApiService) :
    TodoDataSource {

    override suspend fun getTodo(id: Int): Todo {
        return todoApiService.getTodo(id)
    }
}