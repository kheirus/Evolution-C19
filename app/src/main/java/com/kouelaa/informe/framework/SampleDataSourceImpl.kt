package com.kouelaa.informe.framework

import com.kouelaa.informe.data.local.SampleDataSource
import com.kouelaa.informe.domain.SampleEntity

/**
 * Created by kheirus on 2020-02-11.
 */

// Will be a room database
class SampleDataSourceImpl : SampleDataSource {

    // This data will be stocked in a real database later
    private lateinit var data: String

    override suspend fun add(entity: SampleEntity) {
        data = entity.text
    }

    override suspend fun get(): SampleEntity {
        return SampleEntity(data)
    }
}