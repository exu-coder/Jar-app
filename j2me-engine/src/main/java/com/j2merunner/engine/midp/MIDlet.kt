package com.j2merunner.engine.midp

/**
 * Bridge for javax.microedition.midlet.MIDlet
 * All J2ME games extend this class. We intercept lifecycle calls.
 */
abstract class MIDlet {

    private var destroyed = false

    /**
     * Called by the system to start the MIDlet
     */
    abstract fun startApp()

    /**
     * Called when the MIDlet is paused
     */
    abstract fun pauseApp()

    /**
     * Called when the MIDlet is destroyed
     */
    abstract fun destroyApp(unconditional: Boolean)

    /**
     * Notify the system that this MIDlet has completed
     */
    fun notifyDestroyed() {
        destroyed = true
        // Hook to close game session
    }

    /**
     * Notify the system that this MIDlet wants to pause
     */
    fun notifyPaused() {
        // Hook to pause game session
    }

    /**
     * Request to resume the MIDlet
     */
    fun resumeRequest() {
        // Hook to resume game session
    }

    /**
     * Get the application property
     */
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
