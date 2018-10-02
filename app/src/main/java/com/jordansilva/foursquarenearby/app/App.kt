package com.jordansilva.foursquarenearby.app

import android.app.Application
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.crashlytics.android.Crashlytics
import com.jordansilva.foursquarenearby.app.di.KoinModule
import io.fabric.sdk.android.Fabric
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

        Fabric.with(this, Crashlytics())
        startKoin(this, listOf(KoinModule.UseCaseModule,
                KoinModule.ViewModule,
                KoinModule.RepositoryModule,
                KoinModule.ApiModule))
    }
}