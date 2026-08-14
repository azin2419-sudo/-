package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CyclePhase
import com.example.data.model.CyclePhaseInfo
import com.example.ui.components.CycleStatusCard
import com.example.ui.theme.FollicularAccent
import com.example.ui.theme.GentleComfortAccent
import com.example.ui.theme.OvulationAccent
import com.example.ui.theme.PeriodAccent
import com.example.ui.theme.PmsAccent

@Composable
fun CycleTrackerScreen(
    cycleInfo: CyclePhaseInfo,
    onOpenCycleDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Main Status Card
        CycleStatusCard(
            cycleInfo = cycleInfo,
            onEditCycleClick = onOpenCycleDialog
        )

        // PMS & Care Guide Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.SleekBorderLight)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = PmsAccent.copy(alpha = 0.15f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolunteerActivism,
                            contentDescription = null,
                            tint = PmsAccent,
                            modifier = Modifier
                                .padding(8.dp)
                                .size(22.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "راهنمای همراهی عاشقانه در فاز P.M.S",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "نکاتی برای آرامش و حال خوب او در روزهای حساس",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                PmsCareItem(
                    icon = Icons.Default.Psychology,
                    title = "۱. صبوری و درک عاطفی بدون سرزنش",
                    desc = "در فاز PMS نوسانات هورمونی باعث حساسیت و دلتنگی زودهنگام می‌شود. شنونده صبور باشید و بحث‌های استرس‌زا را به بعد موکول کنید.",
                    accentColor = PmsAccent
                )

                PmsCareItem(
                    icon = Icons.Default.LocalCafe,
                    title = "۲. دمنوش‌های گرم و شکلات تلخ",
                    desc = "دمنوش بابونه، گل‌گاوزبان، چای ماسالا و زنجبیل به همراه مقداری شکلات تلخ و خرما، گرفتگی‌ها را به شدت کاهش می‌دهد.",
                    accentColor = GentleComfortAccent
                )

                PmsCareItem(
                    icon = Icons.Default.Spa,
                    title = "۳. کیسه آب گرم و ماساژ آرام",
                    desc = "گرما دادن به ناحیه کمر و شکم معجزه می‌کند. همچنین پیام‌های عاشقانه کوتاه و بدون توقع پاسخ به او احساس امنیت می‌دهد.",
                    accentColor = PeriodAccent
                )
            }
        }

        // All 4 Phases Visual Roadmap
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.SleekBorderLight)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "نقشه ۴ فاز چرخه ماهانه بانوان",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                PhaseRoadmapItem(
                    phaseName = "۱. فاز پریود (Menstruation)",
                    daysRange = "روز ۱ تا ۵",
                    emoji = "🌸",
                    accentColor = PeriodAccent,
                    isCurrent = cycleInfo.phase == CyclePhase.MENSTRUATION,
                    desc = "استراحت و بازیابی بدن، دمنوش گرم، کاهش استرس."
                )

                PhaseRoadmapItem(
                    phaseName = "۲. فاز فولیکولار (Follicular)",
                    daysRange = "روز ۶ تا ۱۱",
                    emoji = "🌿",
                    accentColor = FollicularAccent,
                    isCurrent = cycleInfo.phase == CyclePhase.FOLLICULAR,
                    desc = "بالا رفتن استروژن، انرژی و شادابی، برنامه‌های دونفره جذاب."
                )

                PhaseRoadmapItem(
                    phaseName = "۳. فاز تخمک‌گذاری (Ovulation)",
                    daysRange = "روز ۱۲ تا ۱۶",
                    emoji = "✨",
                    accentColor = OvulationAccent,
                    isCurrent = cycleInfo.phase == CyclePhase.OVULATION,
                    desc = "اوج درخشش و اعتماد به نفس، احساسات رمانتیک پرشور."
                )

                PhaseRoadmapItem(
                    phaseName = "۴. فاز لوتئال / P.M.S",
                    daysRange = "روز ۱۷ تا ۲۸",
                    emoji = "🌙",
                    accentColor = PmsAccent,
                    isCurrent = cycleInfo.phase == CyclePhase.PMS_LUTEAL,
                    desc = "کاهش انرژی، نیاز به نوازش و عدم ایجاد کوچکترین تنش و فشار ذهنی."
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun PmsCareItem(
    icon: ImageVector,
    title: String,
    desc: String,
    accentColor: Color
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = accentColor.copy(alpha = 0.08f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(20.dp)
            )
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
private fun PhaseRoadmapItem(
    phaseName: String,
    daysRange: String,
    emoji: String,
    accentColor: Color,
    isCurrent: Boolean,
    desc: String
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = if (isCurrent) accentColor.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
        border = if (isCurrent) androidx.compose.foundation.BorderStroke(1.5.dp, accentColor) else null,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(text = emoji, fontSize = 24.sp)
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = phaseName,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = if (isCurrent) accentColor else MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = daysRange,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 16.sp
                )
            }
            if (isCurrent) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = accentColor
                ) {
                    Text(
                        text = "اکنون 👈",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}
