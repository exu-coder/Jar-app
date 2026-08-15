package com.j2merunner.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.j2merunner.app.ui.components.VirtualKeypad

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamePlayerScreen(
    jarPath: String,
    onExit: () -> Unit
) {
    var showKeypad by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Game", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onExit) {
                        Icon(Icons.Default.Close, contentDescription = "Exit", tint = Color(0xFF00D4FF))
                    }
                },
                actions = {
                    IconButton(onClick = { showKeypad = !showKeypad }) {
                        Icon(Icons.Default.Pause, contentDescription = "Menu", tint = Color(0xFF00D4FF))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0A0A1A)
                )
            )
        },
        containerColor = Color(0xFF0A0A1A)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Game Canvas Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                val displayText = "Game Canvas\n(JAR: " + jarPath + ")"
                Text(
                    text = displayText,
                    color = Color(0xFF00D4FF)
                )
                // TODO: Integrate GameCanvasView here
            }

            // Virtual Keypad
            if (showKeypad) {
                VirtualKeypad(
                    onKeyPress = { keyCode ->
                        // TODO: Forward to game engine
                    },
                    onKeyRelease = { keyCode ->
                        // TODO: Forward to game engine
                    }
                )
            }
        }
    }
}
