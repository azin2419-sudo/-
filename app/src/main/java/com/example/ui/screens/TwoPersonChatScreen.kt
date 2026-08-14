package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ChatMessage
import com.example.data.model.MessageSender
import com.example.ui.theme.SleekBackground
import com.example.ui.theme.SleekBorderLight
import com.example.ui.theme.SleekPinkLight
import com.example.ui.theme.SleekPinkPrimary
import com.example.ui.theme.SleekTextMuted
import com.example.ui.theme.SleekTextPrimary
import com.example.ui.theme.SleekTextSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TwoPersonChatScreen(
    messages: List<ChatMessage>,
    partnerName: String,
    partnerNickname: String,
    onSendMessage: (MessageSender, String, Boolean) -> Unit,
    onToggleHeart: (String) -> Unit,
    onDeleteMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var inputText by remember { mutableStateOf("") }
    var currentSender by remember { mutableStateOf(MessageSender.ME) }
    var isSpecialLoveNote by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    // Auto-scroll to bottom when new message arrives
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SleekBackground)
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        // Chat Header Card with Sender Switcher
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("chat_header_card"),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            border = BorderStroke(1.dp, SleekBorderLight)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = if (currentSender == MessageSender.ME) SleekPinkLight else Color(0xFFF3E8FF),
                        modifier = Modifier.size(42.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = if (currentSender == MessageSender.ME) "🤵" else "👰‍♀️",
                                fontSize = 20.sp
                            )
                        }
                    }

                    Column {
                        Text(
                            text = "گفتگوی دو نفره امن 💬",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = SleekTextPrimary
                        )
                        Text(
                            text = "ارسال‌کننده فعلی: ${if (currentSender == MessageSender.ME) "من (عشقم)" else partnerName}",
                            style = MaterialTheme.typography.bodySmall,
                            color = SleekPinkPrimary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Switch Sender Toggle Button
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = SleekPinkLight,
                    border = BorderStroke(1.dp, SleekBorderLight),
                    modifier = Modifier
                        .clickable {
                            currentSender = if (currentSender == MessageSender.ME) MessageSender.PARTNER else MessageSender.ME
                        }
                        .testTag("toggle_sender_button")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.SwapHoriz,
                            contentDescription = "تغییر ارسال‌کننده",
                            tint = SleekPinkPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "تغییر گوینده",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = SleekPinkPrimary
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Messages List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(vertical = 6.dp)
        ) {
            items(messages, key = { it.id }) { msg ->
                val isMe = msg.sender == MessageSender.ME

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = if (isMe) Alignment.End else Alignment.Start
                ) {
                    Row(
                        modifier = Modifier.widthIn(max = 310.dp),
                        horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        if (!isMe) {
                            Surface(
                                shape = CircleShape,
                                color = SleekPinkLight,
                                modifier = Modifier
                                    .size(28.dp)
                                    .padding(bottom = 2.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(text = "🌸", fontSize = 14.sp)
                                }
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                        }

                        // Message Bubble Card
                        Card(
                            shape = RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp,
                                bottomStart = if (isMe) 20.dp else 4.dp,
                                bottomEnd = if (isMe) 4.dp else 20.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = if (msg.isSpecialLoveNote) {
                                    Color(0xFFFFF1F2)
                                } else if (isMe) {
                                    SleekPinkPrimary
                                } else {
                                    Color.White
                                }
                            ),
                            border = BorderStroke(
                                width = 1.dp,
                                color = if (msg.isSpecialLoveNote) SleekPinkPrimary else if (isMe) Color.Transparent else SleekBorderLight
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp),
                            modifier = Modifier.testTag("chat_msg_${msg.id}")
                        ) {
                            Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
                                if (msg.isSpecialLoveNote) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = SleekPinkPrimary,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Text(
                                            text = "یادداشت عاشقانه ویژه",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = SleekPinkPrimary
                                        )
                                    }
                                }

                                Text(
                                    text = msg.text,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = if (msg.isSpecialLoveNote) SleekTextPrimary else if (isMe) Color.White else SleekTextPrimary,
                                    lineHeight = 22.sp
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = formatTimeAgo(msg.timestamp),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (isMe && !msg.isSpecialLoveNote) Color.White.copy(alpha = 0.75f) else SleekTextMuted,
                                        fontSize = 10.sp
                                    )

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        // Heart reaction button
                                        IconButton(
                                            onClick = { onToggleHeart(msg.id) },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                imageVector = if (msg.isHeartReacted) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                                contentDescription = "قلب",
                                                tint = if (msg.isHeartReacted) {
                                                    if (isMe && !msg.isSpecialLoveNote) Color.White else SleekPinkPrimary
                                                } else {
                                                    if (isMe && !msg.isSpecialLoveNote) Color.White.copy(alpha = 0.6f) else SleekTextMuted
                                                },
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }

                                        // Delete message button
                                        IconButton(
                                            onClick = { onDeleteMessage(msg.id) },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "حذف پیام",
                                                tint = if (isMe && !msg.isSpecialLoveNote) Color.White.copy(alpha = 0.6f) else SleekTextMuted,
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        if (isMe) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = CircleShape,
                                color = SleekPinkPrimary.copy(alpha = 0.2f),
                                modifier = Modifier
                                    .size(28.dp)
                                    .padding(bottom = 2.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(text = "💖", fontSize = 14.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Quick Love Templates
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val quickNotes = listOf("دوستت دارم ❤️", "مراقب خودت باش 🌸", "خیلی برام عزیزی ✨", "حالت خوبه؟ ☕")
            quickNotes.forEach { note ->
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, SleekBorderLight),
                    modifier = Modifier
                        .clickable {
                            inputText = note
                        }
                ) {
                    Text(
                        text = note,
                        style = MaterialTheme.typography.labelSmall,
                        color = SleekTextSecondary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // Input Area Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("chat_input_card"),
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            border = BorderStroke(1.dp, SleekBorderLight)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Special Love Note Toggle
                Surface(
                    shape = CircleShape,
                    color = if (isSpecialLoveNote) SleekPinkPrimary else SleekPinkLight,
                    modifier = Modifier
                        .size(38.dp)
                        .clickable { isSpecialLoveNote = !isSpecialLoveNote }
                        .testTag("special_note_toggle")
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "پیام ویژه",
                            tint = if (isSpecialLoveNote) Color.White else SleekPinkPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Text Input
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = {
                        Text(
                            text = if (currentSender == MessageSender.ME) "پیامی برای $partnerName بنویس..." else "پاسخ از طرف $partnerName...",
                            style = MaterialTheme.typography.bodySmall,
                            color = SleekTextMuted
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("chat_text_field"),
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SleekPinkPrimary,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    maxLines = 3
                )

                // Send Button
                Surface(
                    shape = CircleShape,
                    color = if (inputText.isNotBlank()) SleekPinkPrimary else SleekBorderLight,
                    modifier = Modifier
                        .size(44.dp)
                        .clickable(enabled = inputText.isNotBlank()) {
                            onSendMessage(currentSender, inputText, isSpecialLoveNote)
                            inputText = ""
                            isSpecialLoveNote = false
                        }
                        .testTag("send_chat_message_btn"),
                    shadowElevation = if (inputText.isNotBlank()) 2.dp else 0.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "ارسال پیام",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

private fun formatTimeAgo(timestamp: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
