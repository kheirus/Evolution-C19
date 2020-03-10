package com.kouelaa.informe.data.datasources.remote

import com.kouelaa.informe.domain.entities.Todo


/**
 * Created by kheirus on 2020-02-11.
 */

interface TodoDataSource {

    suspend fun getTodo(id: Int): Todo
}