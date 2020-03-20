package com.kouelaa.coronavirus.data.usecases

import com.kouelaa.coronavirus.data.repository.GlobalRepository
import com.kouelaa.coronavirus.domain.entities.Global

/**
 * Created by kheirus on 2020-03-11.
 */

class GetGlobalUseCase (
    private val globalRepository: GlobalRepository
){
    suspend operator fun invoke(): Global? = globalRepository.getGlobal()
}