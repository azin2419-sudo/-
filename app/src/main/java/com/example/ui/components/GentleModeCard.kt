package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DailyMood
import com.example.ui.theme.SleekBorderLight
import com.example.ui.theme.SleekIndigoSoft
import com.example.ui.theme.SleekIndigoText
import com.example.ui.theme.SleekPinkLight
import com.example.ui.theme.SleekPinkPrimary
import com.example.ui.theme.SleekTextPrimary
import com.example.ui.theme.SleekTextSecondary

@Composable
fun GentleModeCard(
    currentMood: DailyMood,
    isManualBadMood: Boolean,
    isAutoGentleEnabled: Boolean,
    isGentleCurrentlyActive: Boolean,
    onMoodSelected: (DailyMood) -> Unit,
    onToggleManualBadMood: (Boolean) -> Unit,
    onToggleAutoGentle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("gentle_mode_card"),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, SleekBorderLight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = SleekIndigoSoft
                    ) {
                        Icon(
                            imageVector = Icons.Default.SelfImprovement,
                            contentDescription = "آرامش",
                            tint = SleekIndigoText,
                            modifier = Modifier
                                .padding(10.dp)
                                .size(20.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "حالت مدارا و عدم مزاحمت",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = SleekTextPrimary
                        )
                        Text(
                            text = "رعایت حال در روزهای حساس و PMS",
                            style = MaterialTheme.typography.labelSmall,
                            color = SleekTextSecondary
                        )
                    }
                }

                // Active status pill
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = if (isGentleCurrentlyActive) SleekIndigoSoft else SleekPinkLight.copy(alpha = 0.5f)
                ) {
                    Text(
                        text = if (isGentleCurrentlyActive) "فعال 🌿" else "عادی 💖",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (isGentleCurrentlyActive) SleekIndigoText else SleekPinkPrimary,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Mood Selector
            Text(
                text = "حال و هوای امروز او چگونه است؟",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = SleekTextPrimary
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DailyMood.entries.forEach { mood ->
                    val isSelected = currentMood == mood
                    FilterChip(
                        selected = isSelected,
                        onClick = { onMoodSelected(mood) },
                        shape = RoundedCornerShape(16.dp),
                        label = {
                            Text(
                                text = "${mood.emoji} ${mood.titleFa}",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        leadingIcon = if (isSelected) {
                            {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "انتخاب شده",
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        } else null,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = if (mood.isSensitiveOrBad) SleekIndigoSoft else SleekPinkLight,
                            selectedLabelColor = if (mood.isSensitiveOrBad) SleekIndigoText else SleekPinkPrimary,
                            containerColor = Color.White
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = if (isSelected) (if (mood.isSensitiveOrBad) SleekIndigoText else SleekPinkPrimary) else SleekBorderLight,
                            borderWidth = 1.dp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Advice Banner for current mood
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = if (currentMood.isSensitiveOrBad) SleekIndigoSoft.copy(alpha = 0.5f) else SleekPinkLight.copy(alpha = 0.5f),
                border = BorderStroke(1.dp, if (currentMood.isSensitiveOrBad) SleekIndigoText.copy(alpha = 0.2f) else SleekPinkPrimary.copy(alpha = 0.15f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(text = currentMood.emoji, fontSize = 22.sp)
                    Text(
                        text = currentMood.gentleCareAdvice,
                        style = MaterialTheme.typography.bodySmall,
                        color = SleekTextPrimary,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Toggle 1: Manual "امروز حالش خوب نیست"
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = SleekIndigoSoft.copy(alpha = 0.35f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "امروز حالش مساعد نیست (مراقبت ویژه)",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = SleekTextPrimary
                        )
                        Text(
                            text = "اعلان‌ها با پیام‌های تسکین‌بخش و لحن پرمهر و بدون ایجاد زحمت ارسال خواهند شد.",
                            style = MaterialTheme.typography.bodySmall,
                            color = SleekTextSecondary,
                            lineHeight = 16.sp
                        )
                    }

                    Switch(
                        checked = isManualBadMood,
                        onCheckedChange = onToggleManualBadMood,
                        modifier = Modifier.testTag("manual_bad_mood_switch"),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = SleekIndigoText
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Toggle 2: Auto gentle during PMS
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "تشخیص هوشمند و خودکار در دوران PMS",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = SleekTextPrimary
                    )
                    Text(
                        text = "در روزهای حساس منتهی به پریود، لحن اعلان‌ها خودکار صبورانه می‌شود.",
                        style = MaterialTheme.typography.bodySmall,
                        color = SleekTextSecondary
                    )
                }

                Switch(
                    checked = isAutoGentleEnabled,
                    onCheckedChange = onToggleAutoGentle,
                    modifier = Modifier.testTag("auto_gentle_pms_switch"),
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = SleekPinkPrimary
                    )
                )
            }
        }
    }
}
