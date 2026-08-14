package com.example.data.model

import java.util.UUID

data class SurpriseDateProposal(
    val id: String = UUID.randomUUID().toString(),
    val coupleTitle: String = "پوریا ❤️ فرشته",
    val whenTimeFa: String,
    val whereLocationFa: String,
    val specialSurpriseFa: String,
    val outfitTipFa: String,
    val moodEmoji: String,
    val timestamp: Long = System.currentTimeMillis()
)

object SurpriseDateDeck {
    val curatedDateProposals: List<SurpriseDateProposal> = listOf(
        SurpriseDateProposal(
            whenTimeFa = "غروب پنج‌شنبه ساعت ۱۸:۳۰ 🌇",
            whereLocationFa = "کافه دنج بام با منظره پانورامای شهر و هوای عالی ☕🏙️",
            specialSurpriseFa = "پوریا قراره یه شاخه گل رز صورتی با عطر بی‌نظیر تقدیم فرشته کنه و صورت‌حساب رو کامل مهمون کنه 🌹👑",
            outfitTipFa = "تیپ شیک و راحت با رنگ‌های صورتی یا طوسی روشن 👗",
            moodEmoji = "☕"
        ),
        SurpriseDateProposal(
            whenTimeFa = "جمعه این هفته ساعت ۲۰:۰۰ 🕯️",
            whereLocationFa = "رستوران ایتالیایی زیر نور ملایم شمع با موسیقی لایت 🍝🍷",
            specialSurpriseFa = "پوریا برات پاستای دست‌ساز آلفردو و دسر تیرامیسو سفارش می‌ده و تمام مدت نگاهت می‌کنه ✨💖",
            outfitTipFa = "پیراهن رمانتیک و ملایم 🌸",
            moodEmoji = "🍝"
        ),
        SurpriseDateProposal(
            whenTimeFa = "فردا عصر ساعت ۱۷:۰۰ 🍦",
            whereLocationFa = "پیاده‌روی دونفره در پارک و خوردن بستنی قیفی دونفره 🌳🌸",
            specialSurpriseFa = "دست تو دست هم زیر سایه درختا قدم می‌زنید و پوریا کلی حرف‌های قشنگ برات داره 🥺❤️",
            outfitTipFa = "لباس اسپرت و کتونی راحت برای قدم زدن 👟",
            moodEmoji = "🍦"
        ),
        SurpriseDateProposal(
            whenTimeFa = "شنبه شب ساعت ۱۹:۰۰ 🎬",
            whereLocationFa = "سینما، فیلم رمانتیک و صندلی‌های ویژه دونفره 🍿🎥",
            specialSurpriseFa = "پوریا یه سطل بزرگ پاپ‌کورن و خوراکی‌های مورد علاقه فرشته رو می‌خره 🍫🥤",
            outfitTipFa = "استایل کژوال و هودی راحت 🍿",
            moodEmoji = "🎬"
        ),
        SurpriseDateProposal(
            whenTimeFa = "صبح جمعه ساعت ۹:۳۰ ☀️",
            whereLocationFa = "پیک‌نیک عاشقانه در طبیعت با زیرانداز و دمنوش گرم 🧺🍓",
            specialSurpriseFa = "پوریا برات توت‌فرنگی تازه و پنکیک دست‌پخت آماده می‌کنه با یک هدیه سورپرایز کوچیک 🎁🥞",
            outfitTipFa = "کلاه آفتابی و استایل شاد بهاری 👒",
            moodEmoji = "🧺"
        ),
        SurpriseDateProposal(
            whenTimeFa = "چهارشنبه شب ساعت ۲۰:۳۰ 🎡",
            whereLocationFa = "شهربازی، چرخ‌وفلک بزرگ و خوردن پشمک صورتی 🎠✨",
            specialSurpriseFa = "بالای چرخ و فلک وقتی به اوج آسمون رسیدین، پوریا در گوشت می‌گه که چقدر بی‌نهایت دوستت داره 💫❤️",
            outfitTipFa = "لباس اسپرت پرانرژی 🎡",
            moodEmoji = "🎡"
        ),
        SurpriseDateProposal(
            whenTimeFa = "عصر یکشنبه ساعت ۱۶:۳۰ 📚",
            whereLocationFa = "کتاب‌فروشی بزرگ و دنج و بعدش هات‌چاکلت گرم 📖☕",
            specialSurpriseFa = "پوریا کتاب شعر یا رمان مورد علاقه‌ات رو برات می‌خره و صفحه اولش یه متن عاشقانه اختصاصی می‌نویسه 📜🖋️",
            outfitTipFa = "استایل پاییزی/کلاسیک با شال ظریف 🧣",
            moodEmoji = "📚"
        ),
        SurpriseDateProposal(
            whenTimeFa = "جمعه شب ساعت ۲۱:۰۰ 🍳",
            whereLocationFa = "آشپزی دونفره در خانه با پخش آهنگ‌های هایده و قمیشی 🎶🍲",
            specialSurpriseFa = "پوریا پیش‌بند می‌بنده و همراه فرشته غذای محبوبتون رو می‌پزن و کلی می‌خندن و می‌رقصن 💃🕺",
            outfitTipFa = "لباس راحت خانگی و موهای باز 🌸",
            moodEmoji = "🍳"
        ),
        SurpriseDateProposal(
            whenTimeFa = "سه شنبه ساعت ۱۸:۰۰ 🗝️",
            whereLocationFa = "اتاق فرار هیجان‌انگیز دو نفره 🧩🚪",
            specialSurpriseFa = "پوریا مثل یک تکیه‌گاه امن کنارت معماها رو حل می‌کنه و هواتو داره 🛡️❤️",
            outfitTipFa = "لباس مشکی یا اسپرت راحت 👟",
            moodEmoji = "🗝️"
        ),
        SurpriseDateProposal(
            whenTimeFa = "آخر هفته ساعت ۱۹:۰۰ 🍰",
            whereLocationFa = "کافه روف‌گاردن روباز با کیک شکلاتی و چای زعفرانی 🍰☕",
            specialSurpriseFa = "پوریا برات شکلات دست‌ساز و کارت‌پستال دست‌نویس آورده تا خستگی کل هفته از تنت بره 💌🍫",
            outfitTipFa = "استایل مینیمال با اکسسوری‌های درخشان 💍",
            moodEmoji = "🍰"
        )
    )

    fun getRandomProposal(excludeId: String? = null): SurpriseDateProposal {
        val candidates = if (excludeId != null) curatedDateProposals.filter { it.id != excludeId } else curatedDateProposals
        return (candidates.ifEmpty { curatedDateProposals }).random()
    }
}
