package com.j2merunner.engine.midp

/**
 * Bridge for javax.microedition.lcdui.Command
 */
class Command(
    val label: String,
    val commandType: Int,
    val priority: Int
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

    fun getLabel(): String = label
    fun getCommandType(): Int = commandType
    fun getPriority(): Int = priority
}

/**
 * Listener for command events
 */
interface CommandListener {
    fun commandAction(command: Command, displayable: Displayable)
}
