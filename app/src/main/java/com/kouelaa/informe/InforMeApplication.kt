package com.kouelaa.informe

import android.app.Application
import com.kouelaa.informe.framework.di.domainModule
import com.kouelaa.informe.framework.di.vmModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Created by kheirus on 2020-02-21.
 */
class InforMeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Fabric.with(this, Crashlytics())

        startKoin {
            androidContext(this@InforMeApplication)
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