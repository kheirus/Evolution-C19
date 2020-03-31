package com.kouelaa.evolutionc19.framework.remote


import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.kouelaa.evolutionc19.BuildConfig
import com.kouelaa.evolutionc19.R

/**
 * Created by kheirus on 30/03/2020.
 */

const val REMOTE_KEY = "dialog_update"
const val REMOTE_FAILED_LOG = "Firebase Remote Config failed to update"
const val REMOTE_SUCCESS_LOG = "Firebase Remote Config update successfully"

fun initRemoteConfig(): FirebaseRemoteConfig {
    return FirebaseRemoteConfig.getInstance().apply {
        var minimumFetchIntervalInSeconds: Long = 28800

        if (BuildConfig.DEBUG) {
            minimumFetchIntervalInSeconds = 1
        }

        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(minimumFetchIntervalInSeconds)
            .setFetchTimeoutInSeconds(30)
            .build()

        setConfigSettingsAsync(configSettings)
        setDefaultsAsync(R.xml.remote_config)
    }
}