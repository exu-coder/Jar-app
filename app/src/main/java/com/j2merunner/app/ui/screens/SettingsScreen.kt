package com.j2merunner.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit
) {
    var soundEnabled by remember { mutableStateOf(true) }
    var vibrationEnabled by remember { mutableStateOf(true) }
    var screenScaling by remember { mutableStateOf(1.0f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF00D4FF)
                        )
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SettingsSection(title = "Audio") {
                SettingsSwitch(
                    title = "Sound Effects",
                    checked = soundEnabled,
                    onCheckedChange = { soundEnabled = it }
                )
            }

            SettingsSection(title = "Controls") {
                SettingsSwitch(
                    title = "Vibration",
                    checked = vibrationEnabled,
                    onCheckedChange = { vibrationEnabled = it }
                )
            }

            SettingsSection(title = "Display") {
                Text(
                    "Screen Scaling: ${(screenScaling * 100).toInt()}%",
                    color = Color.White,
                    fontSize = 16.sp
                )
                Slider(
                    value = screenScaling,
                    onValueChange = { screenScaling = it },
                    valueRange = 0.5f..2.0f,
                    steps = 5,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFF00D4FF),
                        activeTrackColor = Color(0xFF0066CC)
                    )
                )
            }

            SettingsSection(title = "About") {
                Text(
                    "𝐉𝟐𝐌𝐄 • 𝐑𝐔𝐍𝐍𝐄𝐑 v1.0.0",
                    color = Color(0xFFB0B0C0),
                    fontSize = 14.sp
                )
                Text(
                    "Run classic J2ME games on modern Android",
                    color = Color(0xFF606080),
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Text(
            title,
            color = Color(0xFF00D4FF),
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF12122A)
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
private fun SettingsSwitch(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, color = Color.White, fontSize = 16.sp)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color(0xFF00D4FF),
                checkedTrackColor = Color(0xFF0066CC)
            )
        )
    }
}
