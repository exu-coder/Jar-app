package com.j2merunner.engine.cldc

import java.util.Timer as JavaTimer
import java.util.TimerTask

/**
 * Bridge for java.util.Timer in J2ME
 */
class Timer {

    private val timer = JavaTimer()

    fun schedule(task: TimerTask, delay: Long) {
        timer.schedule(task, delay)
    }

    fun schedule(task: TimerTask, delay: Long, period: Long) {
        timer.schedule(task, delay, period)
    }

    fun scheduleAtFixedRate(task: TimerTask, delay: Long, period: Long) {
        timer.scheduleAtFixedRate(task, delay, period)
    }

    fun cancel() {
        timer.cancel()
    }
}
