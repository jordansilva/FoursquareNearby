package com.jordansilva.foursquarenearby.app

import android.app.Application
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.jordansilva.foursquarenearby.app.di.KoinModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    companion object {
        init {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()

//        if (BuildConfig.DEBUG) {
//            Timber.plant(Timber.DebugTree())
//        }

        //Fabric.with(this, Crashlytics())
        startKoin(this, listOf(KoinModule.UseCaseModule,
                KoinModule.ViewModule,
                KoinModule.RepositoryModule,
                KoinModule.ApiModule))
    }
}