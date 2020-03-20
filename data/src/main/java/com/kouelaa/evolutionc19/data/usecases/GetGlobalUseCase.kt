package com.kouelaa.evolutionc19.data.usecases

import com.kouelaa.evolutionc19.data.repository.GlobalRepository
import com.kouelaa.evolutionc19.domain.entities.Global

/**
 * Created by kheirus on 2020-03-11.
 */

class GetGlobalUseCase (
    private val globalRepository: GlobalRepository
){
    suspend operator fun invoke(): Global? = globalRepository.getGlobal()
}