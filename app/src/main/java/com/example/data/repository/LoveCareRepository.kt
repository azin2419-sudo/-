package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.data.model.CyclePhaseInfo
import com.example.data.model.DailyMood
import com.example.data.model.PartnerProfile
import com.example.data.model.QuoteCategory
import com.example.data.model.RomanticQuote
import com.example.data.quotes.QuoteProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.json.JSONObject

class LoveCareRepository(private val context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("love_care_prefs", Context.MODE_PRIVATE)

    private val _profile = MutableStateFlow(loadProfile())
    val profile: StateFlow<PartnerProfile> = _profile.asStateFlow()

    private val _quotes = MutableStateFlow(loadQuotes())
    val quotes: StateFlow<List<RomanticQuote>> = _quotes.asStateFlow()

    private val _currentMood = MutableStateFlow(loadMood())
    val currentMood: StateFlow<DailyMood> = _currentMood.asStateFlow()

    private val _chatMessages = MutableStateFlow(loadChatMessages())
    val chatMessages: StateFlow<List<com.example.data.model.ChatMessage>> = _chatMessages.asStateFlow()

    private val _calorieProfile = MutableStateFlow(loadCalorieProfile())
    val calorieProfile: StateFlow<com.example.data.model.CalorieProfile> = _calorieProfile.asStateFlow()

    private val _dailyFoodLogs = MutableStateFlow(loadDailyFoodLogs())
    val dailyFoodLogs: StateFlow<List<com.example.data.model.FoodLogItem>> = _dailyFoodLogs.asStateFlow()

    fun updateProfile(newProfile: PartnerProfile) {
        _profile.value = newProfile
        saveProfile(newProfile)
    }

    fun updateMood(mood: DailyMood) {
        _currentMood.value = mood
        prefs.edit().putString("today_mood", mood.name).apply()
        // If mood is sensitive or painful, we can also auto-adjust gentle mode if enabled
        if (mood.isSensitiveOrBad && _profile.value.isGentleModeAuto) {
            updateProfile(_profile.value.copy(isManualBadMoodToday = true))
        } else if (!mood.isSensitiveOrBad && _profile.value.isManualBadMoodToday) {
            updateProfile(_profile.value.copy(isManualBadMoodToday = false))
        }
    }

    fun setManualBadMood(isBadMood: Boolean) {
        updateProfile(_profile.value.copy(isManualBadMoodToday = isBadMood))
    }

    fun advanceToNextQuote(): RomanticQuote {
        val allQuotes = _quotes.value
        val nextIndex = (_profile.value.currentQuoteIndex + 1) % if (allQuotes.isNotEmpty()) allQuotes.size else 1
        updateProfile(_profile.value.copy(currentQuoteIndex = nextIndex))
        return getCurrentQuote()
    }

    fun setSpecificQuoteIndex(index: Int) {
        val allQuotes = _quotes.value
        val safeIndex = if (allQuotes.isNotEmpty()) (index % allQuotes.size).coerceAtLeast(0) else 0
        updateProfile(_profile.value.copy(currentQuoteIndex = safeIndex))
    }

    fun getCurrentQuote(): RomanticQuote {
        val prof = _profile.value
        val cycleInfo = getCyclePhaseInfo()
        val allQuotes = _quotes.value

        // If gentle mode is active because of severe PMS or manual bad mood day:
        val isGentleActive = (prof.isManualBadMoodToday) ||
                (prof.isGentleModeAuto && (cycleInfo.isPmsActive || cycleInfo.phase == com.example.data.model.CyclePhase.MENSTRUATION || _currentMood.value.isSensitiveOrBad))

        if (isGentleActive) {
            val gentleQuotes = allQuotes.filter { it.isGentleModeOnly || it.category == QuoteCategory.PMS_COMFORT }
            if (gentleQuotes.isNotEmpty()) {
                val index = prof.currentQuoteIndex % gentleQuotes.size
                return gentleQuotes[index]
            }
        }

        // Otherwise regular ordered quote
        val filtered = when (prof.selectedCategory) {
            QuoteCategory.ALL -> allQuotes
            else -> allQuotes.filter { it.category == prof.selectedCategory }.ifEmpty { allQuotes }
        }

        return QuoteProvider.getQuoteForDay(prof.currentQuoteIndex, filtered)
    }

    fun addCustomQuote(text: String, author: String, category: QuoteCategory) {
        val currentList = _quotes.value.toMutableList()
        val newQuote = RomanticQuote(
            id = "custom_${System.currentTimeMillis()}",
            text = text,
            sourceOrAuthor = if (author.isBlank()) "دل‌نوشته اختصاصی" else author,
            category = category,
            orderIndex = currentList.size + 1,
            isCustom = true
        )
        currentList.add(newQuote)
        _quotes.value = currentList
        saveQuotes(currentList)
    }

    fun toggleFavorite(quoteId: String) {
        val currentList = _quotes.value.map {
            if (it.id == quoteId) it.copy(isFavorite = !it.isFavorite) else it
        }
        _quotes.value = currentList
        saveQuotes(currentList)
    }

    fun deleteQuote(quoteId: String) {
        val currentList = _quotes.value.filter { it.id != quoteId }
        _quotes.value = currentList
        saveQuotes(currentList)
    }

    fun sendChatMessage(sender: com.example.data.model.MessageSender, text: String, isSpecialLoveNote: Boolean = false) {
        if (text.isBlank()) return
        val currentList = _chatMessages.value.toMutableList()
        val prof = _profile.value
        val senderName = if (sender == com.example.data.model.MessageSender.ME) "من" else prof.name
        val newMsg = com.example.data.model.ChatMessage(
            sender = sender,
            senderName = senderName,
            text = text.trim(),
            timestamp = System.currentTimeMillis(),
            isSpecialLoveNote = isSpecialLoveNote
        )
        currentList.add(newMsg)
        _chatMessages.value = currentList
        saveChatMessages(currentList)
    }

    fun toggleHeartReactMessage(messageId: String) {
        val currentList = _chatMessages.value.map { msg ->
            if (msg.id == messageId) msg.copy(isHeartReacted = !msg.isHeartReacted) else msg
        }
        _chatMessages.value = currentList
        saveChatMessages(currentList)
    }

    fun deleteChatMessage(messageId: String) {
        val currentList = _chatMessages.value.filter { it.id != messageId }
        _chatMessages.value = currentList
        saveChatMessages(currentList)
    }

    fun clearChatHistory() {
        val prof = _profile.value
        val initial = com.example.data.chat.InitialChatMessages.getDefaultMessages(prof.name, prof.nickname)
        _chatMessages.value = initial
        saveChatMessages(initial)
    }

    private fun loadChatMessages(): List<com.example.data.model.ChatMessage> {
        val jsonString = prefs.getString("saved_chat_messages_json", null)
        val prof = loadProfile()
        if (jsonString.isNullOrEmpty()) {
            return com.example.data.chat.InitialChatMessages.getDefaultMessages(prof.name, prof.nickname)
        }
        return try {
            val jsonArray = JSONArray(jsonString)
            val list = mutableListOf<com.example.data.model.ChatMessage>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val senderStr = obj.optString("sender", com.example.data.model.MessageSender.ME.name)
                val sender = try { com.example.data.model.MessageSender.valueOf(senderStr) } catch (_: Exception) { com.example.data.model.MessageSender.ME }
                list.add(
                    com.example.data.model.ChatMessage(
                        id = obj.getString("id"),
                        sender = sender,
                        senderName = obj.optString("senderName", if (sender == com.example.data.model.MessageSender.ME) "من" else prof.name),
                        text = obj.getString("text"),
                        timestamp = obj.optLong("timestamp", System.currentTimeMillis()),
                        isHeartReacted = obj.optBoolean("isHeartReacted", false),
                        isSpecialLoveNote = obj.optBoolean("isSpecialLoveNote", false)
                    )
                )
            }
            if (list.isEmpty()) com.example.data.chat.InitialChatMessages.getDefaultMessages(prof.name, prof.nickname) else list
        } catch (_: Exception) {
            com.example.data.chat.InitialChatMessages.getDefaultMessages(prof.name, prof.nickname)
        }
    }

    private fun saveChatMessages(messages: List<com.example.data.model.ChatMessage>) {
        try {
            val jsonArray = JSONArray()
            for (m in messages) {
                val obj = JSONObject()
                obj.put("id", m.id)
                obj.put("sender", m.sender.name)
                obj.put("senderName", m.senderName)
                obj.put("text", m.text)
                obj.put("timestamp", m.timestamp)
                obj.put("isHeartReacted", m.isHeartReacted)
                obj.put("isSpecialLoveNote", m.isSpecialLoveNote)
                jsonArray.put(obj)
            }
            prefs.edit().putString("saved_chat_messages_json", jsonArray.toString()).apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getCyclePhaseInfo(): CyclePhaseInfo {
        val prof = _profile.value
        return CyclePhaseInfo.calculate(
            lastPeriodStartDateMillis = prof.lastPeriodStartDateMillis,
            cycleLength = prof.cycleLengthDays,
            periodDuration = prof.periodDurationDays
        )
    }

    private fun loadProfile(): PartnerProfile {
        val name = prefs.getString("partner_name", "جان دلم") ?: "جان دلم"
        val nickname = prefs.getString("partner_nickname", "فرشته من") ?: "فرشته من"
        val lastPeriod = prefs.getLong("last_period_millis", System.currentTimeMillis() - (18L * 24 * 60 * 60 * 1000))
        val cycleLength = prefs.getInt("cycle_length", 28)
        val periodDuration = prefs.getInt("period_duration", 5)
        val notifHour = prefs.getInt("notif_hour", 9)
        val notifMinute = prefs.getInt("notif_minute", 0)
        val notifEnabled = prefs.getBoolean("notif_enabled", true)
        val gentleAuto = prefs.getBoolean("gentle_auto", true)
        val badMoodToday = prefs.getBoolean("bad_mood_today", false)
        val quoteIndex = prefs.getInt("quote_index", 0)
        val includePms = prefs.getBoolean("include_pms_notif", true)
        val categoryStr = prefs.getString("quote_category", QuoteCategory.ALL.name) ?: QuoteCategory.ALL.name
        val category = try { QuoteCategory.valueOf(categoryStr) } catch (_: Exception) { QuoteCategory.ALL }

        return PartnerProfile(
            name = name,
            nickname = nickname,
            lastPeriodStartDateMillis = lastPeriod,
            cycleLengthDays = cycleLength,
            periodDurationDays = periodDuration,
            notificationHour = notifHour,
            notificationMinute = notifMinute,
            isNotificationEnabled = notifEnabled,
            isGentleModeAuto = gentleAuto,
            isManualBadMoodToday = badMoodToday,
            currentQuoteIndex = quoteIndex,
            includePmsInNotification = includePms,
            selectedCategory = category
        )
    }

    private fun saveProfile(prof: PartnerProfile) {
        prefs.edit()
            .putString("partner_name", prof.name)
            .putString("partner_nickname", prof.nickname)
            .putLong("last_period_millis", prof.lastPeriodStartDateMillis)
            .putInt("cycle_length", prof.cycleLengthDays)
            .putInt("period_duration", prof.periodDurationDays)
            .putInt("notif_hour", prof.notificationHour)
            .putInt("notif_minute", prof.notificationMinute)
            .putBoolean("notif_enabled", prof.isNotificationEnabled)
            .putBoolean("gentle_auto", prof.isGentleModeAuto)
            .putBoolean("bad_mood_today", prof.isManualBadMoodToday)
            .putInt("quote_index", prof.currentQuoteIndex)
            .putBoolean("include_pms_notif", prof.includePmsInNotification)
            .putString("quote_category", prof.selectedCategory.name)
            .apply()
    }

    private fun loadQuotes(): List<RomanticQuote> {
        val jsonString = prefs.getString("saved_quotes_json", null)
        if (jsonString.isNullOrEmpty()) {
            return QuoteProvider.initialQuotes
        }
        return try {
            val jsonArray = JSONArray(jsonString)
            val list = mutableListOf<RomanticQuote>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val catName = obj.optString("category", QuoteCategory.ROMANTIC.name)
                val cat = try { QuoteCategory.valueOf(catName) } catch (_: Exception) { QuoteCategory.ROMANTIC }
                list.add(
                    RomanticQuote(
                        id = obj.getString("id"),
                        text = obj.getString("text"),
                        sourceOrAuthor = obj.getString("sourceOrAuthor"),
                        category = cat,
                        orderIndex = obj.optInt("orderIndex", i + 1),
                        isFavorite = obj.optBoolean("isFavorite", false),
                        isCustom = obj.optBoolean("isCustom", false),
                        isGentleModeOnly = obj.optBoolean("isGentleModeOnly", false)
                    )
                )
            }
            if (list.isEmpty()) QuoteProvider.initialQuotes else list
        } catch (_: Exception) {
            QuoteProvider.initialQuotes
        }
    }

    private fun saveQuotes(quotes: List<RomanticQuote>) {
        try {
            val jsonArray = JSONArray()
            for (q in quotes) {
                val obj = JSONObject()
                obj.put("id", q.id)
                obj.put("text", q.text)
                obj.put("sourceOrAuthor", q.sourceOrAuthor)
                obj.put("category", q.category.name)
                obj.put("orderIndex", q.orderIndex)
                obj.put("isFavorite", q.isFavorite)
                obj.put("isCustom", q.isCustom)
                obj.put("isGentleModeOnly", q.isGentleModeOnly)
                jsonArray.put(obj)
            }
            prefs.edit().putString("saved_quotes_json", jsonArray.toString()).apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun updateCalorieProfile(profile: com.example.data.model.CalorieProfile) {
        _calorieProfile.value = profile
        saveCalorieProfile(profile)
    }

    fun addFoodLogItem(item: com.example.data.model.FoodLogItem) {
        val currentList = _dailyFoodLogs.value.toMutableList()
        currentList.add(item)
        _dailyFoodLogs.value = currentList
        saveDailyFoodLogs(currentList)
    }

    fun deleteFoodLogItem(itemId: String) {
        val currentList = _dailyFoodLogs.value.filter { it.id != itemId }
        _dailyFoodLogs.value = currentList
        saveDailyFoodLogs(currentList)
    }

    fun clearDailyFoodLogs() {
        _dailyFoodLogs.value = emptyList()
        saveDailyFoodLogs(emptyList())
    }

    private fun loadCalorieProfile(): com.example.data.model.CalorieProfile {
        val age = prefs.getInt("cal_age", 24)
        val weight = prefs.getFloat("cal_weight", 58f)
        val height = prefs.getFloat("cal_height", 165f)
        val genderStr = prefs.getString("cal_gender", com.example.data.model.UserGender.FEMALE.name)
        val activityStr = prefs.getString("cal_activity", com.example.data.model.ActivityLevel.LIGHT.name)
        val goalStr = prefs.getString("cal_goal", com.example.data.model.WeightGoal.MAINTAIN.name)

        val gender = try { com.example.data.model.UserGender.valueOf(genderStr ?: "") } catch (_: Exception) { com.example.data.model.UserGender.FEMALE }
        val activity = try { com.example.data.model.ActivityLevel.valueOf(activityStr ?: "") } catch (_: Exception) { com.example.data.model.ActivityLevel.LIGHT }
        val goal = try { com.example.data.model.WeightGoal.valueOf(goalStr ?: "") } catch (_: Exception) { com.example.data.model.WeightGoal.MAINTAIN }

        return com.example.data.model.CalorieProfile(
            age = age,
            weightKg = weight,
            heightCm = height,
            gender = gender,
            activityLevel = activity,
            goal = goal
        )
    }

    private fun saveCalorieProfile(profile: com.example.data.model.CalorieProfile) {
        prefs.edit()
            .putInt("cal_age", profile.age)
            .putFloat("cal_weight", profile.weightKg)
            .putFloat("cal_height", profile.heightCm)
            .putString("cal_gender", profile.gender.name)
            .putString("cal_activity", profile.activityLevel.name)
            .putString("cal_goal", profile.goal.name)
            .apply()
    }

    private fun loadDailyFoodLogs(): List<com.example.data.model.FoodLogItem> {
        val jsonString = prefs.getString("saved_daily_food_logs_json", null)
        if (jsonString.isNullOrEmpty()) {
            return getDefaultInitialFoodLogs()
        }
        return try {
            val jsonArray = JSONArray(jsonString)
            val list = mutableListOf<com.example.data.model.FoodLogItem>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val mealTypeStr = obj.optString("mealType", com.example.data.model.MealType.BREAKFAST.name)
                val mealType = try { com.example.data.model.MealType.valueOf(mealTypeStr) } catch (_: Exception) { com.example.data.model.MealType.BREAKFAST }
                list.add(
                    com.example.data.model.FoodLogItem(
                        id = obj.getString("id"),
                        name = obj.getString("name"),
                        calories = obj.getInt("calories"),
                        mealType = mealType,
                        proteinGrams = obj.optInt("proteinGrams", 0),
                        carbsGrams = obj.optInt("carbsGrams", 0),
                        fatGrams = obj.optInt("fatGrams", 0),
                        timestamp = obj.optLong("timestamp", System.currentTimeMillis()),
                        isAiDetected = obj.optBoolean("isAiDetected", false)
                    )
                )
            }
            list
        } catch (_: Exception) {
            getDefaultInitialFoodLogs()
        }
    }

    private fun getDefaultInitialFoodLogs(): List<com.example.data.model.FoodLogItem> {
        return listOf(
            com.example.data.model.FoodLogItem(
                name = "اوتمیل جو دوسر با شیر و دارچین 🥣",
                calories = 280,
                mealType = com.example.data.model.MealType.BREAKFAST,
                proteinGrams = 11,
                carbsGrams = 42,
                fatGrams = 5
            ),
            com.example.data.model.FoodLogItem(
                name = "سینه مرغ گریل با کینوا و سبزیجات 🥗",
                calories = 420,
                mealType = com.example.data.model.MealType.LUNCH,
                proteinGrams = 38,
                carbsGrams = 35,
                fatGrams = 9,
                isAiDetected = true
            ),
            com.example.data.model.FoodLogItem(
                name = "دمنوش بابونه با شکلات تلخ ۸۵٪ ☕🍫",
                calories = 95,
                mealType = com.example.data.model.MealType.SNACK,
                proteinGrams = 2,
                carbsGrams = 8,
                fatGrams = 6
            )
        )
    }

    private fun saveDailyFoodLogs(items: List<com.example.data.model.FoodLogItem>) {
        try {
            val jsonArray = JSONArray()
            for (item in items) {
                val obj = JSONObject()
                obj.put("id", item.id)
                obj.put("name", item.name)
                obj.put("calories", item.calories)
                obj.put("mealType", item.mealType.name)
                obj.put("proteinGrams", item.proteinGrams)
                obj.put("carbsGrams", item.carbsGrams)
                obj.put("fatGrams", item.fatGrams)
                obj.put("timestamp", item.timestamp)
                obj.put("isAiDetected", item.isAiDetected)
                jsonArray.put(obj)
            }
            prefs.edit().putString("saved_daily_food_logs_json", jsonArray.toString()).apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadMood(): DailyMood {
        val moodStr = prefs.getString("today_mood", DailyMood.HAPPY.name) ?: DailyMood.HAPPY.name
        return try {
            DailyMood.valueOf(moodStr)
        } catch (_: Exception) {
            DailyMood.HAPPY
        }
    }
}
