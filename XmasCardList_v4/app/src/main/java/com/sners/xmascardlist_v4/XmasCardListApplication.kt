package com.sners.xmascardlist_v4

import android.app.Application
import timber.log.Timber

class XmasCardListApplication  : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}