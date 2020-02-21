package com.kouelaa.informe.data.repository

import com.kouelaa.informe.data.local.SampleDataSource
import com.kouelaa.informe.domain.SampleEntity

/**
 * Created by kheirus on 2020-02-11.
 */

class SampleRepositoryImpl(
    private val sampleDataSource: SampleDataSource
) : SampleRepository {

    override suspend fun addSample(entity: SampleEntity) {
        sampleDataSource.add(entity)
    }

    override suspend fun getSample(): SampleEntity {
        return sampleDataSource.get()
    }
}