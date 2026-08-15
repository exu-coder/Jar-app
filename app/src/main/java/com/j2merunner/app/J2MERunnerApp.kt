package com.j2merunner.app

import android.app.Application
import com.j2merunner.engine.midp.RecordStore

class J2MERunnerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        RecordStore.initialize(this)
    }

    companion object {
        lateinit var instance: J2MERunnerApp
            private set
    }
}
