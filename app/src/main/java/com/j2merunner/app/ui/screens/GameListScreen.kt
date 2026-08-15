package com.j2merunner.app.ui.screens

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.j2merunner.app.GameActivity
import com.j2merunner.app.GameRepository
import com.j2merunner.app.JarLoader
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameListScreen(
    onNavigateToSettings: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val repository = remember { GameRepository(context) }

    var games by remember { mutableStateOf<List<JarLoader.JarInfo>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let {
            // Handle selected JAR
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "𝐉𝟐𝐌𝐄 • 𝐑𝐔𝐍𝐍𝐄𝐑",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF00D4FF)
                        )
                        Text(
                            "Run • Play • Enjoy",
                            fontSize = 12.sp,
                            color = Color(0xFFB0B0C0)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0A0A1A)
                ),
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = Color(0xFF00D4FF)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    launcher.launch(arrayOf("*/*"))
                },
                containerColor = Color(0xFF0066CC),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    Icons.Default.FolderOpen,
                    contentDescription = "Browse JAR",
                    tint = Color.White
                )
            }
        },
        containerColor = Color(0xFF0A0A1A)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (games.isEmpty() && !isLoading) {
                EmptyState()
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(games) { game ->
                        GameCard(
                            game = game,
                            onClick = {
                                val intent = Intent(context, GameActivity::class.java).apply {
                                    putExtra(GameActivity.EXTRA_JAR_PATH, game.file.absolutePath)
                                }
                                context.startActivity(intent)
                                repository.addRecentGame(game.file.absolutePath, game.name)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GameCard(
    game: JarLoader.JarInfo,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF12122A)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFF0066CC), Color(0xFF00D4FF))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.VideogameAsset,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    game.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                Text(
                    "${game.vendor} • v${game.version}",
                    fontSize = 13.sp,
                    color = Color(0xFFB0B0C0)
                )
            }
        }
    }
}

@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.VideogameAsset,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = Color(0xFF1A1A3A)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "No games found",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFB0B0C0)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Tap the + button to browse for JAR files",
            fontSize = 14.sp,
            color = Color(0xFF606080),
            textAlign = TextAlign.Center
        )
    }
}
