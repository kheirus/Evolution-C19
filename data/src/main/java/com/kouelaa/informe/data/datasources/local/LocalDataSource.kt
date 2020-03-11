package com.kouelaa.informe.data.datasources.local

import com.kouelaa.informe.domain.entities.SampleEntity

/**
 * Created by kheirus on 2020-02-11.
 */

interface LocalDataSource {

    suspend fun add(entity: SampleEntity)

    suspend fun get(): SampleEntity
}