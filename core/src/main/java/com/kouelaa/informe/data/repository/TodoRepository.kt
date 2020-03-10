package com.kouelaa.informe.data.repository

import com.kouelaa.informe.domain.entities.Todo

/**
 * Created by kheirus on 2020-02-11.
 */

interface TodoRepository {

    suspend fun getTodo(id: Int): Todo

}