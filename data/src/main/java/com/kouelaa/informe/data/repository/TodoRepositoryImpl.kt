package com.kouelaa.informe.data.repository

import com.kouelaa.informe.data.datasources.remote.TodoDataSource
import com.kouelaa.informe.domain.entities.Todo

/**
 * Created by kheirus on 2020-02-11.
 */

class TodoRepositoryImpl(
    private val todoDataSource: TodoDataSource
) : TodoRepository {

    override suspend fun getTodo(id: Int): Todo {
        return todoDataSource.getTodo(id)
    }
}