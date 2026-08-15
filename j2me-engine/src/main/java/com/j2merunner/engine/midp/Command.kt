package com.j2merunner.engine.midp

/**
 * Bridge for javax.microedition.lcdui.Command
 */
class Command(
    private val cmdLabel: String,
    private val cmdType: Int,
    private val cmdPriority: Int
) {
    companion object {
        const val SCREEN = 1
        const val BACK = 2
        const val CANCEL = 3
        const val OK = 4
        const val HELP = 5
        const val STOP = 6
        const val EXIT = 7
        const val ITEM = 8
    }

    @JvmName("getLabelValue")
    fun getLabel(): String = cmdLabel

    @JvmName("getCommandTypeValue")
    fun getCommandType(): Int = cmdType

    @JvmName("getPriorityValue")
    fun getPriority(): Int = cmdPriority
}

/**
 * Listener for command events
 */
interface CommandListener {
    fun commandAction(command: Command, displayable: Displayable)
}
