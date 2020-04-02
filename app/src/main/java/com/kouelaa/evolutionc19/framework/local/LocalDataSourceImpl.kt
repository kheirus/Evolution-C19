package com.kouelaa.evolutionc19.framework.local

import android.content.SharedPreferences
import com.kouelaa.evolutionc19.data.datasources.local.LocalDataSource

/**
 * Created by kheirus on 2020-03-09.
 */

class LocalDataSourceImpl(
    private val sharedPref: SharedPreferences
) : LocalDataSource {

    companion object {
        const val DIALOG_CHOICE = "dialog_choice"
    }

    override fun setDialogChoice(isAlreadyClicked: Boolean) {
        sharedPref.edit()
            .putBoolean(DIALOG_CHOICE, isAlreadyClicked)
            .apply()
    }

    override fun getDialogChoice(): Boolean {
        return sharedPref.getBoolean(DIALOG_CHOICE, false)
    }
}