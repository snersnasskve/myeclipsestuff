package com.sners.ramblerswalks4

import android.app.Application
import timber.log.Timber

class RamblersApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}