package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Egg
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SurpriseDateDeck
import com.example.data.model.SurpriseDateProposal
import com.example.ui.theme.SleekBackground
import com.example.ui.theme.SleekBlueLight
import com.example.ui.theme.SleekBluePrimary
import com.example.ui.theme.SleekBorderLight
import com.example.ui.theme.SleekPinkLight
import com.example.ui.theme.SleekPinkPrimary
import com.example.ui.theme.SleekTextMuted
import com.example.ui.theme.SleekTextPrimary
import com.example.ui.theme.SleekTextSecondary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SurpriseEggScreen(
    partnerName: String = "فرشته",
    senderName: String = "پوریا",
    onSendDateToChat: (String) -> Unit = {},
    onOpenChat: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var crackStage by remember { mutableIntStateOf(0) } // 0: intact, 1: small crack, 2: big crack, 3: broken open
    var currentProposal by remember { mutableStateOf(SurpriseDateDeck.getRandomProposal()) }
    var openedHistory by remember { mutableStateOf(listOf<SurpriseDateProposal>()) }
    val coroutineScope = rememberCoroutineScope()

    // Floating idle animation for intact egg
    val infiniteTransition = rememberInfiniteTransition(label = "egg_float")
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "egg_float_anim"
    )

    val eggScale by animateFloatAsState(
        targetValue = when (crackStage) {
            0 -> 1f
            1 -> 1.06f
            2 -> 1.12f
            else -> 0.95f
        },
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 400f),
        label = "egg_scale_anim"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SleekBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp, vertical = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "تخم مرغ شانسی عاشقانه 🥚✨",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = SleekTextPrimary
                )
                Text(
                    text = "تعیین زمان و مکان قرار بعدی $senderName و $partnerName",
                    style = MaterialTheme.typography.bodySmall,
                    color = SleekTextSecondary
                )
            }

            Surface(
                shape = CircleShape,
                color = SleekPinkLight,
                border = BorderStroke(1.5.dp, SleekPinkPrimary),
                modifier = Modifier.size(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = "💖", fontSize = 20.sp)
                }
            }
        }

        // Egg Cracking Zone
        AnimatedContent(
            targetState = crackStage == 3,
            transitionSpec = {
                (fadeIn(animationSpec = tween(400)) + scaleIn(initialScale = 0.85f))
                    .togetherWith(fadeOut(animationSpec = tween(200)) + scaleOut(targetScale = 0.85f))
            },
            label = "egg_reveal_switch"
        ) { isBroken ->
            if (!isBroken) {
                // Interactive Egg View
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("surprise_egg_interactive_card"),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.5.dp, SleekBorderLight),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = SleekPinkLight
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = SleekPinkPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = when (crackStage) {
                                        0 -> "روی تخم‌مرغ بزن تا بشکنه! 🔨"
                                        1 -> "ترک اول برداشته شد... باز بزن! ⚡"
                                        2 -> "داره باز می‌شه... یه ضربه دیگه! 💥"
                                        else -> "باز شد! 🎉"
                                    },
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = SleekPinkPrimary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Custom Painted Bouncing Egg
                        Box(
                            modifier = Modifier
                                .size(190.dp, 240.dp)
                                .offset(y = floatOffset.dp)
                                .scale(eggScale)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    if (crackStage < 2) {
                                        crackStage++
                                    } else {
                                        crackStage = 3
                                        if (!openedHistory.contains(currentProposal)) {
                                            openedHistory = listOf(currentProposal) + openedHistory
                                        }
                                    }
                                }
                                .testTag("crackable_egg_view"),
                            contentAlignment = Alignment.Center
                        ) {
                            // Egg Shape Canvas
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val w = size.width
                                val h = size.height

                                // Draw Egg Shadow
                                drawOval(
                                    color = Color.Black.copy(alpha = 0.12f),
                                    topLeft = Offset(w * 0.15f, h * 0.88f),
                                    size = Size(w * 0.7f, h * 0.12f)
                                )

                                // Egg Gradient Fill
                                val eggBrush = Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xFFFFF1F2),
                                        Color(0xFFFFCCD5),
                                        Color(0xFFFB7185),
                                        Color(0xFFF43F5E)
                                    ),
                                    center = Offset(w * 0.4f, h * 0.35f),
                                    radius = w * 0.75f
                                )

                                val eggPath = Path().apply {
                                    moveTo(w * 0.5f, 0f)
                                    cubicTo(w * 0.88f, 0f, w, h * 0.45f, w, h * 0.72f)
                                    cubicTo(w, h * 0.95f, w * 0.78f, h * 0.98f, w * 0.5f, h * 0.98f)
                                    cubicTo(w * 0.22f, h * 0.98f, 0f, h * 0.95f, 0f, h * 0.72f)
                                    cubicTo(0f, h * 0.45f, w * 0.12f, 0f, w * 0.5f, 0f)
                                    close()
                                }

                                drawPath(path = eggPath, brush = eggBrush)

                                // Golden Ribbon Pattern Across Egg
                                drawCircle(
                                    color = Color.White.copy(alpha = 0.4f),
                                    radius = w * 0.12f,
                                    center = Offset(w * 0.35f, h * 0.3f)
                                )

                                // Crack Lines depending on stage
                                if (crackStage >= 1) {
                                    val crack1 = Path().apply {
                                        moveTo(w * 0.5f, h * 0.28f)
                                        lineTo(w * 0.42f, h * 0.42f)
                                        lineTo(w * 0.55f, h * 0.54f)
                                        lineTo(w * 0.48f, h * 0.68f)
                                    }
                                    drawPath(
                                        path = crack1,
                                        color = Color(0xFF881337),
                                        style = Stroke(width = 6f)
                                    )
                                    drawPath(
                                        path = crack1,
                                        color = Color(0xFFFFFBEB),
                                        style = Stroke(width = 2.5f)
                                    )
                                }

                                if (crackStage >= 2) {
                                    val crack2 = Path().apply {
                                        moveTo(w * 0.42f, h * 0.42f)
                                        lineTo(w * 0.25f, h * 0.48f)
                                        lineTo(w * 0.18f, h * 0.60f)
                                    }
                                    val crack3 = Path().apply {
                                        moveTo(w * 0.55f, h * 0.54f)
                                        lineTo(w * 0.75f, h * 0.50f)
                                        lineTo(w * 0.85f, h * 0.64f)
                                    }
                                    drawPath(path = crack2, color = Color(0xFF881337), style = Stroke(width = 5f))
                                    drawPath(path = crack3, color = Color(0xFF881337), style = Stroke(width = 5f))
                                }
                            }

                            // Center Heart Glow
                            Text(
                                text = if (crackStage == 0) "💖" else if (crackStage == 1) "⚡" else "💥",
                                fontSize = 38.sp,
                                modifier = Modifier.offset(y = (-10).dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "تخم‌مرغ شانسی قرار عاشقانه",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = SleekTextPrimary
                        )

                        Text(
                            text = "با ضربه زدن به تخم‌مرغ، قرعه‌کشی قرار دونفره انجام می‌شود!",
                            style = MaterialTheme.typography.bodySmall,
                            color = SleekTextSecondary,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    crackStage = 1
                                    delay(200)
                                    crackStage = 2
                                    delay(250)
                                    crackStage = 3
                                    if (!openedHistory.contains(currentProposal)) {
                                        openedHistory = listOf(currentProposal) + openedHistory
                                    }
                                }
                            },
                            shape = RoundedCornerShape(18.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SleekPinkPrimary),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("break_egg_now_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Egg,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "شکستن و دیدن قرار بعدی 🔨✨",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            } else {
                // Revealed Romantic Date Proposal Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("revealed_date_proposal_card"),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(2.dp, SleekPinkPrimary),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(22.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Golden Header Banner
                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = SleekPinkPrimary,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(text = "🎉", fontSize = 20.sp)
                                    Text(
                                        text = "قرار بعدی $senderName و $partnerName",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color.White
                                    )
                                }

                                Surface(
                                    shape = CircleShape,
                                    color = Color.White.copy(alpha = 0.25f)
                                ) {
                                    Text(
                                        text = currentProposal.moodEmoji,
                                        fontSize = 18.sp,
                                        modifier = Modifier.padding(6.dp)
                                    )
                                }
                            }
                        }

                        // 1. When (زمان قرار - کی)
                        DateDetailRow(
                            icon = Icons.Default.Schedule,
                            iconColor = Color(0xFF0284C7),
                            bgColor = SleekBlueLight,
                            title = "کی؟ (زمان قرار ⏰)",
                            content = currentProposal.whenTimeFa,
                            isHighlighted = true
                        )

                        // 2. Where (مکان قرار - کجا)
                        DateDetailRow(
                            icon = Icons.Default.LocationOn,
                            iconColor = Color(0xFFE11D48),
                            bgColor = SleekPinkLight,
                            title = "کجا؟ (مکان قرار 📍)",
                            content = currentProposal.whereLocationFa,
                            isHighlighted = true
                        )

                        // 3. Special Surprise by Pouria
                        DateDetailRow(
                            icon = Icons.Default.Spa,
                            iconColor = Color(0xFFD97706),
                            bgColor = Color(0xFFFEF3C7),
                            title = "سورپرایز ویژه $senderName برای $partnerName 🎁",
                            content = currentProposal.specialSurpriseFa
                        )

                        // 4. Suggested Outfit
                        DateDetailRow(
                            icon = Icons.Default.Style,
                            iconColor = Color(0xFF7C3AED),
                            bgColor = Color(0xFFEDE9FE),
                            title = "پیشنهاد استایل و تیپ 👗👔",
                            content = currentProposal.outfitTipFa
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        // Action Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = {
                                    val message = buildString {
                                        append("🥚✨ تخم مرغ شانسی قرار ما باز شد!\n")
                                        append("⏰ زمان (کی): ${currentProposal.whenTimeFa}\n")
                                        append("📍 مکان (کجا): ${currentProposal.whereLocationFa}\n")
                                        append("🎁 سورپرایز: ${currentProposal.specialSurpriseFa}\n")
                                        append("👗 استایل: ${currentProposal.outfitTipFa}\n")
                                        append("دوستت دارم تمام دنیای من! ❤️")
                                    }
                                    onSendDateToChat(message)
                                    onOpenChat()
                                },
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = SleekPinkPrimary),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .testTag("send_date_to_chat_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Chat,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "ارسال به چت دونفره 💬",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            OutlinedButton(
                                onClick = {
                                    currentProposal = SurpriseDateDeck.getRandomProposal(excludeId = currentProposal.id)
                                    crackStage = 0
                                },
                                shape = RoundedCornerShape(16.dp),
                                border = BorderStroke(1.5.dp, SleekPinkPrimary),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .testTag("crack_new_egg_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = null,
                                    tint = SleekPinkPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "تخم‌مرغ جدید 🥚",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = SleekPinkPrimary
                                )
                            }
                        }
                    }
                }
            }
        }

        // Previous Opened Date Proposals History
        if (openedHistory.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, SleekBorderLight)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = null,
                            tint = SleekPinkPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "قرارهای باز شده قبلی 📜",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = SleekTextPrimary
                        )
                    }

                    openedHistory.forEachIndexed { index, prop ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = SleekPinkLight.copy(alpha = 0.5f),
                            border = BorderStroke(1.dp, SleekBorderLight),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(text = prop.moodEmoji, fontSize = 22.sp)
                                    Column {
                                        Text(
                                            text = prop.whereLocationFa,
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = SleekTextPrimary
                                        )
                                        Text(
                                            text = prop.whenTimeFa,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = SleekTextSecondary
                                        )
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = SleekPinkPrimary,
                                    modifier = Modifier.clickable {
                                        currentProposal = prop
                                        crackStage = 3
                                    }
                                ) {
                                    Text(
                                        text = "مشاهده",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DateDetailRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    bgColor: Color,
    title: String,
    content: String,
    isHighlighted: Boolean = false
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = if (isHighlighted) bgColor.copy(alpha = 0.6f) else Color(0xFFF8FAFC),
        border = BorderStroke(1.dp, if (isHighlighted) iconColor.copy(alpha = 0.4f) else SleekBorderLight),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = bgColor,
                modifier = Modifier.size(42.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = SleekTextMuted
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = content,
                    style = if (isHighlighted) MaterialTheme.typography.titleSmall else MaterialTheme.typography.bodyMedium,
                    fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.Normal,
                    color = SleekTextPrimary,
                    lineHeight = 22.sp
                )
            }
        }
    }
}
