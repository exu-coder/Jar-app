package com.j2merunner.engine.midp

import android.media.MediaPlayer
import java.io.InputStream

/**
 * Bridge for javax.microedition.media.Manager
 * Audio management for J2ME games
 */
object Manager {

    const val TONE_DEVICE_LOCATOR = "device://tone"
    const val MIDI_DEVICE_LOCATOR = "device://midi"

    fun createPlayer(stream: InputStream, type: String): Player {
        return Player(stream, type)
    }

    fun createPlayer(locator: String): Player {
        return Player(null, locator)
    }

    fun createPlayer(stream: InputStream): Player {
        return Player(stream, "audio/midi")
    }

    fun getSupportedContentTypes(protocol: String?): Array<String> {
        return arrayOf("audio/midi", "audio/wav", "audio/mpeg")
    }

    fun getSupportedProtocols(contentType: String?): Array<String> {
        return arrayOf("file", "http")
    }

    fun playTone(note: Int, duration: Int, volume: Int) {
        // TODO: Implement tone generation
    }
}

class Player(private val stream: InputStream?, private val type: String) {

    companion object {
        const val UNREALIZED = 100
        const val REALIZED = 200
        const val PREFETCHED = 300
        const val STARTED = 400
        const val CLOSED = 0
    }

    private var state = UNREALIZED
    private var loopCount = 1
    private var listeners = mutableListOf<PlayerListener>()
    private var mediaPlayer: MediaPlayer? = null

    fun realize() {
        state = REALIZED
        // Initialize media player
    }

    fun prefetch() {
        if (state < REALIZED) realize()
        state = PREFETCHED
    }

    fun start() {
        if (state < PREFETCHED) prefetch()
        state = STARTED
        // Start playback
    }

    fun stop() {
        if (state == STARTED) {
            state = PREFETCHED
            // Stop playback
        }
    }

    fun close() {
        state = CLOSED
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun deallocate() {
        if (state == STARTED) stop()
        state = REALIZED
    }

    fun setLoopCount(count: Int) {
        loopCount = count
    }

    fun getState(): Int = state

    fun getDuration(): Long = mediaPlayer?.duration?.toLong() ?: -1

    fun getMediaTime(): Long = mediaPlayer?.currentPosition?.toLong() ?: 0

    fun setMediaTime(now: Long): Long {
        mediaPlayer?.seekTo(now.toInt())
        return getMediaTime()
    }

    fun getContentType(): String = type

    fun addPlayerListener(listener: PlayerListener) {
        listeners.add(listener)
    }

    fun removePlayerListener(listener: PlayerListener) {
        listeners.remove(listener)
    }
}

interface PlayerListener {
    fun playerUpdate(player: Player, event: String, eventData: Any?)
}

class VolumeControl {
    fun setLevel(level: Int): Int = level
    fun getLevel(): Int = 100
    fun setMute(mute: Boolean): Boolean = mute
    fun isMuted(): Boolean = false
}
