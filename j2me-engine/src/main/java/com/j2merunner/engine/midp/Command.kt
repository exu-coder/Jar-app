package com.j2merunner.engine.midp

/**
 * Bridge for javax.microedition.lcdui.Command
 */
class Command(
    @get:JvmName("getLabel")
    val label: String,
    @get:JvmName("getCommandType")
    val commandType: Int,
    @get:JvmName("getPriority")
    val priority: Int
)

/**
 * Listener for command events
 */
interface CommandListener {
    fun commandAction(command: Command, displayable: Displayable)
}
