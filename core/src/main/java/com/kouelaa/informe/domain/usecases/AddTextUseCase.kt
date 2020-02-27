package com.kouelaa.informe.domain.usecases

import com.kouelaa.informe.data.repository.SampleRepository
import com.kouelaa.informe.domain.entities.SampleEntity

/**
 * Created by kheirus on 2020-02-11.
 */
class AddTextUseCase(private val sampleRepository: SampleRepository) {

    suspend fun invoke(entity: SampleEntity) {
        sampleRepository.addSample(entity)
    }

}