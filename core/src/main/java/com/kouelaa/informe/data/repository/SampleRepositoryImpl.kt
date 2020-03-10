package com.kouelaa.informe.data.repository

import com.kouelaa.informe.data.datasources.local.LocalDataSource
import com.kouelaa.informe.domain.entities.SampleEntity

/**
 * Created by kheirus on 2020-02-11.
 */

class SampleRepositoryImpl(
    private val localDataSource: LocalDataSource
) : SampleRepository {

    override suspend fun addSample(entity: SampleEntity) {
        localDataSource.add(entity)
    }

    override suspend fun getSample(): SampleEntity {
        return localDataSource.get()
    }
}