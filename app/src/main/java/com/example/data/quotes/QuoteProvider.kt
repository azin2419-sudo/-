package com.example.data.quotes

import com.example.data.model.QuoteCategory
import com.example.data.model.RomanticQuote

object QuoteProvider {
    val initialQuotes: List<RomanticQuote> = listOf(
        RomanticQuote(
            id = "q_love_1",
            text = "خیلی دوستت دارم تمام دنیای من! قلب من فقط برای تو می‌تپه. ❤️",
            sourceOrAuthor = "عاشقانه روزانه",
            category = QuoteCategory.ROMANTIC,
            orderIndex = 1
        ),
        RomanticQuote(
            id = "q_beauty_1",
            text = "چقدر خوشگل شدی امروز فرشته‌ی من! چشم‌های قشنگت مثل ستاره می‌درخشه. ✨🌸",
            sourceOrAuthor = "ستایش و تمجید روز",
            category = QuoteCategory.COMPLIMENTS,
            orderIndex = 2
        ),
        RomanticQuote(
            id = "q_love_2",
            text = "امروز بیشتر از دیروز و کمتر از فردا دوستت دارم. عاشقتم تا ابد! 💖",
            sourceOrAuthor = "پیام مهر و عشق",
            category = QuoteCategory.ROMANTIC,
            orderIndex = 3
        ),
        RomanticQuote(
            id = "q_beauty_2",
            text = "لبخندت قشنگ‌ترین اتفاق روی زمینه؛ هر بار می‌خندی تمام دنیام روشن می‌شه. 👑",
            sourceOrAuthor = "ستایش زیبایی",
            category = QuoteCategory.COMPLIMENTS,
            orderIndex = 4
        ),
        RomanticQuote(
            id = "q_love_3",
            text = "فقط خواستم بهت یادآوری کنم که بی‌نهایت دوستت دارم و برام باارزش‌ترینی. 🌷🤍",
            sourceOrAuthor = "عاشقانه همیشگی",
            category = QuoteCategory.ROMANTIC,
            orderIndex = 5
        ),
        RomanticQuote(
            id = "q_beauty_3",
            text = "چقدر تو ماهی آخه! تو خوشگل‌ترین و مهربون‌ترین پاداش زندگی منی. 💫❤️",
            sourceOrAuthor = "تعریف و دلدادگی",
            category = QuoteCategory.COMPLIMENTS,
            orderIndex = 6
        ),
        RomanticQuote(
            id = "q_pms_1",
            text = "امروز فقط استراحت کن قشنگم؛ هیچ کاری مهم‌تر از آرامش و سلامت تو در دنیا نیست. ☕🍫",
            sourceOrAuthor = "دلجویی و مراقبت PMS",
            category = QuoteCategory.PMS_COMFORT,
            orderIndex = 7,
            isGentleModeOnly = true
        ),
        RomanticQuote(
            id = "q_love_4",
            text = "من از عهد آدم تو را دوست دارم / از آغاز عالم تو را دوست دارم",
            sourceOrAuthor = "قیصر امین‌پور",
            category = QuoteCategory.DEEP_POETRY,
            orderIndex = 8
        ),
        RomanticQuote(
            id = "q_morning_1",
            text = "صبح بخیر زیباترین دلیل شروع روزم! چقدر خوشگل و دلنشینی، امروز روز توئه. ☀️🌸",
            sourceOrAuthor = "صبح بخیر عاشقانه",
            category = QuoteCategory.SWEET_MORNING,
            orderIndex = 9
        ),
        RomanticQuote(
            id = "q_pms_2",
            text = "می‌دونم امروز شاید کمی دلت بی‌قرار باشه یا دردت بیاد، اما یادت نره من همیشه پشتتم و جونم برات در میره. 🥺❤️",
            sourceOrAuthor = "پیام آرامش در PMS",
            category = QuoteCategory.PMS_COMFORT,
            orderIndex = 10,
            isGentleModeOnly = true
        ),
        RomanticQuote(
            id = "q_love_5",
            text = "دوست داشتن تو نه برنامه می‌خواهد و نه دلیل، مثل نفس کشیدن خودکار و حیاتی است.",
            sourceOrAuthor = "عاشقانه دلنشین",
            category = QuoteCategory.ROMANTIC,
            orderIndex = 11
        ),
        RomanticQuote(
            id = "q_beauty_4",
            text = "تو فقط قشنگ‌ترین دختر این جهان نیستی؛ تو تمام نور و زیبایی زندگی منی.",
            sourceOrAuthor = "ستایش زیبایی",
            category = QuoteCategory.COMPLIMENTS,
            orderIndex = 12
        ),
        RomanticQuote(
            id = "q_love_6",
            text = "دیدار یار غایب دانی چه ذوق دارد؟ / ابری که در بیابان بر تشنه‌ای ببارد",
            sourceOrAuthor = "سعدی شیرازی",
            category = QuoteCategory.DEEP_POETRY,
            orderIndex = 13
        ),
        RomanticQuote(
            id = "q_morning_2",
            text = "شب که می‌شود، دلتنگی‌ام برای صدایت به ستاره‌ها می‌رسد. شبت پر از خواب‌های شیرین جان دلم. 🌙✨",
            sourceOrAuthor = "شب بخیر رمانتیک",
            category = QuoteCategory.SWEET_MORNING,
            orderIndex = 14
        ),
        RomanticQuote(
            id = "q_pms_3",
            text = "هر حس و حالی که امروز داری کاملاً طبیعیه. اگه بی‌حوصله‌ای، من با کمال میل سکوت می‌کنم و مراقبتم. 🌸🤍",
            sourceOrAuthor = "همدلی روزهای حساس",
            category = QuoteCategory.PMS_COMFORT,
            orderIndex = 15,
            isGentleModeOnly = true
        ),
        RomanticQuote(
            id = "q_love_7",
            text = "حضورت مثل عطر بهارنارنج در هوای بارانی، حال دل مرا همیشه خوب می‌کند.",
            sourceOrAuthor = "دل‌نوشته ناب",
            category = QuoteCategory.ROMANTIC,
            orderIndex = 16
        ),
        RomanticQuote(
            id = "q_beauty_5",
            text = "ظرافت دست‌هایت و زلالی کلامت، زیباترین شعری است که کائنات سروده است. چقدر تو بی‌نقصی! 🌹",
            sourceOrAuthor = "تعریف و تمجید",
            category = QuoteCategory.COMPLIMENTS,
            orderIndex = 17
        ),
        RomanticQuote(
            id = "q_pms_4",
            text = "کمی استراحت، یک فنجان دمنوش گرم و صدای آهنگ ملایم... امروز رو فقط برای آرامش خودت بذار گل من. 🛋️🎶",
            sourceOrAuthor = "آرامش و ریکاوری",
            category = QuoteCategory.PMS_COMFORT,
            orderIndex = 18,
            isGentleModeOnly = true
        ),
        RomanticQuote(
            id = "q_love_8",
            text = "من تماشای تو می‌کردم و غافل بودم / کز تماشای تو جمعی به تماشای منند",
            sourceOrAuthor = "هوشنگ ابتهاج (سایه)",
            category = QuoteCategory.DEEP_POETRY,
            orderIndex = 19
        ),
        RomanticQuote(
            id = "q_love_9",
            text = "جهان بدون تو مثل یک نقاشی بدون رنگ است؛ خاکستری و بی‌جان. همیشه برایم بمان، خیلی دوستت دارم. ❤️🕊️",
            sourceOrAuthor = "عاشقانه ناب",
            category = QuoteCategory.ROMANTIC,
            orderIndex = 20
        )
    )

    fun getQuoteForDay(index: Int, quotesList: List<RomanticQuote> = initialQuotes): RomanticQuote {
        if (quotesList.isEmpty()) return initialQuotes.first()
        val safeIndex = ((index % quotesList.size) + quotesList.size) % quotesList.size
        return quotesList[safeIndex]
    }

    fun getGentleComfortQuote(): RomanticQuote {
        return initialQuotes.filter { it.isGentleModeOnly }.randomOrNull()
            ?: RomanticQuote(
                id = "gentle_default",
                text = "امروز فقط آرامش و استراحت سهم توست. هیچ چیزی جز لبخند و آسایشت مهم نیست عزیزم. ☕🌸",
                sourceOrAuthor = "حالت مراقبت ویژه",
                category = QuoteCategory.PMS_COMFORT,
                orderIndex = 0,
                isGentleModeOnly = true
            )
    }
}
