package com.example

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.FormatQuote
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.CyclePhase
import com.example.ui.components.CycleSettingsDialog
import com.example.ui.screens.CalorieScreen
import com.example.ui.screens.CycleTrackerScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.OnlineMusicScreen
import com.example.ui.screens.QuotesLibraryScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.TwoPersonChatScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.PeriodAccent
import com.example.ui.theme.PmsAccent
import com.example.ui.theme.SleekPinkPrimary
import com.example.ui.viewmodel.LoveCareViewModel

enum class MainTab(
    val titleFa: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
) {
    HOME("امروز", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder, "tab_home"),
    CALORIE("کالری من", Icons.Filled.Restaurant, Icons.Outlined.Restaurant, "tab_calorie"),
    CHAT("چت دونفره", Icons.Filled.Chat, Icons.Outlined.Chat, "tab_chat"),
    MUSIC("موزیک", Icons.Filled.MusicNote, Icons.Outlined.MusicNote, "tab_music"),
    CYCLE("چرخه/PMS", Icons.Filled.CalendarMonth, Icons.Outlined.CalendarMonth, "tab_cycle"),
    QUOTES("جملات", Icons.Filled.FormatQuote, Icons.Outlined.FormatQuote, "tab_quotes"),
    SETTINGS("تنظیمات", Icons.Filled.Settings, Icons.Outlined.Settings, "tab_settings")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    LoveCareApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoveCareApp(viewModel: LoveCareViewModel = viewModel()) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedTab by remember { mutableStateOf(MainTab.HOME) }
    var showCycleDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    // Request notification permission on Android 13+
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(context, "برای ارسال خودکار اعلان‌ها، دسترسی لازم است", Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    // Show toast message when notification is sent
    LaunchedEffect(uiState.messageSentNotificationToast) {
        uiState.messageSentNotificationToast?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearToast()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "LoveCare",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = com.example.ui.theme.SleekPinkPrimary
                        )

                        if (uiState.cycleInfo.isPmsActive) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = PmsAccent.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "فاز P.M.S 🌙",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = PmsAccent,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        } else if (uiState.cycleInfo.phase == CyclePhase.MENSTRUATION) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = PeriodAccent.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "دوره پریود 🌸",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = PeriodAccent,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.sendImmediateTestNotification() },
                        modifier = Modifier.testTag("top_bar_send_notification_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.NotificationsActive,
                            contentDescription = "ارسال فوری اعلان",
                            tint = com.example.ui.theme.SleekPinkPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = com.example.ui.theme.SleekBackground
                )
            )
        },
        bottomBar = {
            Surface(
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = Color.White,
                shadowElevation = 8.dp,
                border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.SleekBorderLight)
            ) {
                NavigationBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("main_bottom_nav"),
                    containerColor = Color.Transparent,
                    tonalElevation = 0.dp
                ) {
                    MainTab.entries.forEach { tab ->
                        val isSelected = selectedTab == tab
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { selectedTab = tab },
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                    contentDescription = tab.titleFa
                                )
                            },
                            label = {
                                Text(
                                    text = tab.titleFa,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            modifier = Modifier.testTag(tab.testTag),
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = com.example.ui.theme.SleekPinkPrimary,
                                selectedTextColor = com.example.ui.theme.SleekPinkPrimary,
                                unselectedIconColor = com.example.ui.theme.SleekTextMuted,
                                unselectedTextColor = com.example.ui.theme.SleekTextMuted,
                                indicatorColor = com.example.ui.theme.SleekPinkLight
                            )
                        )
                    }
                }
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = selectedTab,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "tab_navigation"
            ) { targetTab ->
                when (targetTab) {
                    MainTab.HOME -> {
                        HomeScreen(
                            state = uiState,
                            viewModel = viewModel,
                            onOpenCycleDialog = { showCycleDialog = true },
                            onNavigateToCalorie = { selectedTab = MainTab.CALORIE }
                        )
                    }
                    MainTab.CALORIE -> {
                        CalorieScreen(
                            calorieProfile = uiState.calorieProfile,
                            dailyFoodLogs = uiState.dailyFoodLogs,
                            isAnalyzingFood = uiState.isAnalyzingFood,
                            aiAnalysisResult = uiState.aiAnalysisResult,
                            foodAnalysisError = uiState.foodAnalysisError,
                            partnerName = uiState.profile.name,
                            onUpdateProfile = { viewModel.updateCalorieProfile(it) },
                            onAddFoodLog = { name, cal, meal, p, c, f, isAi ->
                                viewModel.addFoodLogItem(name, cal, meal, p, c, f, isAi)
                            },
                            onDeleteFoodLog = { viewModel.deleteFoodLogItem(it) },
                            onClearLogs = { viewModel.clearDailyFoodLogs() },
                            onAnalyzeFoodImage = { bmp, note -> viewModel.analyzeFoodImage(bmp, note) },
                            onClearAiResult = { viewModel.clearAiAnalysisResult() }
                        )
                    }
                    MainTab.CHAT -> {
                        TwoPersonChatScreen(
                            messages = uiState.chatMessages,
                            partnerName = uiState.profile.name,
                            partnerNickname = uiState.profile.nickname,
                            onSendMessage = { sender, text, isSpecial ->
                                viewModel.sendChatMessage(sender, text, isSpecial)
                            },
                            onToggleHeart = { viewModel.toggleHeartReactMessage(it) },
                            onDeleteMessage = { viewModel.deleteChatMessage(it) }
                        )
                    }
                    MainTab.MUSIC -> {
                        OnlineMusicScreen(
                            tracks = uiState.musicTracks,
                            playerState = uiState.musicPlayerState,
                            onTogglePlayTrack = { viewModel.togglePlayPause(it) },
                            onSeekTrack = { viewModel.seekMusic(it) },
                            onVoiceGreeting = { viewModel.playWelcomeGreetingVoice() },
                            partnerName = uiState.profile.name
                        )
                    }
                    MainTab.QUOTES -> {
                        QuotesLibraryScreen(
                            quotes = uiState.quotesList,
                            currentQuoteId = uiState.todayQuote.id,
                            selectedCategory = uiState.profile.selectedCategory,
                            onCategorySelected = { viewModel.setQuoteCategory(it) },
                            onSelectQuoteAsActive = { viewModel.selectSpecificQuote(it) },
                            onToggleFavorite = { viewModel.toggleFavoriteQuote(it) },
                            onDeleteQuote = { viewModel.deleteQuote(it) },
                            onAddQuote = { text, author, cat -> viewModel.addCustomQuote(text, author, cat) }
                        )
                    }
                    MainTab.CYCLE -> {
                        CycleTrackerScreen(
                            cycleInfo = uiState.cycleInfo,
                            onOpenCycleDialog = { showCycleDialog = true }
                        )
                    }
                    MainTab.SETTINGS -> {
                        SettingsScreen(
                            profile = uiState.profile,
                            onUpdatePartnerName = { name, nick -> viewModel.updatePartnerName(name, nick) },
                            onUpdateNotificationTime = { h, m, en -> viewModel.updateNotificationTime(h, m, en) },
                            onToggleIncludePms = { viewModel.setIncludePmsInNotification(it) },
                            onToggleAutoGentle = { viewModel.toggleGentleModeAuto(it) },
                            onSendTestNotification = { viewModel.sendImmediateTestNotification() }
                        )
                    }
                }
            }
        }

        if (showCycleDialog) {
            CycleSettingsDialog(
                currentLastPeriodMillis = uiState.profile.lastPeriodStartDateMillis,
                currentCycleLength = uiState.profile.cycleLengthDays,
                currentPeriodDuration = uiState.profile.periodDurationDays,
                onDismiss = { showCycleDialog = false },
                onSave = { lastPeriod, length, duration ->
                    viewModel.updateCycleSettings(lastPeriod, length, duration)
                    showCycleDialog = false
                }
            )
        }
    }
}
