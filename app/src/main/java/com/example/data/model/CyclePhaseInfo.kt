package com.example.data.model

import java.util.concurrent.TimeUnit

enum class CyclePhase(
    val titleFa: String,
    val titleEn: String,
    val description: String,
    val iconEmoji: String,
    val careTip: String
) {
    MENSTRUATION(
        titleFa = "دوره پریود",
        titleEn = "Period",
        description = "دوران استراحت، نوشیدن دمنوش گرم و نوازش روح",
        iconEmoji = "🌸",
        careTip = "برایش کیسه آب گرم، دمنوش بابونه یا زنجبیل و استراحت کامل فراهم کنید."
    ),
    FOLLICULAR(
        titleFa = "فاز شادابی و انرژی",
        titleEn = "Follicular",
        description = "افزایش انرژی، حس سرزندگی، لبخند و انگیزه بالا",
        iconEmoji = "🌿",
        careTip = "بهترین زمان برای برنامه‌های دونفره شاد، پیاده‌روی و تعریف از زیبایی اوست."
    ),
    OVULATION(
        titleFa = "فاز تخمک‌گذاری و درخشش",
        titleEn = "Ovulation",
        description = "اوج جذابیت، اعتماد به نفس و نشاط روحی",
        iconEmoji = "✨",
        careTip = "سورپرایزهای عاشقانه و هدیه‌های کوچک گل، خوشحالی او را دوچندان می‌کند."
    ),
    PMS_LUTEAL(
        titleFa = "فاز P.M.S (سندرم پیش از قاعدگی)",
        titleEn = "P.M.S Phase",
        description = "روزهای حساس و آسیب‌پذیر؛ نیاز به آرامش، درک عمیق و صبر مضاعف",
        iconEmoji = "🌙",
        careTip = "با لحن بسیار آرام صحبت کنید، شکلات تلخ و ماساژ شانه هدیه دهید و هیچ بحث یا تنشی ایجاد نکنید."
    )
}

data class CyclePhaseInfo(
    val currentDayInCycle: Int,
    val totalCycleLength: Int,
    val periodDuration: Int,
    val phase: CyclePhase,
    val isPmsActive: Boolean,
    val daysUntilNextPeriod: Int,
    val daysSincePeriodStart: Int,
    val statusBadgeFa: String,
    val notificationPmsText: String,
    val progressPercent: Float
) {
    companion object {
        fun calculate(
            lastPeriodStartDateMillis: Long,
            cycleLength: Int = 28,
            periodDuration: Int = 5,
            nowMillis: Long = System.currentTimeMillis()
        ): CyclePhaseInfo {
            val safeCycleLength = if (cycleLength <= 0) 28 else cycleLength
            val safePeriodDuration = if (periodDuration <= 0) 5 else periodDuration

            val diffMillis = nowMillis - lastPeriodStartDateMillis
            val totalDaysPassed = TimeUnit.MILLISECONDS.toDays(diffMillis).toInt()

            val rawDay = if (totalDaysPassed >= 0) {
                (totalDaysPassed % safeCycleLength) + 1
            } else {
                1
            }

            val currentDay = rawDay.coerceIn(1, safeCycleLength)
            val daysUntilNext = safeCycleLength - currentDay
            val daysSince = currentDay

            val phase: CyclePhase
            val isPms: Boolean

            when {
                currentDay <= safePeriodDuration -> {
                    phase = CyclePhase.MENSTRUATION
                    isPms = false
                }
                currentDay <= (safeCycleLength / 2 - 2) -> {
                    phase = CyclePhase.FOLLICULAR
                    isPms = false
                }
                currentDay <= (safeCycleLength / 2 + 2) -> {
                    phase = CyclePhase.OVULATION
                    isPms = false
                }
                else -> {
                    phase = CyclePhase.PMS_LUTEAL
                    isPms = true
                }
            }

            val statusBadge: String = when {
                phase == CyclePhase.MENSTRUATION -> "روز $currentDay از دوره پریود 🌸"
                isPms -> "فاز P.M.S 🌙 ($daysUntilNext روز مانده به پریودی)"
                phase == CyclePhase.OVULATION -> "فاز درخشش ✨ ($daysUntilNext روز تا دوره بعدی)"
                else -> "فاز شادابی 🌿 (روز $currentDay چرخه)"
            }

            val notificationPmsText: String = when {
                phase == CyclePhase.MENSTRUATION -> "🌸 روز $currentDay از شروع پریود (مراقبت ویژه)"
                isPms -> "🌙 وضعیت P.M.S: فقط $daysUntilNext روز مانده به پریود بعدی"
                else -> "✨ روز $currentDay از چرخه ($daysUntilNext روز مانده تا پریود بعدی)"
            }

            val progress = currentDay.toFloat() / safeCycleLength.toFloat()

            return CyclePhaseInfo(
                currentDayInCycle = currentDay,
                totalCycleLength = safeCycleLength,
                periodDuration = safePeriodDuration,
                phase = phase,
                isPmsActive = isPms,
                daysUntilNextPeriod = daysUntilNext,
                daysSincePeriodStart = daysSince,
                statusBadgeFa = statusBadge,
                notificationPmsText = notificationPmsText,
                progressPercent = progress.coerceIn(0f, 1f)
            )
        }
    }
}
