package com.j2merunner.app

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class GameRepository(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val jarLoader = JarLoader()

    data class GameEntry(
        val path: String,
        val name: String,
        val lastPlayed: Long = 0
    )

    suspend fun scanForJars(directory: File): List<JarLoader.JarInfo> = withContext(Dispatchers.IO) {
        directory.walkTopDown()
            .filter { it.isFile && it.extension.equals("jar", ignoreCase = true) }
            .mapNotNull { jarLoader.loadJar(it) }
            .toList()
    }

    fun getRecentGames(): List<GameEntry> {
        val json = prefs.getString(KEY_RECENT, "[]") ?: "[]"
        return parseRecentGames(json)
    }

    fun addRecentGame(path: String, name: String) {
        val games = getRecentGames().toMutableList()
        games.removeAll { it.path == path }
        games.add(0, GameEntry(path, name, System.currentTimeMillis()))
        if (games.size > MAX_RECENT) {
            games.subList(MAX_RECENT, games.size).clear()
        }
        prefs.edit().putString(KEY_RECENT, serializeRecentGames(games)).apply()
    }

    private fun parseRecentGames(json: String): List<GameEntry> {
        // Simplified parsing - in production use Gson/Moshi
        return emptyList()
    }

    private fun serializeRecentGames(games: List<GameEntry>): String {
        return "[]" // Simplified - in production use Gson/Moshi
    }

    companion object {
        private const val PREFS_NAME = "j2me_runner_prefs"
        private const val KEY_RECENT = "recent_games"
        private const val MAX_RECENT = 10
    }
}
