package com.j2merunner.app

import android.app.Application

class J2MERunnerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: J2MERunnerApp
            private set
    }
}
