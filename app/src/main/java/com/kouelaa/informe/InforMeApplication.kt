package com.kouelaa.informe

import android.app.Application
import com.kouelaa.informe.framework.domainModule
import com.kouelaa.informe.framework.vmModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by kheirus on 2020-02-21.
 */
class InforMeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Fabric.with(this, Crashlytics())

        startKoin {
            androidContext(this@InforMeApplication)
            modules(
                listOf(
                    vmModule,
                    domainModule
                )
            )

//            if (BuildConfig.DEBUG) {
//                androidLogger()
//            }
        }
    }
}