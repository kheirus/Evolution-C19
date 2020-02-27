package com.kouelaa.informe.data.repository

import com.kouelaa.informe.domain.entities.SampleEntity

/**
 * Created by kheirus on 2020-02-11.
 */

interface SampleRepository {

    suspend fun addSample(entity: SampleEntity)

    suspend fun getSample(): SampleEntity
}