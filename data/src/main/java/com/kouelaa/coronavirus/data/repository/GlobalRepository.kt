package com.kouelaa.coronavirus.data.repository

import com.kouelaa.coronavirus.domain.entities.Global


/**
 * Created by kheirus on 2020-02-11.
 */

interface GlobalRepository {

    suspend fun get() : Global

}