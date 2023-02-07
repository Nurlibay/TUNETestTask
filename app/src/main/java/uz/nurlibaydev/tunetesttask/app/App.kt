package uz.nurlibaydev.tunetesttask.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import uz.nurlibaydev.tunetesttask.BuildConfig

/**
 *  Created by Nurlibay Koshkinbaev on 07/02/2023 15:48
 */

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this@App
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {
        lateinit var instance: App
    }
}