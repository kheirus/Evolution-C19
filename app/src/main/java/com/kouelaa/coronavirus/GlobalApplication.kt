package com.kouelaa.coronavirus

import android.app.Application
import com.kouelaa.coronavirus.framework.di.domainModule
import com.kouelaa.coronavirus.framework.di.vmModule
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