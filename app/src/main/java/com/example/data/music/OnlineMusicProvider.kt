package com.example.data.music

import com.example.data.model.MusicCategory
import com.example.data.model.OnlineMusicTrack

object OnlineMusicProvider {
    val curatedTracks = listOf(
        OnlineMusicTrack(
            id = "track_1",
            title = "Acoustic Love Breeze",
            artist = "Acoustic Romantic",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
            category = MusicCategory.ROMANTIC,
            durationSeconds = 372,
            emoji = "🌹",
            description = "ملودی احساسی و دلنشین برای لحظات دو نفره"
        ),
        OnlineMusicTrack(
            id = "track_2",
            title = "Gentle Piano for Fereshteh",
            artist = "Piano Whispers",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
            category = MusicCategory.PIANO_CHILL,
            durationSeconds = 423,
            emoji = "🎹",
            description = "پیانوی آرام و تسکین‌دهنده احساسات و خستگی"
        ),
        OnlineMusicTrack(
            id = "track_3",
            title = "PMS Relief & Deep Serenity",
            artist = "Calm Waters",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3",
            category = MusicCategory.CALM_PMS,
            durationSeconds = 345,
            emoji = "🌿",
            description = "موسیقی درمانی برای کاهش تنش‌ها و درد روزهای سخت"
        ),
        OnlineMusicTrack(
            id = "track_4",
            title = "Sweet Starlight Melodies",
            artist = "Starry Nights",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3",
            category = MusicCategory.ROMANTIC,
            durationSeconds = 302,
            emoji = "✨",
            description = "نوای ملایم گیتار و احساس بی‌پایان مهر و دوستی"
        ),
        OnlineMusicTrack(
            id = "track_5",
            title = "Warm Sunset Glow",
            artist = "Acoustic Harmony",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-8.mp3",
            category = MusicCategory.GUITAR_ACOUSTIC,
            durationSeconds = 330,
            emoji = "🌅",
            description = "آرامش عصرگاهی و دل‌گرمی در کنار یار"
        ),
        OnlineMusicTrack(
            id = "track_6",
            title = "Lavender Sleep & Meditation",
            artist = "Zen Atmosphere",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-9.mp3",
            category = MusicCategory.CALM_PMS,
            durationSeconds = 398,
            emoji = "🌙",
            description = "تنظیم ویژه ریلکسیشن و خواب عمیق و راحت"
        )
    )
}
