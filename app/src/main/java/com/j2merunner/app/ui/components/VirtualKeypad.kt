package com.j2merunner.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VirtualKeypad(
    onKeyPress: (Int) -> Unit,
    onKeyRelease: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0A0A1A))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DPad(
                onUp = { onKeyPress(-1); onKeyRelease(-1) },
                onDown = { onKeyPress(-2); onKeyRelease(-2) },
                onLeft = { onKeyPress(-3); onKeyRelease(-3) },
                onRight = { onKeyPress(-4); onKeyRelease(-4) },
                onCenter = { onKeyPress(-5); onKeyRelease(-5) }
            )

            ActionButtons(
                onSoftLeft = { onKeyPress(-6); onKeyRelease(-6) },
                onSoftRight = { onKeyPress(-7); onKeyRelease(-7) }
            )
        }

        NumberPad(
            onKeyPress = onKeyPress,
            onKeyRelease = onKeyRelease
        )
    }
}

@Composable
private fun DPad(
    onUp: () -> Unit,
    onDown: () -> Unit,
    onLeft: () -> Unit,
    onRight: () -> Unit,
    onCenter: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        KeyButton("▲", onUp, Modifier.size(48.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            KeyButton("◄", onLeft, Modifier.size(48.dp))
            KeyButton("●", onCenter, Modifier.size(48.dp), isCenter = true)
            KeyButton("►", onRight, Modifier.size(48.dp))
        }
        KeyButton("▼", onDown, Modifier.size(48.dp))
    }
}

@Composable
private fun ActionButtons(
    onSoftLeft: () -> Unit,
    onSoftRight: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        KeyButton("L", onSoftLeft, Modifier.size(56.dp, 36.dp))
        KeyButton("R", onSoftRight, Modifier.size(56.dp, 36.dp))
    }
}

@Composable
private fun NumberPad(
    onKeyPress: (Int) -> Unit,
    onKeyRelease: (Int) -> Unit
) {
    val keys = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf("*", "0", "#")
    )

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        keys.forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                row.forEach { key ->
                    val keyCode = when (key) {
                        "*" -> 42
                        "#" -> 35
                        else -> key.toInt() + 48
                    }
                    KeyButton(
                        label = key,
                        onClick = {
                            onKeyPress(keyCode)
                            onKeyRelease(keyCode)
                        },
                        modifier = Modifier.size(64.dp, 44.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun KeyButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isCenter: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isPressed) Color(0xFF00D4FF)
                else if (isCenter) Color(0xFF0066CC)
                else Color(0xFF1A1A3A)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            label,
            color = if (isPressed) Color.Black else Color.White,
            fontSize = if (isCenter) 14.sp else 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
