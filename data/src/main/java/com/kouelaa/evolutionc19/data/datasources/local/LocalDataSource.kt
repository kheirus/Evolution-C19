package com.kouelaa.evolutionc19.data.datasources.local


/**
 * Created by kheirus on 2020-04-01.
 */

interface LocalDataSource {
    fun getDialogChoice(): Boolean
    fun setDialogChoice(isAlreadyClicked: Boolean)
}