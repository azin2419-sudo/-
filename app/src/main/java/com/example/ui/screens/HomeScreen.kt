package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Spa
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.CycleStatusCard
import com.example.ui.components.GentleModeCard
import com.example.ui.components.TodayQuoteCard
import com.example.ui.theme.SleekBackground
import com.example.ui.theme.SleekBorderLight
import com.example.ui.theme.SleekPinkLight
import com.example.ui.theme.SleekPinkPrimary
import com.example.ui.theme.SleekTextMuted
import com.example.ui.theme.SleekTextPrimary
import com.example.ui.theme.SleekTextSecondary
import com.example.ui.viewmodel.LoveCareUiState
import com.example.ui.viewmodel.LoveCareViewModel

@Composable
fun HomeScreen(
    state: LoveCareUiState,
    viewModel: LoveCareViewModel,
    onOpenCycleDialog: () -> Unit,
    onNavigateToCalorie: () -> Unit = {},
    onNavigateToSurpriseEgg: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SleekBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Sleek App Header Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "خوش آمدی به یادآور عشق 🌸",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Medium,
                    color = SleekTextMuted
                )
                Text(
                    text = "برای «${state.profile.name}»",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = SleekTextPrimary
                )
            }

            // Decorative Flower Avatar
            Surface(
                shape = CircleShape,
                color = SleekPinkLight,
                border = BorderStroke(2.dp, Color.White),
                shadowElevation = 2.dp,
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = "💖", fontSize = 22.sp)
                }
            }
        }

        // Hero Image Banner with 32dp corners
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("hero_header_card"),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            border = BorderStroke(1.dp, SleekBorderLight)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hero_care_banner),
                    contentDescription = "بنر عاشقانه",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Soft dark gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.2f),
                                    Color.Black.copy(alpha = 0.65f)
                                )
                            )
                        )
                )

                // Content inside banner
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "مهر و مراقبت روزانه",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                        Text(
                            text = "ارسال با محبت و بدون مزاحمت",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = SleekPinkPrimary
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "P.M.S & Care",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        // Voice Welcome & Quick Audio Bar
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("home_voice_welcome_card"),
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = SleekPinkLight.copy(alpha = 0.6f)),
            border = BorderStroke(1.dp, SleekPinkPrimary.copy(alpha = 0.4f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = SleekPinkPrimary,
                        modifier = Modifier.size(38.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.NotificationsActive,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Column {
                        Text(
                            text = "خوش‌آمدگویی صوتی انگلیسی 🔊",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = SleekTextPrimary
                        )
                        Text(
                            text = "“Hello my beautiful Fereshteh, welcome to your own application! ✨”",
                            style = MaterialTheme.typography.bodySmall,
                            color = SleekPinkPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = SleekPinkPrimary,
                    modifier = Modifier.testTag("replay_voice_welcome_home")
                ) {
                    androidx.compose.material3.TextButton(
                        onClick = { viewModel.playWelcomeGreetingVoice() },
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "پخش صدا",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // 1. Surprise Egg Date Teaser Card (پوریا & فرشته)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("home_surprise_egg_card"),
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.5.dp, Color(0xFFFDE047)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFFEF9C3),
                        modifier = Modifier.size(46.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = "🥚", fontSize = 24.sp)
                        }
                    }

                    Column {
                        Text(
                            text = "تخم‌مرغ شانسی قرار بعدی 🎁",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = SleekTextPrimary
                        )
                        Text(
                            text = "قرار بعدی کی و کجا؟ بین ${state.profile.senderName} و ${state.profile.name}",
                            style = MaterialTheme.typography.bodySmall,
                            color = SleekTextSecondary
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = SleekPinkPrimary
                ) {
                    androidx.compose.material3.TextButton(
                        onClick = onNavigateToSurpriseEgg,
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "شکستن 🔨",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // 2. Cycle & PMS Status Card (Prominently shows PMS phase, days left / passed)
        CycleStatusCard(
            cycleInfo = state.cycleInfo,
            onEditCycleClick = onOpenCycleDialog
        )

        // 3. Gentle & Bad Mood Protector (Non-intrusive care when feeling unwell)
        GentleModeCard(
            currentMood = state.currentMood,
            isManualBadMood = state.profile.isManualBadMoodToday,
            isAutoGentleEnabled = state.profile.isGentleModeAuto,
            isGentleCurrentlyActive = state.isGentleModeActive,
            onMoodSelected = { viewModel.setMood(it) },
            onToggleManualBadMood = { viewModel.toggleManualBadMood(it) },
            onToggleAutoGentle = { viewModel.toggleGentleModeAuto(it) }
        )

        // 4. Calorie & AI Nutrition Summary Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("home_calorie_card"),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, SleekBorderLight),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            val targetCal = state.calorieProfile.calculateTargetCalories()
            val consumedCal = state.dailyFoodLogs.sumOf { it.calories }
            val remainingCal = (targetCal - consumedCal).coerceAtLeast(0)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = SleekPinkLight,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(text = "🔥", fontSize = 18.sp)
                            }
                        }
                        Column {
                            Text(
                                text = "کالری من و هوش مصنوعی 🥗",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = SleekTextPrimary
                            )
                            Text(
                                text = "هدف: $targetCal | مصرفی: $consumedCal کیلوکالری",
                                style = MaterialTheme.typography.bodySmall,
                                color = SleekTextSecondary
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = SleekPinkLight
                    ) {
                        Text(
                            text = "باقیمانده: $remainingCal",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = SleekPinkPrimary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    androidx.compose.material3.OutlinedButton(
                        onClick = onNavigateToCalorie,
                        modifier = Modifier.weight(1f).height(42.dp),
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.dp, SleekPinkPrimary)
                    ) {
                        Text(
                            text = "محاسبه‌گر کالری",
                            style = MaterialTheme.typography.labelMedium,
                            color = SleekPinkPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    androidx.compose.material3.Button(
                        onClick = onNavigateToCalorie,
                        modifier = Modifier.weight(1f).height(42.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = SleekPinkPrimary
                        )
                    ) {
                        Text(
                            text = "اسکن عکس غذا ✨",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
