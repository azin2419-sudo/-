package com.example.data.model

data class OnlineMusicTrack(
    val id: String,
    val title: String,
    val artist: String,
    val audioUrl: String,
    val category: MusicCategory,
    val durationSeconds: Int,
    val emoji: String,
    val description: String
)

enum class MusicCategory(val titleFa: String, val emoji: String) {
    ALL("همه آهنگ‌ها", "🎵"),
    PERSIAN_LEGENDS("اسطوره‌های خاطره‌انگیز", "📻"),
    HAYEDEH("بانو هایده", "👑"),
    GHOMAYSHI("سیاوش قمیشی", "🌧️"),
    MAHASTI("بانو مهستی", "🌸"),
    GOOGOOSH("گوگوش", "✨"),
    ROMANTIC("عاشقانه و دلنشین", "💖"),
    CALM_PMS("آرامش‌بخش دوران PMS", "🌿"),
    PIANO_CHILL("پیانو و خواب آرام", "🌙"),
    GUITAR_ACOUSTIC("گیتار ملایم", "🎸")
}

