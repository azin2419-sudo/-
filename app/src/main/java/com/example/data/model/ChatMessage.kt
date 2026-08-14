package com.example.data.model

import java.util.UUID

enum class MessageSender {
    ME,        // Sender (You / عشقم)
    PARTNER    // Partner (فرشته / جان دلم)
}

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val sender: MessageSender,
    val senderName: String,
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isHeartReacted: Boolean = false,
    val isSpecialLoveNote: Boolean = false
)
