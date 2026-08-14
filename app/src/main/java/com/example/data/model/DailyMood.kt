package com.example.data.model

enum class DailyMood(
    val titleFa: String,
    val emoji: String,
    val isSensitiveOrBad: Boolean,
    val gentleCareAdvice: String
) {
    LOVING(
        titleFa = "عاشق و پرانرژی",
        emoji = "🥰",
        isSensitiveOrBad = false,
        gentleCareAdvice = "روز فوق‌العاده‌ای برای قرارهای رمانتیک و ابراز عشق پرشور است."
    ),
    HAPPY(
        titleFa = "شاداب و آرام",
        emoji = "🌸",
        isSensitiveOrBad = false,
        gentleCareAdvice = "انرژی مثبت او را با یک پیام پرمهر و شیرین ستایش کنید."
    ),
    NORMAL(
        titleFa = "معمولی و آرام",
        emoji = "☕",
        isSensitiveOrBad = false,
        gentleCareAdvice = "یک فنجان چای یا یادآوری کوچک نشان می‌دهد همیشه به یادش هستید."
    ),
    TIRED(
        titleFa = "خسته و بی‌رمق",
        emoji = "🛋️",
        isSensitiveOrBad = true,
        gentleCareAdvice = "به استراحت نیاز دارد؛ از کارهای سنگین معافش کنید و پیامی سبک و آرام بفرستید."
    ),
    SENSITIVE_PMS(
        titleFa = "حساس و دلتنگ (PMS)",
        emoji = "🥺",
        isSensitiveOrBad = true,
        gentleCareAdvice = "حالت مدارا فعال است. فقط گوش شنوا باشید، در آغوشش بگیرید و کلمات نوازش‌گرانه بگویید."
    ),
    PAINFUL(
        titleFa = "درد و گرفتگی شدید",
        emoji = "🩹",
        isSensitiveOrBad = true,
        gentleCareAdvice = "حالت سکوت و مراقبت ویژه: کیسه آب گرم، شکلات داغ، ماساژ ملایم و عدم ایجاد کوچکترین استرس."
    )
}
