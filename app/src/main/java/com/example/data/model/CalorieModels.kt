package com.example.data.model

import java.util.UUID

enum class UserGender(val titleFa: String) {
    FEMALE("بانو 👩"),
    MALE("آقا 👨")
}

enum class ActivityLevel(
    val titleFa: String,
    val descriptionFa: String,
    val multiplier: Float
) {
    SEDENTARY("کم‌تحرک / استراحت", "کار پشت‌میزی و فعالیت بدنی کم", 1.2f),
    LIGHT("فعالیت سبک", "۱ تا ۳ روز پیاده‌روی یا ورزش سبک در هفته", 1.375f),
    MODERATE("فعالیت متوسط", "۳ تا ۵ روز ورزش با شدت متوسط", 1.55f),
    ACTIVE("بسیار فعال / ورزشکار", "۶ تا ۷ روز ورزش سنگین یا کار پرتحرک", 1.725f),
    PMS_REST("دوران حساس / PMS", "متابولیسم کمی افزایش، نیاز به مراقبت و آرامش", 1.3f)
}

enum class WeightGoal(
    val titleFa: String,
    val descriptionFa: String,
    val calorieDelta: Int
) {
    LOSE_WEIGHT("کاهش وزن سالم", "کاهش تدریجی و ایمن چربی (حدود ۴۰۰ کالری کسری)", -400),
    MAINTAIN("تثبیت وزن و تناسب", "حفظ وزن فعلی و سلامت عمومی", 0),
    GAIN_WEIGHT("افزایش وزن و عضله‌سازی", "افزایش حجم سالم و انرژی بیشتر", 400)
}

data class CalorieProfile(
    val age: Int = 24,
    val weightKg: Float = 58f,
    val heightCm: Float = 165f,
    val gender: UserGender = UserGender.FEMALE,
    val activityLevel: ActivityLevel = ActivityLevel.LIGHT,
    val goal: WeightGoal = WeightGoal.MAINTAIN
) {
    // Mifflin-St Jeor Equation
    fun calculateBmr(): Int {
        val base = (10 * weightKg) + (6.25f * heightCm) - (5 * age)
        val bmr = if (gender == UserGender.FEMALE) {
            base - 161
        } else {
            base + 5
        }
        return bmr.toInt().coerceAtLeast(1000)
    }

    // TDEE = Total Daily Energy Expenditure
    fun calculateTdee(): Int {
        val bmr = calculateBmr()
        return (bmr * activityLevel.multiplier).toInt()
    }

    // Daily Target Calories based on Goal
    fun calculateTargetCalories(): Int {
        val tdee = calculateTdee()
        val target = tdee + goal.calorieDelta
        return target.coerceAtLeast(1200)
    }

    // Macro breakdown based on target
    fun calculateMacros(): MacroBreakdown {
        val totalKcal = calculateTargetCalories()
        // Standard balanced ratio: 25% Protein, 50% Carbs, 25% Fat
        val proteinKcal = totalKcal * 0.25f
        val carbsKcal = totalKcal * 0.50f
        val fatKcal = totalKcal * 0.25f

        val proteinGrams = (proteinKcal / 4).toInt()
        val carbsGrams = (carbsKcal / 4).toInt()
        val fatGrams = (fatKcal / 9).toInt()

        return MacroBreakdown(
            proteinGrams = proteinGrams,
            carbsGrams = carbsGrams,
            fatGrams = fatGrams,
            waterLiters = (weightKg * 0.033f).coerceIn(1.8f, 3.5f)
        )
    }
}

data class MacroBreakdown(
    val proteinGrams: Int,
    val carbsGrams: Int,
    val fatGrams: Int,
    val waterLiters: Float
)

enum class MealType(val titleFa: String, val emoji: String, val suggestedPercentage: Float) {
    BREAKFAST("صبحانه", "🍳", 0.25f),
    LUNCH("ناهار", "🥗", 0.35f),
    DINNER("شام", "🍲", 0.25f),
    SNACK("میان‌وعده و آرامش", "☕", 0.15f)
}

data class FoodLogItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val calories: Int,
    val mealType: MealType,
    val proteinGrams: Int = 0,
    val carbsGrams: Int = 0,
    val fatGrams: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val isAiDetected: Boolean = false
)

data class FoodAnalysisResult(
    val foodName: String,
    val estimatedCalories: Int,
    val calorieRange: String,
    val proteinGrams: Int,
    val carbsGrams: Int,
    val fatGrams: Int,
    val fiberGrams: Int,
    val ingredients: List<String>,
    val healthTip: String,
    val pmsBenefit: String,
    val confidenceScore: String
)
