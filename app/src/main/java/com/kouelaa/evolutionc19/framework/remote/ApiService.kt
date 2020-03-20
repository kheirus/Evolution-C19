package com.kouelaa.evolutionc19.framework.remote

import com.kouelaa.evolutionc19.domain.entities.Global
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by kheirus on 2020-03-09.
 */
interface ApiService {
    @GET("corona-back/corona-numbers.json")
    suspend fun getGlobal(): Response<Global>
}