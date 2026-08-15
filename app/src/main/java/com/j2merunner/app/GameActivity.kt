package com.j2merunner.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.j2merunner.app.ui.screens.GamePlayerScreen

class GameActivity : ComponentActivity() {
    companion object {
        const val EXTRA_JAR_PATH = "jar_path"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val jarPath = intent.getStringExtra(EXTRA_JAR_PATH) ?: return finish()

        setContent {
            GamePlayerScreen(
                jarPath = jarPath,
                onExit = { finish() }
            )
        }
    }
}
