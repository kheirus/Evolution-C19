package com.kouelaa.evolutionc19

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.kouelaa.evolutionc19.framework.di.domainModule
import com.kouelaa.evolutionc19.framework.di.vmModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Created by kheirus on 2020-02-21.
 */
class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Fabric.with(this, Crashlytics())
        AndroidThreeTen.init(this);


        startKoin {
            androidContext(this@GlobalApplication)
            modules(listOf(vmModule, domainModule))
            if (BuildConfig.DEBUG) {
                androidLogger()
            }
        }

        if (BuildConfig.DEBUG) {
            Timber.uprootAll()
            Timber.plant(Timber.DebugTree())
        }
    }
}