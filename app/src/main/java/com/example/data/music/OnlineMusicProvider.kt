package com.example.data.music

import com.example.data.model.MusicCategory
import com.example.data.model.OnlineMusicTrack

object OnlineMusicProvider {
    val curatedTracks = listOf(
        // === بانو هایده (Hayedeh) ===
        OnlineMusicTrack(
            id = "hayedeh_soghati",
            title = "سوغاتی (وقتی میای)",
            artist = "بانو هایده",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
            category = MusicCategory.HAYEDEH,
            durationSeconds = 380,
            emoji = "👑",
            description = "«وقتی میای صدای پات از همه جاده‌ها میاد...» نوستالژی جاودان و ماندگار"
        ),
        OnlineMusicTrack(
            id = "hayedeh_shanehayat",
            title = "شانه‌هایت برای گریه",
            artist = "بانو هایده",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
            category = MusicCategory.HAYEDEH,
            durationSeconds = 410,
            emoji = "🌹",
            description = "«شانه‌هایت را برای گریه کردن دوست دارم...» آرامش ناب احساسی و عاشقانه"
        ),
        OnlineMusicTrack(
            id = "hayedeh_shabe_eshgh",
            title = "شب عشق",
            artist = "بانو هایده",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3",
            category = MusicCategory.HAYEDEH,
            durationSeconds = 345,
            emoji = "✨",
            description = "«امشب شب عشقه، همین امشبو داریم...» ترانه‌ای پر از شوق و شور زندگی"
        ),
        OnlineMusicTrack(
            id = "hayedeh_ravi",
            title = "راوی",
            artist = "بانو هایده",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3",
            category = MusicCategory.HAYEDEH,
            durationSeconds = 360,
            emoji = "💫",
            description = "«یکی به دادم برسه...» نوای دلنشین احساسات عمیق قلبی"
        ),

        // === سیاوش قمیشی (Siavash Ghomayshi) ===
        OnlineMusicTrack(
            id = "ghomayshi_jazireh",
            title = "جزیره",
            artist = "سیاوش قمیشی",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-5.mp3",
            category = MusicCategory.GHOMAYSHI,
            durationSeconds = 355,
            emoji = "🏝️",
            description = "«من همون جزیره بودم، خاکی و صمیمی و گرم...» ترانه نوستالژیک و رویایی بارانی"
        ),
        OnlineMusicTrack(
            id = "ghomayshi_yadegari",
            title = "یادگاری (تو بارون)",
            artist = "سیاوش قمیشی",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-6.mp3",
            category = MusicCategory.GHOMAYSHI,
            durationSeconds = 320,
            emoji = "🌧️",
            description = "«تو بارون که رفتی شبم زیر و رو شد...» پیانوی روح‌نواز و لطیف هوای دونفره"
        ),
        OnlineMusicTrack(
            id = "ghomayshi_farangis",
            title = "فرنگیس",
            artist = "سیاوش قمیشی",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-7.mp3",
            category = MusicCategory.GHOMAYSHI,
            durationSeconds = 340,
            emoji = "🎹",
            description = "«شب بود، بیابان بود، زمستان بود...» شاهکار خاطره‌انگیز ملودی و احساس"
        ),
        OnlineMusicTrack(
            id = "ghomayshi_parandeh",
            title = "پرنده مهاجر",
            artist = "سیاوش قمیشی",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-8.mp3",
            category = MusicCategory.GHOMAYSHI,
            durationSeconds = 330,
            emoji = "🕊️",
            description = "نوای ساز و پیانو برای لحظات دلتنگی و آرامش ذهن"
        ),

        // === بانو مهستی (Mahasti) ===
        OnlineMusicTrack(
            id = "mahasti_beya_benevisim",
            title = "بیا بنویسیم (میخونه)",
            artist = "بانو مهستی",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-9.mp3",
            category = MusicCategory.MAHASTI,
            durationSeconds = 390,
            emoji = "🌸",
            description = "«بیا بنویسیم روی خاک، رو درخت، رو پر پرنده...» عاشقانه‌ای پر از امید و زلالی"
        ),
        OnlineMusicTrack(
            id = "mahasti_delam_tange",
            title = "دلم تنگه",
            artist = "بانو مهستی",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-10.mp3",
            category = MusicCategory.MAHASTI,
            durationSeconds = 370,
            emoji = "💎",
            description = "«دلم تنگه برای گریه کردن، کجاست اون شونه‌های مهربونت...» دلجویی و تسکین دلتنگی"
        ),
        OnlineMusicTrack(
            id = "mahasti_asir",
            title = "اسیر عشق",
            artist = "بانو مهستی",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-11.mp3",
            category = MusicCategory.MAHASTI,
            durationSeconds = 340,
            emoji = "🌷",
            description = "ملودی کلاسیک و خاطره‌ساز با تحریرهای دلنشین اصیل"
        ),

        // === گوگوش (Googoosh) ===
        OnlineMusicTrack(
            id = "googoosh_gonjeshk",
            title = "من و گنجشک‌های خونه",
            artist = "گوگوش",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-12.mp3",
            category = MusicCategory.GOOGOOSH,
            durationSeconds = 315,
            emoji = "🕊️",
            description = "«دیدنت بهانه‌مونه...» شیرین‌ترین ترانه صبحگاهی و پر از حال خوب"
        ),
        OnlineMusicTrack(
            id = "googoosh_hamsafar",
            title = "همسفر",
            artist = "گوگوش",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-13.mp3",
            category = MusicCategory.GOOGOOSH,
            durationSeconds = 365,
            emoji = "✨",
            description = "«تو از شهر غریب بی‌نشونی اومدی، تو با اسب سفید مهربونی اومدی...» شاهکار ماندگار"
        ),
        OnlineMusicTrack(
            id = "googoosh_gharibeh",
            title = "غریب آشنا",
            artist = "گوگوش",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-14.mp3",
            category = MusicCategory.GOOGOOSH,
            durationSeconds = 350,
            emoji = "💫",
            description = "«ای غریب آشنا، بگو از کدوم دیاری...» خاطره مشترک قلب‌های مهربان"
        ),

        // === ریلکسیشن و آرامش PMS و خواب ===
        OnlineMusicTrack(
            id = "track_pms_relief",
            title = "PMS Relief & Deep Serenity",
            artist = "Calm Waters & Piano",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-15.mp3",
            category = MusicCategory.CALM_PMS,
            durationSeconds = 345,
            emoji = "🌿",
            description = "موسیقی درمانی با فرکانس ۴۳۲ هرتز برای کاهش تنش، دل‌پیچه و دردهای PMS"
        ),
        OnlineMusicTrack(
            id = "track_lavender_sleep",
            title = "Lavender Sleep & Meditation",
            artist = "Zen Atmosphere",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-16.mp3",
            category = MusicCategory.PIANO_CHILL,
            durationSeconds = 398,
            emoji = "🌙",
            description = "تنظیم ویژه ریلکسیشن عمیق، مدیتیشن و خواب آرام و بی‌دغدغه"
        ),
        OnlineMusicTrack(
            id = "track_acoustic_love",
            title = "Acoustic Love Breeze",
            artist = "Acoustic Romantic",
            audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
            category = MusicCategory.GUITAR_ACOUSTIC,
            durationSeconds = 372,
            emoji = "🎸",
            description = "نوای ملایم گیتار فلامنکو و آکوستیک برای آرامش عصرگاهی"
        )
    )
}

