package com.example.data.model

data class PartnerProfile(
    val name: String = "فرشته",
    val nickname: String = "فرشته خوشگلم",
    val senderName: String = "پوریا",
    val lastPeriodStartDateMillis: Long = System.currentTimeMillis() - (18L * 24 * 60 * 60 * 1000), // Default 18 days ago (in PMS phase)
    val cycleLengthDays: Int = 28,
    val periodDurationDays: Int = 5,
    val notificationHour: Int = 9,
    val notificationMinute: Int = 0,
    val isNotificationEnabled: Boolean = true,
    val isGentleModeAuto: Boolean = true, // Automatic gentle notification during PMS or bad mood
    val isManualBadMoodToday: Boolean = false, // Manual override when she is feeling unwell today
    val currentQuoteIndex: Int = 0,
    val includePmsInNotification: Boolean = true,
    val selectedCategory: QuoteCategory = QuoteCategory.ALL,
    val isProfileCompleted: Boolean = false
)

