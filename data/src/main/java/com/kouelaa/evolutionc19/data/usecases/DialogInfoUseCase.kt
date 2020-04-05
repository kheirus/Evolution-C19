package com.kouelaa.evolutionc19.data.usecases

import com.kouelaa.evolutionc19.data.repository.DialogInformationRepository

/**
 * Created by kheirus on 2020-04-01.
 */

class DialogInfoUseCase (
    private val dialogInformationRepository: DialogInformationRepository
){
    suspend operator fun invoke(): Boolean = dialogInformationRepository.getChoice()
    suspend operator fun invoke(value: Boolean) = dialogInformationRepository.setChoice(value)
}