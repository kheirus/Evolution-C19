package com.kouelaa.informe.interactors

import com.kouelaa.informe.data.repository.SampleRepository
import com.kouelaa.informe.domain.SampleEntity

/**
 * Created by kheirus on 2020-02-11.
 */
class GetTextUseCase(private val sampleRepository: SampleRepository) {

    suspend fun invoke(sampleEntity: SampleEntity): SampleEntity {
        return sampleRepository.getSample()
    }

}