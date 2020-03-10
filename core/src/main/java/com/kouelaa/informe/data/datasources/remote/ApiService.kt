package com.kouelaa.informe.data.datasources.remote

import com.kouelaa.informe.domain.entities.Todo
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Created by kheirus on 2020-03-09.
 */
interface ApiService {


    @GET("/todos/{id}")
    suspend fun getTodo(@Path(value = "id") id: Int): Todo

}