package com.example.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.data.repository.LoveCareRepository

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val repo = LoveCareRepository(context)
        val profile = repo.profile.value

        if (!profile.isNotificationEnabled && intent?.action == AlarmScheduler.ACTION_DAILY_NOTIFICATION) {
            return
        }

        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            // Re-schedule alarm after device reboot
            if (profile.isNotificationEnabled) {
                AlarmScheduler.scheduleDailyAlarm(
                    context,
                    profile.notificationHour,
                    profile.notificationMinute
                )
            }
            return
        }

        val cycleInfo = repo.getCyclePhaseInfo()
        val currentMood = repo.currentMood.value
        val isGentleMode = profile.isManualBadMoodToday ||
                (profile.isGentleModeAuto && (cycleInfo.isPmsActive || currentMood.isSensitiveOrBad || cycleInfo.phase == com.example.data.model.CyclePhase.MENSTRUATION))

        val todayQuote = repo.getCurrentQuote()

        // Display Notification
        NotificationHelper.showDailyNotification(
            context = context,
            profile = profile,
            quote = todayQuote,
            cycleInfo = cycleInfo,
            isGentleModeActive = isGentleMode
        )

        // Advance to next quote in order for the next day
        repo.advanceToNextQuote()

        // Reschedule alarm for next day
        AlarmScheduler.scheduleDailyAlarm(
            context,
            profile.notificationHour,
            profile.notificationMinute
        )
    }
}
