package com.example.data.model

enum class QuoteCategory(val titleFa: String, val iconEmoji: String) {
    ALL("همه جملات", "💖"),
    ROMANTIC("عاشقانه و احساسی", "🌹"),
    PMS_COMFORT("دلجویی و آرامش PMS", "🌙"),
    SWEET_MORNING("صبح و شب‌بخیر مهر", "☀️"),
    DEEP_POETRY("اشعار ناب فارسی", "📜"),
    COMPLIMENTS("تعریف و ستایش زیبایی", "✨")
}

data class RomanticQuote(
    val id: String,
    val text: String,
    val sourceOrAuthor: String,
    val category: QuoteCategory,
    val orderIndex: Int,
    val isFavorite: Boolean = false,
    val isCustom: Boolean = false,
    val isGentleModeOnly: Boolean = false // Recommended when mood is sensitive
)
