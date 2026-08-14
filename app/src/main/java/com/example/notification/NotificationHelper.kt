package com.example.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.MainActivity
import com.example.R
import com.example.data.model.CyclePhaseInfo
import com.example.data.model.PartnerProfile
import com.example.data.model.RomanticQuote

object NotificationHelper {

    const val CHANNEL_ROMANTIC_ID = "love_care_romantic_channel"
    const val CHANNEL_GENTLE_ID = "love_care_gentle_channel"
    const val NOTIFICATION_ID = 1001

    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // 1. Regular Romantic Channel (With sound and gentle vibration)
            val romanticChannel = NotificationChannel(
                CHANNEL_ROMANTIC_ID,
                context.getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = context.getString(R.string.notification_channel_desc)
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 250, 150, 250)
            }

            // 2. Gentle Comfort Channel (Low importance / Gentle sound, non-intrusive for PMS & bad days)
            val gentleChannel = NotificationChannel(
                CHANNEL_GENTLE_ID,
                context.getString(R.string.gentle_notification_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = context.getString(R.string.gentle_notification_channel_desc)
                enableVibration(false)
            }

            notificationManager.createNotificationChannel(romanticChannel)
            notificationManager.createNotificationChannel(gentleChannel)
        }
    }

    fun showDailyNotification(
        context: Context,
        profile: PartnerProfile,
        quote: RomanticQuote,
        cycleInfo: CyclePhaseInfo,
        isGentleModeActive: Boolean
    ) {
        createNotificationChannels(context)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = if (isGentleModeActive) CHANNEL_GENTLE_ID else CHANNEL_ROMANTIC_ID

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Build title with partner name and cycle/PMS indicator
        val pmsStatusShort = if (profile.includePmsInNotification) {
            if (cycleInfo.isPmsActive) {
                " [P.M.S: ${cycleInfo.daysUntilNextPeriod} روز مانده]"
            } else if (cycleInfo.phase == com.example.data.model.CyclePhase.MENSTRUATION) {
                " [روز ${cycleInfo.daysSincePeriodStart} پریود 🌸]"
            } else {
                " [${cycleInfo.daysUntilNextPeriod} روز تا دوره بعدی ✨]"
            }
        } else {
            ""
        }

        // Dynamic romantic notification titles
        val sweetSalutations = listOf(
            "دوستت دارم ${profile.name} من ❤️",
            "چقدر خوشگل شدی امروز ${profile.name} 🌸✨",
            "پیام عشق و دلدادگی برای ${profile.name} 💖",
            "لبخندت قشنگ‌ترین اتفاق امروزه 👑",
            "عاشقتم تمام دنیای من 🌷"
        )
        val randomSalutation = sweetSalutations[((quote.orderIndex) % sweetSalutations.size)]

        val title = if (isGentleModeActive) {
            "🌸 به یادتم و دوستت دارم ${profile.name}$pmsStatusShort"
        } else {
            "$randomSalutation$pmsStatusShort"
        }

        val bigText = buildString {
            append(quote.text)
            if (quote.sourceOrAuthor.isNotBlank()) {
                append("\n\n— ${quote.sourceOrAuthor}")
            }
            if (profile.includePmsInNotification) {
                append("\n\n🗓️ وضعیت چرخه: ${cycleInfo.statusBadgeFa}")
            }
            if (isGentleModeActive) {
                append("\n🍵 یادآوری: مراقب سلامتی و استراحتت باش.")
            }
        }

        val largeIcon = try {
            BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_fg)
        } catch (_: Exception) {
            null
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_menu_myplaces)
            .apply {
                if (largeIcon != null) {
                    setLargeIcon(largeIcon)
                }
            }
            .setContentTitle(title)
            .setContentText(quote.text)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(bigText)
                    .setSummaryText(if (cycleInfo.isPmsActive) "وضعیت: P.M.S 🌙" else "یادآور مهر و عاشقی 💖")
            )
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(
                if (isGentleModeActive) NotificationCompat.PRIORITY_DEFAULT
                else NotificationCompat.PRIORITY_HIGH
            )
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }
}
