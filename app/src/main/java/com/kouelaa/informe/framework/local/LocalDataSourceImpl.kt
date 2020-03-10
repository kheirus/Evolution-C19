package com.kouelaa.informe.framework.local

import com.kouelaa.informe.data.datasources.local.LocalDataSource
import com.kouelaa.informe.domain.entities.SampleEntity

/**
 * Created by kheirus on 2020-02-11.
 */

// Will be a room database
class LocalDataSourceImpl : LocalDataSource {

    // This data will be stocked in a real database later
    private lateinit var data: String

    override suspend fun add(entity: SampleEntity) {
        data = entity.text
    }

    override suspend fun get(): SampleEntity {
        return SampleEntity(data)
    }
}