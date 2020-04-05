package com.kouelaa.evolutionc19.data.repository

import com.kouelaa.evolutionc19.data.datasources.local.LocalDataSource

/**
 * Created by kheirus on 2020-04-01.
 */

class DialogInformationRepositoryImpl(
    private val localDataSource: LocalDataSource
) : DialogInformationRepository {

    override suspend fun getChoice(): Boolean {
        return localDataSource.getDialogChoice()
    }

    override suspend fun setChoice(choice: Boolean) {
        localDataSource.setDialogChoice(choice)
    }
}