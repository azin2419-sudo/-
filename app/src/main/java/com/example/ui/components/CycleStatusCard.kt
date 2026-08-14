package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CyclePhase
import com.example.data.model.CyclePhaseInfo
import com.example.ui.theme.FollicularAccent
import com.example.ui.theme.OvulationAccent
import com.example.ui.theme.PeriodAccent
import com.example.ui.theme.PmsAccent
import com.example.ui.theme.SleekBorderLight
import com.example.ui.theme.SleekPinkLight
import com.example.ui.theme.SleekPinkPrimary
import com.example.ui.theme.SleekTextMuted
import com.example.ui.theme.SleekTextPrimary
import com.example.ui.theme.SleekTextSecondary

@Composable
fun CycleStatusCard(
    cycleInfo: CyclePhaseInfo,
    onEditCycleClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val phaseColor = when (cycleInfo.phase) {
        CyclePhase.MENSTRUATION -> PeriodAccent
        CyclePhase.FOLLICULAR -> FollicularAccent
        CyclePhase.OVULATION -> OvulationAccent
        CyclePhase.PMS_LUTEAL -> PmsAccent
    }

    val animatedProgress by animateFloatAsState(
        targetValue = cycleInfo.progressPercent,
        animationSpec = tween(durationMillis = 900),
        label = "cycle_progress"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("cycle_status_card"),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, SleekBorderLight)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Decorative subtle background corner accent
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(bottomStart = 80.dp))
                    .background(SleekPinkLight.copy(alpha = 0.5f))
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp)
            ) {
                // Header Row: Icon Badge & Title + Phase Status
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
                            color = SleekPinkLight
                        ) {
                            Text(
                                text = "🗓️",
                                fontSize = 18.sp,
                                modifier = Modifier.padding(10.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "وضعیت چرخه P.M.S",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = SleekTextPrimary
                            )
                            Text(
                                text = if (cycleInfo.isPmsActive) "مراقبت ویژه فاز P.M.S" else cycleInfo.phase.titleFa,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Medium,
                                color = phaseColor
                            )
                        }
                    }

                    // Phase status badge
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = phaseColor.copy(alpha = 0.12f)
                    ) {
                        Text(
                            text = "${cycleInfo.phase.iconEmoji} ${cycleInfo.phase.titleFa}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = phaseColor,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Big Countdown Number Display and Action Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = if (cycleInfo.phase == CyclePhase.MENSTRUATION) "${cycleInfo.daysSincePeriodStart}" else "${cycleInfo.daysUntilNextPeriod}",
                                style = MaterialTheme.typography.displaySmall.copy(fontSize = 38.sp),
                                fontWeight = FontWeight.Black,
                                color = SleekPinkPrimary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "روز",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium,
                                color = SleekTextMuted,
                                modifier = Modifier.padding(bottom = 6.dp)
                            )
                        }

                        Text(
                            text = if (cycleInfo.phase == CyclePhase.MENSTRUATION) "سپری شده از دوره پریود 🌸" else "مانده تا شروع دوره بعدی",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = SleekTextSecondary
                        )
                    }

                    Button(
                        onClick = onEditCycleClick,
                        modifier = Modifier.testTag("edit_cycle_button"),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SleekPinkPrimary
                        ),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = "تنظیم تقویم",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Progress Bar & Day Tracker
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = SleekPinkLight.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "روز ${cycleInfo.currentDayInCycle} از کل چرخه (${cycleInfo.totalCycleLength} روز)",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = SleekTextPrimary
                            )

                            Text(
                                text = "${(animatedProgress * 100).toInt()}%",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = SleekPinkPrimary
                            )
                        }

                        // Linear Progress track
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.White)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(animatedProgress)
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(phaseColor)
                            )
                        }

                        Text(
                            text = if (cycleInfo.isPmsActive) {
                                "💡 در فاز P.M.S: تغییرات هورمونی باعث حساسیت است؛ صبوری و ارسال محبت بیشترین آرامش را به همراه دارد."
                            } else {
                                cycleInfo.phase.careTip
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = SleekTextSecondary,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }
    }
}
