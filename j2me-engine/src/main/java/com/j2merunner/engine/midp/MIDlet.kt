package com.j2merunner.engine.midp

abstract class MIDlet {
    private var destroyed = false

    abstract fun startApp()
    abstract fun pauseApp()
    abstract fun destroyApp(unconditional: Boolean)

    fun notifyDestroyed() {
        destroyed = true
    }

    fun notifyPaused() {}
    fun resumeRequest() {}

    fun getAppProperty(key: String): String? {
        return when (key) {
            "MIDlet-Name" -> "J2ME Game"
            "MIDlet-Version" -> "1.0"
            "MIDlet-Vendor" -> "Unknown"
            else -> null
        }
    }

    fun isDestroyed(): Boolean = destroyed
}
