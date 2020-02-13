package com.kouelaa.informe.data.local

import com.kouelaa.informe.domain.SampleEntity

/**
 * Created by kheirus on 2020-02-11.
 */

interface SampleDataSource {

    suspend fun add(entity: SampleEntity)

    suspend fun get(): SampleEntity
}