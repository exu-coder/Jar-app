package com.j2merunner.engine.midp

abstract class Canvas {
    companion object {
        const val KEY_NUM0 = 48
        const val KEY_NUM1 = 49
        const val KEY_NUM2 = 50
        const val KEY_NUM3 = 51
        const val KEY_NUM4 = 52
        const val KEY_NUM5 = 53
        const val KEY_NUM6 = 54
        const val KEY_NUM7 = 55
        const val KEY_NUM8 = 56
        const val KEY_NUM9 = 57
        const val KEY_STAR = 42
        const val KEY_POUND = 35
        const val KEY_UP = -1
        const val KEY_DOWN = -2
        const val KEY_LEFT = -3
        const val KEY_RIGHT = -4
        const val KEY_FIRE = -5
        const val KEY_SOFT_LEFT = -6
        const val KEY_SOFT_RIGHT = -7
        const val KEY_CLEAR = -8
        const val KEY_SEND = -10
        const val KEY_END = -11

        const val UP = 1
        const val LEFT = 2
        const val RIGHT = 5
        const val DOWN = 6
        const val FIRE = 8
        const val GAME_A = 9
        const val GAME_B = 10
        const val GAME_C = 11
        const val GAME_D = 12
    }

    private var width = 240
    private var height = 320
    private var fullScreenMode = false

    abstract fun paint(g: Graphics)

    open fun keyPressed(keyCode: Int) {}
    open fun keyReleased(keyCode: Int) {}
    open fun keyRepeated(keyCode: Int) {}
    open fun pointerPressed(x: Int, y: Int) {}
    open fun pointerReleased(x: Int, y: Int) {}
    open fun pointerDragged(x: Int, y: Int) {}

    fun repaint() {}
    fun repaint(x: Int, y: Int, width: Int, height: Int) {}
    fun serviceRepaints() {}

    fun setFullScreenMode(mode: Boolean) {
        fullScreenMode = mode
    }

    fun isFullScreenMode(): Boolean = fullScreenMode
    fun getWidth(): Int = width
    fun getHeight(): Int = height

    fun getGameAction(keyCode: Int): Int {
        return when (keyCode) {
            KEY_UP, KEY_NUM2 -> UP
            KEY_DOWN, KEY_NUM8 -> DOWN
            KEY_LEFT, KEY_NUM4 -> LEFT
            KEY_RIGHT, KEY_NUM6 -> RIGHT
            KEY_FIRE, KEY_NUM5 -> FIRE
            else -> 0
        }
    }

    fun getKeyCode(gameAction: Int): Int {
        return when (gameAction) {
            UP -> KEY_UP
            DOWN -> KEY_DOWN
            LEFT -> KEY_LEFT
            RIGHT -> KEY_RIGHT
            FIRE -> KEY_FIRE
            else -> 0
        }
    }

    fun getKeyName(keyCode: Int): String {
        return when (keyCode) {
            KEY_NUM0 -> "0"
            KEY_NUM1 -> "1"
            KEY_NUM2 -> "2"
            KEY_NUM3 -> "3"
            KEY_NUM4 -> "4"
            KEY_NUM5 -> "5"
            KEY_NUM6 -> "6"
            KEY_NUM7 -> "7"
            KEY_NUM8 -> "8"
            KEY_NUM9 -> "9"
            KEY_STAR -> "*"
            KEY_POUND -> "#"
            KEY_UP -> "UP"
            KEY_DOWN -> "DOWN"
            KEY_LEFT -> "LEFT"
            KEY_RIGHT -> "RIGHT"
            KEY_FIRE -> "FIRE"
            KEY_SOFT_LEFT -> "SOFT1"
            KEY_SOFT_RIGHT -> "SOFT2"
            else -> "UNKNOWN"
        }
    }

    fun hasPointerEvents(): Boolean = true
    fun hasPointerMotionEvents(): Boolean = true
    fun hasRepeatEvents(): Boolean = true

    internal fun setDimensions(w: Int, h: Int) {
        width = w
        height = h
    }
}
