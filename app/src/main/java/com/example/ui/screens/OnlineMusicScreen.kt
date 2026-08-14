package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MusicCategory
import com.example.data.model.OnlineMusicTrack
import com.example.ui.theme.SleekBackground
import com.example.ui.theme.SleekBorderLight
import com.example.ui.theme.SleekPinkLight
import com.example.ui.theme.SleekPinkPrimary
import com.example.ui.theme.SleekTextMuted
import com.example.ui.theme.SleekTextPrimary
import com.example.ui.theme.SleekTextSecondary
import com.example.util.MusicPlayerState

@Composable
fun OnlineMusicScreen(
    tracks: List<OnlineMusicTrack>,
    playerState: MusicPlayerState,
    onTogglePlayTrack: (OnlineMusicTrack) -> Unit,
    onSeekTrack: (Int) -> Unit,
    onVoiceGreeting: () -> Unit,
    partnerName: String,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf<MusicCategory?>(null) }

    val filteredTracks = if (selectedCategory == null) {
        tracks
    } else {
        tracks.filter { it.category == selectedCategory }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SleekBackground)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Top Card: Voice Greeting & Online Status
        Card(
            modifier = Modifier.fillMaxWidth().testTag("music_top_banner"),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            border = BorderStroke(1.dp, SleekBorderLight)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SleekPinkLight
                        ) {
                            Text(
                                text = "موسیقی آنلاین 🎵",
                                style = MaterialTheme.typography.labelSmall,
                                color = SleekPinkPrimary,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "ملودی‌های آرامش‌بخش برای $partnerName",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = SleekTextPrimary
                    )
                    Text(
                        text = "پخش آنلاین با کیفیت بالا برای ریلکسیشن و دوران حساس",
                        style = MaterialTheme.typography.bodySmall,
                        color = SleekTextMuted
                    )
                }

                // Voice Welcome Button
                Surface(
                    shape = CircleShape,
                    color = SleekPinkPrimary,
                    modifier = Modifier
                        .size(52.dp)
                        .clickable { onVoiceGreeting() }
                        .testTag("voice_greeting_btn"),
                    shadowElevation = 4.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "پخش صدای خوش‌آمد: فرشته خوشگلم به اپلیکیشن خودت خوش اومدی",
                            tint = Color.White,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            }
        }

        // Active Player Bar (if a track is selected)
        if (playerState.currentTrack != null) {
            val track = playerState.currentTrack
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("active_music_player_card"),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = SleekPinkLight.copy(alpha = 0.8f)
                ),
                border = BorderStroke(1.5.dp, SleekPinkPrimary),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = Color.White,
                                modifier = Modifier.size(48.dp),
                                shadowElevation = 2.dp
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(text = track.emoji, fontSize = 22.sp)
                                }
                            }

                            Column {
                                Text(
                                    text = track.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = SleekTextPrimary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "${track.artist} • ${track.category.titleFa}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = SleekTextSecondary
                                )
                            }
                        }

                        // Play/Pause Control
                        Surface(
                            shape = CircleShape,
                            color = SleekPinkPrimary,
                            modifier = Modifier
                                .size(48.dp)
                                .clickable { onTogglePlayTrack(track) }
                                .testTag("player_play_pause_btn"),
                            shadowElevation = 3.dp
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                if (playerState.isLoading) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(22.dp),
                                        color = Color.White,
                                        strokeWidth = 2.5.dp
                                    )
                                } else {
                                    Icon(
                                        imageVector = if (playerState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                        contentDescription = if (playerState.isPlaying) "توقف" else "پخش",
                                        tint = Color.White,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }
                        }
                    }

                    // Progress Slider
                    if (playerState.durationMs > 0) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Slider(
                            value = playerState.currentPositionMs.toFloat(),
                            onValueChange = { onSeekTrack(it.toInt()) },
                            valueRange = 0f..playerState.durationMs.toFloat(),
                            modifier = Modifier.fillMaxWidth().testTag("music_slider"),
                            colors = SliderDefaults.colors(
                                thumbColor = SleekPinkPrimary,
                                activeTrackColor = SleekPinkPrimary,
                                inactiveTrackColor = Color.White
                            )
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = formatTime(playerState.currentPositionMs),
                                style = MaterialTheme.typography.labelSmall,
                                color = SleekTextMuted
                            )
                            Text(
                                text = formatTime(playerState.durationMs),
                                style = MaterialTheme.typography.labelSmall,
                                color = SleekTextMuted
                            )
                        }
                    }

                    if (playerState.errorMessage != null) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = playerState.errorMessage,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFFE11D48),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Category Filter Chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            item {
                FilterChip(
                    selected = selectedCategory == null,
                    onClick = { selectedCategory = null },
                    label = { Text("همه آهنگ‌ها 🎧") },
                    shape = RoundedCornerShape(18.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = SleekPinkPrimary,
                        selectedLabelColor = Color.White
                    )
                )
            }
            items(MusicCategory.entries.filter { it != MusicCategory.ALL }) { cat ->
                FilterChip(
                    selected = selectedCategory == cat,
                    onClick = { selectedCategory = if (selectedCategory == cat) null else cat },
                    label = { Text("${cat.emoji} ${cat.titleFa}") },
                    shape = RoundedCornerShape(18.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = SleekPinkPrimary,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        // Tracks List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(filteredTracks, key = { it.id }) { track ->
                val isCurrent = playerState.currentTrack?.id == track.id
                val isPlayingCurrent = isCurrent && playerState.isPlaying

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTogglePlayTrack(track) }
                        .testTag("track_item_${track.id}"),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isCurrent) SleekPinkLight.copy(alpha = 0.5f) else Color.White
                    ),
                    border = BorderStroke(
                        width = if (isCurrent) 1.5.dp else 1.dp,
                        color = if (isCurrent) SleekPinkPrimary else SleekBorderLight
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = if (isCurrent) 3.dp else 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = if (isCurrent) SleekPinkPrimary else SleekPinkLight,
                                modifier = Modifier.size(46.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    if (isPlayingCurrent) {
                                        Icon(
                                            imageVector = Icons.Default.GraphicEq,
                                            contentDescription = "در حال پخش",
                                            tint = Color.White,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    } else {
                                        Text(
                                            text = track.emoji,
                                            fontSize = 20.sp
                                        )
                                    }
                                }
                            }

                            Column {
                                Text(
                                    text = track.title,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isCurrent) SleekPinkPrimary else SleekTextPrimary
                                )
                                Text(
                                    text = track.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = SleekTextMuted,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        // Play/Pause Action
                        Surface(
                            shape = CircleShape,
                            color = if (isCurrent) SleekPinkPrimary else Color.Transparent,
                            border = if (!isCurrent) BorderStroke(1.dp, SleekBorderLight) else null,
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (isPlayingCurrent) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = "پخش",
                                    tint = if (isCurrent) Color.White else SleekPinkPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun formatTime(millis: Int): String {
    val totalSeconds = millis / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}
