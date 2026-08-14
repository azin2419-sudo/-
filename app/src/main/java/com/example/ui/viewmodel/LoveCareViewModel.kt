package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.CyclePhaseInfo
import com.example.data.model.DailyMood
import com.example.data.model.PartnerProfile
import com.example.data.model.QuoteCategory
import com.example.data.model.RomanticQuote
import com.example.data.repository.LoveCareRepository
import com.example.notification.AlarmScheduler
import com.example.notification.NotificationHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class LoveCareUiState(
    val profile: PartnerProfile = PartnerProfile(),
    val cycleInfo: CyclePhaseInfo = CyclePhaseInfo.calculate(System.currentTimeMillis()),
    val todayQuote: RomanticQuote = com.example.data.quotes.QuoteProvider.initialQuotes.first(),
    val quotesList: List<RomanticQuote> = emptyList(),
    val chatMessages: List<com.example.data.model.ChatMessage> = emptyList(),
    val musicTracks: List<com.example.data.model.OnlineMusicTrack> = com.example.data.music.OnlineMusicProvider.curatedTracks,
    val musicPlayerState: com.example.util.MusicPlayerState = com.example.util.MusicPlayerState(),
    val calorieProfile: com.example.data.model.CalorieProfile = com.example.data.model.CalorieProfile(),
    val dailyFoodLogs: List<com.example.data.model.FoodLogItem> = emptyList(),
    val isAnalyzingFood: Boolean = false,
    val aiAnalysisResult: com.example.data.model.FoodAnalysisResult? = null,
    val foodAnalysisError: String? = null,
    val currentMood: DailyMood = DailyMood.HAPPY,
    val isGentleModeActive: Boolean = false,
    val messageSentNotificationToast: String? = null
)

class LoveCareViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = LoveCareRepository(application)
    val musicPlayerManager = com.example.util.OnlineMusicPlayerManager(application)
    val speechWelcomeManager = com.example.util.SpeechWelcomeManager(application)

    private val _toastMessage = MutableStateFlow<String?>(null)
    private val _isAnalyzingFood = MutableStateFlow(false)
    private val _aiAnalysisResult = MutableStateFlow<com.example.data.model.FoodAnalysisResult?>(null)
    private val _foodAnalysisError = MutableStateFlow<String?>(null)

    val uiState: StateFlow<LoveCareUiState> = combine(
        combine(
            repository.profile,
            repository.quotes,
            repository.currentMood,
            repository.calorieProfile,
            repository.dailyFoodLogs
        ) { profile, quotes, mood, calProfile, foodLogs ->
            Tuple5(profile, quotes, mood, calProfile, foodLogs)
        },
        combine(
            repository.chatMessages,
            musicPlayerManager.playerState,
            _isAnalyzingFood,
            _aiAnalysisResult,
            _foodAnalysisError
        ) { chats, playerState, isAnalyzing, aiResult, error ->
            Tuple5(chats, playerState, isAnalyzing, aiResult, error)
        },
        _toastMessage
    ) { group1, group2, toast ->
        val (profile, quotes, mood, calProfile, foodLogs) = group1
        val (chats, playerState, isAnalyzing, aiResult, error) = group2

        val cycleInfo = CyclePhaseInfo.calculate(
            lastPeriodStartDateMillis = profile.lastPeriodStartDateMillis,
            cycleLength = profile.cycleLengthDays,
            periodDuration = profile.periodDurationDays
        )

        val isGentle = profile.isManualBadMoodToday ||
                (profile.isGentleModeAuto && (cycleInfo.isPmsActive || mood.isSensitiveOrBad || cycleInfo.phase == com.example.data.model.CyclePhase.MENSTRUATION))

        val currentQuote = repository.getCurrentQuote()

        LoveCareUiState(
            profile = profile,
            cycleInfo = cycleInfo,
            todayQuote = currentQuote,
            quotesList = quotes,
            chatMessages = chats,
            musicTracks = com.example.data.music.OnlineMusicProvider.curatedTracks,
            musicPlayerState = playerState,
            calorieProfile = calProfile,
            dailyFoodLogs = foodLogs,
            isAnalyzingFood = isAnalyzing,
            aiAnalysisResult = aiResult,
            foodAnalysisError = error,
            currentMood = mood,
            isGentleModeActive = isGentle,
            messageSentNotificationToast = toast
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LoveCareUiState()
    )

    init {
        // Ensure channels are created and alarm is set
        NotificationHelper.createNotificationChannels(application)
        val profile = repository.profile.value
        if (profile.isNotificationEnabled) {
            AlarmScheduler.scheduleDailyAlarm(
                application,
                profile.notificationHour,
                profile.notificationMinute
            )
        }

        // Welcome greeting on app startup in English with high volume:
        // "Fereshteh, welcome to your own application!"
        speechWelcomeManager.speakWelcomeGreeting(profile.nickname.ifBlank { "Fereshteh" })
    }

    fun updatePartnerName(name: String, nickname: String) {
        val current = repository.profile.value
        repository.updateProfile(current.copy(name = name.trim(), nickname = nickname.trim()))
    }

    fun updateSenderName(senderName: String) {
        val current = repository.profile.value
        repository.updateProfile(current.copy(senderName = senderName.trim()))
    }

    fun saveCompleteProfile(partnerProfile: PartnerProfile, calorieProfile: com.example.data.model.CalorieProfile) {
        repository.updateProfile(partnerProfile.copy(isProfileCompleted = true))
        repository.updateCalorieProfile(calorieProfile)
        AlarmScheduler.scheduleDailyAlarm(
            getApplication(),
            partnerProfile.notificationHour,
            partnerProfile.notificationMinute
        )
        _toastMessage.value = "مشخصات کامل با موفقیت ذخیره شد ✨"
    }

    fun sendSurpriseDateToChat(proposalMessage: String) {
        repository.sendChatMessage(
            sender = com.example.data.model.MessageSender.ME,
            text = proposalMessage,
            isSpecialLoveNote = true
        )
        _toastMessage.value = "قرار عاشقانه به چت دونفره ارسال شد 💌"
    }

    fun updateCycleSettings(lastPeriodMillis: Long, cycleLength: Int, periodDuration: Int) {
        val current = repository.profile.value
        repository.updateProfile(
            current.copy(
                lastPeriodStartDateMillis = lastPeriodMillis,
                cycleLengthDays = cycleLength.coerceIn(20, 45),
                periodDurationDays = periodDuration.coerceIn(2, 10)
            )
        )
    }

    fun updateNotificationTime(hour: Int, minute: Int, enabled: Boolean) {
        val current = repository.profile.value
        repository.updateProfile(
            current.copy(
                notificationHour = hour,
                notificationMinute = minute,
                isNotificationEnabled = enabled
            )
        )
        val app = getApplication<Application>()
        if (enabled) {
            AlarmScheduler.scheduleDailyAlarm(app, hour, minute)
        } else {
            AlarmScheduler.cancelAlarm(app)
        }
    }

    fun setIncludePmsInNotification(include: Boolean) {
        val current = repository.profile.value
        repository.updateProfile(current.copy(includePmsInNotification = include))
    }

    fun toggleGentleModeAuto(enabled: Boolean) {
        val current = repository.profile.value
        repository.updateProfile(current.copy(isGentleModeAuto = enabled))
    }

    fun toggleManualBadMood(isBadMood: Boolean) {
        repository.setManualBadMood(isBadMood)
    }

    fun setMood(mood: DailyMood) {
        repository.updateMood(mood)
    }

    fun advanceQuote() {
        repository.advanceToNextQuote()
    }

    fun selectSpecificQuote(quote: RomanticQuote) {
        val all = repository.quotes.value
        val index = all.indexOfFirst { it.id == quote.id }
        if (index != -1) {
            repository.setSpecificQuoteIndex(index)
        }
    }

    fun setQuoteCategory(category: QuoteCategory) {
        val current = repository.profile.value
        repository.updateProfile(current.copy(selectedCategory = category))
    }

    fun addCustomQuote(text: String, author: String, category: QuoteCategory) {
        repository.addCustomQuote(text, author, category)
    }

    fun toggleFavoriteQuote(quoteId: String) {
        repository.toggleFavorite(quoteId)
    }

    fun deleteQuote(quoteId: String) {
        repository.deleteQuote(quoteId)
    }

    // Chat methods
    fun sendChatMessage(sender: com.example.data.model.MessageSender, text: String, isSpecialLoveNote: Boolean = false) {
        repository.sendChatMessage(sender, text, isSpecialLoveNote)
    }

    fun toggleHeartReactMessage(messageId: String) {
        repository.toggleHeartReactMessage(messageId)
    }

    fun deleteChatMessage(messageId: String) {
        repository.deleteChatMessage(messageId)
    }

    fun clearChatHistory() {
        repository.clearChatHistory()
    }

    // Music methods
    fun playTrack(track: com.example.data.model.OnlineMusicTrack) {
        musicPlayerManager.playTrack(track)
    }

    fun togglePlayPause(track: com.example.data.model.OnlineMusicTrack) {
        musicPlayerManager.togglePlayPause(track)
    }

    fun pauseMusic() {
        musicPlayerManager.pause()
    }

    fun resumeMusic() {
        musicPlayerManager.resume()
    }

    fun seekMusic(positionMs: Int) {
        musicPlayerManager.seekTo(positionMs)
    }

    // Speech methods
    fun playWelcomeGreetingVoice() {
        val name = uiState.value.profile.nickname.ifBlank { "Fereshteh" }
        speechWelcomeManager.speakWelcomeGreeting(name)
    }

    fun sendImmediateTestNotification() {
        val state = uiState.value
        NotificationHelper.showDailyNotification(
            context = getApplication(),
            profile = state.profile,
            quote = state.todayQuote,
            cycleInfo = state.cycleInfo,
            isGentleModeActive = state.isGentleModeActive
        )
        _toastMessage.value = "اعلان عاشقانه با موفقیت ارسال شد 💌"
    }

    fun clearToast() {
        _toastMessage.value = null
    }

    // Calorie Management
    fun updateCalorieProfile(profile: com.example.data.model.CalorieProfile) {
        repository.updateCalorieProfile(profile)
    }

    fun addFoodLogItem(
        name: String,
        calories: Int,
        mealType: com.example.data.model.MealType,
        proteinGrams: Int = 0,
        carbsGrams: Int = 0,
        fatGrams: Int = 0,
        isAiDetected: Boolean = false
    ) {
        val item = com.example.data.model.FoodLogItem(
            name = name.trim(),
            calories = calories,
            mealType = mealType,
            proteinGrams = proteinGrams,
            carbsGrams = carbsGrams,
            fatGrams = fatGrams,
            isAiDetected = isAiDetected
        )
        repository.addFoodLogItem(item)
        _toastMessage.value = "غذای «$name» به وعده ${mealType.titleFa} اضافه شد 🥗"
    }

    fun deleteFoodLogItem(itemId: String) {
        repository.deleteFoodLogItem(itemId)
    }

    fun clearDailyFoodLogs() {
        repository.clearDailyFoodLogs()
    }

    // AI Food Calorie Scanner
    fun analyzeFoodImage(bitmap: android.graphics.Bitmap, userNote: String = "") {
        viewModelScope.launch {
            _isAnalyzingFood.value = true
            _foodAnalysisError.value = null
            _aiAnalysisResult.value = null

            val result = com.example.util.GeminiFoodAnalyzer.analyzeFood(bitmap, userNote)
            _isAnalyzingFood.value = false

            result.onSuccess { analysis ->
                _aiAnalysisResult.value = analysis
            }.onFailure { ex ->
                _foodAnalysisError.value = "خطا در تحلیل تصویر: ${ex.message}"
            }
        }
    }

    fun clearAiAnalysisResult() {
        _aiAnalysisResult.value = null
        _foodAnalysisError.value = null
    }
}

private data class Tuple5<A, B, C, D, E>(
    val a: A,
    val b: B,
    val c: C,
    val d: D,
    val e: E
)

