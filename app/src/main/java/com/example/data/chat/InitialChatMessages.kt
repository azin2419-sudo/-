package com.example.data.chat

import com.example.data.model.ChatMessage
import com.example.data.model.MessageSender
import java.util.UUID

object InitialChatMessages {
    fun getDefaultMessages(partnerName: String, nickname: String): List<ChatMessage> {
        val now = System.currentTimeMillis()
        val oneHour = 3600 * 1000L
        return listOf(
            ChatMessage(
                id = "msg_1",
                sender = MessageSender.PARTNER,
                senderName = partnerName,
                text = "سلام عشق من! امروز چطوری؟ روزت خوب شروع شد؟ 🌸",
                timestamp = now - (3 * oneHour),
                isHeartReacted = true
            ),
            ChatMessage(
                id = "msg_2",
                sender = MessageSender.ME,
                senderName = "من",
                text = "سلام قشنگم! با دیدن پیام تو عالی شروع شد. یادم بود امروز هوا سرده، حواست به خودت باشه ها ☕💖",
                timestamp = now - (2 * oneHour),
                isHeartReacted = true
            ),
            ChatMessage(
                id = "msg_3",
                sender = MessageSender.PARTNER,
                senderName = partnerName,
                text = "ممنونم مهربونم! مرسی که همیشه هوامو داری و درکم می‌کنی 🥰",
                timestamp = now - (1 * oneHour),
                isHeartReacted = true
            ),
            ChatMessage(
                id = "msg_4",
                sender = MessageSender.ME,
                senderName = "من",
                text = "وظیفمه فرشته‌ی من! تو مهم‌ترین بخش زندگی منی و همیشه کنارت هستم ✨❤️",
                timestamp = now - (20 * 60 * 1000L),
                isHeartReacted = true,
                isSpecialLoveNote = true
            )
        )
    }
}
