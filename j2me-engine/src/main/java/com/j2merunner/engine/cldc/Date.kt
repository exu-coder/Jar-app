package com.j2merunner.engine.cldc

import java.util.Calendar

/**
 * Bridge for java.util.Date in J2ME
 */
class Date {

    private var time: Long = System.currentTimeMillis()

    constructor()
    constructor(date: Long) {
        time = date
    }

    fun getTime(): Long = time
    fun setTime(time: Long) { this.time = time }

    fun getYear(): Int = Calendar.getInstance().apply { timeInMillis = this@Date.time }.get(Calendar.YEAR) - 1900
    fun getMonth(): Int = Calendar.getInstance().apply { timeInMillis = this@Date.time }.get(Calendar.MONTH)
    fun getDate(): Int = Calendar.getInstance().apply { timeInMillis = this@Date.time }.get(Calendar.DAY_OF_MONTH)
    fun getDay(): Int = Calendar.getInstance().apply { timeInMillis = this@Date.time }.get(Calendar.DAY_OF_WEEK) - 1
    fun getHours(): Int = Calendar.getInstance().apply { timeInMillis = this@Date.time }.get(Calendar.HOUR_OF_DAY)
    fun getMinutes(): Int = Calendar.getInstance().apply { timeInMillis = this@Date.time }.get(Calendar.MINUTE)
    fun getSeconds(): Int = Calendar.getInstance().apply { timeInMillis = this@Date.time }.get(Calendar.SECOND)

    fun after(when_: Date): Boolean = time > when_.time
    fun before(when_: Date): Boolean = time < when_.time
    override fun equals(obj: Any?): Boolean = obj is Date && time == obj.time
    override fun hashCode(): Int = (time xor (time ushr 32)).toInt()
    override fun toString(): String = java.util.Date(time).toString()
}
