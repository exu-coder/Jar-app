package com.j2merunner.engine.midp

/**
 * Bridge for javax.microedition.lcdui.Display
 * Manages the current displayable
 */
class Display private constructor() {

    companion object {
        private var instance: Display? = null
        private var current: Displayable? = null

        fun getDisplay(midlet: MIDlet): Display {
            if (instance == null) {
                instance = Display()
            }
            return instance!!
        }

        fun setCurrent(displayable: Displayable?) {
            current = displayable
        }

        fun getCurrent(): Displayable? = current
    }

    fun setCurrent(nextDisplayable: Displayable?) {
        current = nextDisplayable
    }

    fun getCurrent(): Displayable? = current

    fun callSerially(run: Runnable) {
        run.run()
    }

    fun flashBacklight(duration: Int): Boolean = false

    fun vibrate(duration: Int): Boolean = false

    fun numColors(): Int = 16777216 // 24-bit color

    fun numAlphaLevels(): Int = 256

    fun isColor(): Boolean = true

    fun getBestImageWidth(imageType: Int): Int = 240

    fun getBestImageHeight(imageType: Int): Int = 320
}

/**
 * Base class for displayable objects
 */
abstract class Displayable {
    private var title: String = ""

    fun setTitle(title: String) {
        this.title = title
    }

    fun getTitle(): String = title

    abstract fun getWidth(): Int
    abstract fun getHeight(): Int
    abstract fun isShown(): Boolean
}
