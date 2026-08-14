package com.example.ui.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ActivityLevel
import com.example.data.model.CalorieProfile
import com.example.data.model.FoodAnalysisResult
import com.example.data.model.FoodLogItem
import com.example.data.model.MealType
import com.example.data.model.UserGender
import com.example.data.model.WeightGoal
import com.example.ui.theme.PeriodAccent
import com.example.ui.theme.PmsAccent
import com.example.ui.theme.SleekBackground
import com.example.ui.theme.SleekBorderLight
import com.example.ui.theme.SleekPinkLight
import com.example.ui.theme.SleekPinkPrimary
import com.example.ui.theme.SleekTextMuted
import com.example.ui.theme.SleekTextPrimary
import com.example.ui.theme.SleekTextSecondary

enum class CalorieSectionTab(val title: String, val icon: ImageVector, val tag: String) {
    CALCULATOR("محاسبه‌گر من", Icons.Filled.Calculate, "tab_cal_calc"),
    DAILY_LOG("دفترچه امروز", Icons.Filled.Restaurant, "tab_cal_log"),
    AI_SCANNER("تشخیص هوشمند غذا", Icons.Filled.AutoAwesome, "tab_cal_ai"),
    GUIDE("متابولیسم و PMS", Icons.Filled.Psychology, "tab_cal_guide")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalorieScreen(
    calorieProfile: CalorieProfile,
    dailyFoodLogs: List<FoodLogItem>,
    isAnalyzingFood: Boolean,
    aiAnalysisResult: FoodAnalysisResult?,
    foodAnalysisError: String?,
    partnerName: String,
    onUpdateProfile: (CalorieProfile) -> Unit,
    onAddFoodLog: (name: String, calories: Int, mealType: MealType, protein: Int, carbs: Int, fat: Int, isAi: Boolean) -> Unit,
    onDeleteFoodLog: (String) -> Unit,
    onClearLogs: () -> Unit,
    onAnalyzeFoodImage: (Bitmap, String) -> Unit,
    onClearAiResult: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = CalorieSectionTab.entries

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SleekBackground)
    ) {
        // Section Header Tabs
        PrimaryTabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.White,
            contentColor = SleekPinkPrimary,
            modifier = Modifier.fillMaxWidth().testTag("calorie_subtabs")
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    modifier = Modifier.testTag(tab.tag),
                    text = {
                        Text(
                            text = tab.title,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.title,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                )
            }
        }

        when (tabs[selectedTab]) {
            CalorieSectionTab.CALCULATOR -> {
                CalorieCalculatorSection(
                    profile = calorieProfile,
                    partnerName = partnerName,
                    onSaveProfile = onUpdateProfile
                )
            }
            CalorieSectionTab.DAILY_LOG -> {
                DailyCalorieLogSection(
                    profile = calorieProfile,
                    foodLogs = dailyFoodLogs,
                    onAddFood = onAddFoodLog,
                    onDeleteFood = onDeleteFoodLog,
                    onClearAll = onClearLogs
                )
            }
            CalorieSectionTab.AI_SCANNER -> {
                AiFoodScannerSection(
                    isAnalyzing = isAnalyzingFood,
                    analysisResult = aiAnalysisResult,
                    errorMessage = foodAnalysisError,
                    onAnalyze = onAnalyzeFoodImage,
                    onAddAnalyzedToLog = { result, mealType ->
                        onAddFoodLog(
                            result.foodName,
                            result.estimatedCalories,
                            mealType,
                            result.proteinGrams,
                            result.carbsGrams,
                            result.fatGrams,
                            true
                        )
                    },
                    onClearResult = onClearAiResult
                )
            }
            CalorieSectionTab.GUIDE -> {
                CalorieMetabolismGuideSection(partnerName = partnerName)
            }
        }
    }
}

// -------------------------------------------------------------
// SECTION 1: PERSONAL CALORIE & BMR CALCULATOR
// -------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalorieCalculatorSection(
    profile: CalorieProfile,
    partnerName: String,
    onSaveProfile: (CalorieProfile) -> Unit
) {
    var ageText by remember(profile.age) { mutableStateOf(profile.age.toString()) }
    var weightText by remember(profile.weightKg) { mutableStateOf(profile.weightKg.toInt().toString()) }
    var heightText by remember(profile.heightCm) { mutableStateOf(profile.heightCm.toInt().toString()) }
    var selectedGender by remember(profile.gender) { mutableStateOf(profile.gender) }
    var selectedActivity by remember(profile.activityLevel) { mutableStateOf(profile.activityLevel) }
    var selectedGoal by remember(profile.goal) { mutableStateOf(profile.goal) }

    val currentProfile = remember(ageText, weightText, heightText, selectedGender, selectedActivity, selectedGoal) {
        val a = ageText.toIntOrNull() ?: profile.age
        val w = weightText.toFloatOrNull() ?: profile.weightKg
        val h = heightText.toFloatOrNull() ?: profile.heightCm
        CalorieProfile(
            age = a.coerceIn(12, 100),
            weightKg = w.coerceIn(30f, 250f),
            heightCm = h.coerceIn(100f, 230f),
            gender = selectedGender,
            activityLevel = selectedActivity,
            goal = selectedGoal
        )
    }

    val bmr = currentProfile.calculateBmr()
    val tdee = currentProfile.calculateTdee()
    val targetCalories = currentProfile.calculateTargetCalories()
    val macros = currentProfile.calculateMacros()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Result Highlight Banner
        Card(
            modifier = Modifier.fillMaxWidth().testTag("calorie_target_banner"),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(
                containerColor = SleekPinkLight.copy(alpha = 0.8f)
            ),
            border = BorderStroke(1.5.dp, SleekPinkPrimary),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = SleekPinkPrimary
                ) {
                    Text(
                        text = "کالری هدف روزانه شما 🎯",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Target Highlights: Calories & Protein side-by-side
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Calorie Target
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = SleekPinkLight,
                        border = BorderStroke(1.dp, SleekPinkPrimary),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "کالری هدف 🎯",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = SleekPinkPrimary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "$targetCalories",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Black,
                                color = SleekPinkPrimary
                            )
                            Text(
                                text = "kcal / روز",
                                style = MaterialTheme.typography.labelSmall,
                                color = SleekTextSecondary
                            )
                        }
                    }

                    // Protein Target
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color(0xFFFFF1F2),
                        border = BorderStroke(1.dp, Color(0xFFE11D48)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "پروتئین هدف 🥩",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE11D48)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${macros.proteinGrams}g",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFE11D48)
                            )
                            Text(
                                text = "گرم / روز",
                                style = MaterialTheme.typography.labelSmall,
                                color = SleekTextSecondary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "بر اساس هدف: ${selectedGoal.titleFa} • متابولیسم پایه (BMR): $bmr kcal",
                    style = MaterialTheme.typography.bodySmall,
                    color = SleekTextSecondary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Macro breakdown chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MacroChip(title = "پروتئین", grams = "${macros.proteinGrams}g", color = Color(0xFFE11D48), emoji = "🥩")
                    MacroChip(title = "کربوهیدرات", grams = "${macros.carbsGrams}g", color = Color(0xFFD97706), emoji = "🌾")
                    MacroChip(title = "چربی مفید", grams = "${macros.fatGrams}g", color = Color(0xFF059669), emoji = "🥑")
                    MacroChip(title = "آب روزانه", grams = "${String.format("%.1f", macros.waterLiters)}L", color = Color(0xFF0284C7), emoji = "💧")
                }
            }
        }

        // Input Form Card
        Card(
            modifier = Modifier.fillMaxWidth().testTag("calorie_input_form_card"),
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, SleekBorderLight),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "مشخصات فردی و بدنی 📝",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = SleekTextPrimary
                )

                // Gender Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    UserGender.entries.forEach { g ->
                        val isSel = selectedGender == g
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (isSel) SleekPinkPrimary else SleekPinkLight,
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    selectedGender = g
                                    onSaveProfile(currentProfile.copy(gender = g))
                                },
                            border = BorderStroke(1.dp, if (isSel) SleekPinkPrimary else SleekBorderLight)
                        ) {
                            Box(
                                modifier = Modifier.padding(vertical = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = g.titleFa,
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSel) Color.White else SleekTextPrimary
                                )
                            }
                        }
                    }
                }

                // Age, Weight, Height Fields
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = ageText,
                        onValueChange = {
                            if (it.length <= 3 && it.all { char -> char.isDigit() }) {
                                ageText = it
                                onSaveProfile(currentProfile)
                            }
                        },
                        label = { Text("سن (سال)") },
                        modifier = Modifier.weight(1f).testTag("cal_input_age"),
                        shape = RoundedCornerShape(16.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SleekPinkPrimary,
                            unfocusedBorderColor = SleekBorderLight
                        )
                    )

                    OutlinedTextField(
                        value = weightText,
                        onValueChange = {
                            if (it.length <= 3 && it.all { char -> char.isDigit() }) {
                                weightText = it
                                onSaveProfile(currentProfile)
                            }
                        },
                        label = { Text("وزن (kg)") },
                        modifier = Modifier.weight(1f).testTag("cal_input_weight"),
                        shape = RoundedCornerShape(16.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SleekPinkPrimary,
                            unfocusedBorderColor = SleekBorderLight
                        )
                    )

                    OutlinedTextField(
                        value = heightText,
                        onValueChange = {
                            if (it.length <= 3 && it.all { char -> char.isDigit() }) {
                                heightText = it
                                onSaveProfile(currentProfile)
                            }
                        },
                        label = { Text("قد (cm)") },
                        modifier = Modifier.weight(1f).testTag("cal_input_height"),
                        shape = RoundedCornerShape(16.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SleekPinkPrimary,
                            unfocusedBorderColor = SleekBorderLight
                        )
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Activity Level Selection
                Text(
                    text = "سطح فعالیت روزانه:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = SleekTextPrimary
                )

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    ActivityLevel.entries.forEach { level ->
                        val isSel = selectedActivity == level
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (isSel) SleekPinkLight else Color(0xFFFAFAFA),
                            border = BorderStroke(if (isSel) 1.5.dp else 1.dp, if (isSel) SleekPinkPrimary else SleekBorderLight),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedActivity = level
                                    onSaveProfile(currentProfile.copy(activityLevel = level))
                                }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = level.titleFa,
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSel) SleekPinkPrimary else SleekTextPrimary
                                    )
                                    Text(
                                        text = level.descriptionFa,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = SleekTextMuted
                                    )
                                }
                                if (isSel) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = SleekPinkPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Goal Selection
                Text(
                    text = "هدف بدنی و تناسب اندام:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = SleekTextPrimary
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    WeightGoal.entries.forEach { goal ->
                        val isSel = selectedGoal == goal
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (isSel) SleekPinkPrimary else SleekPinkLight,
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    selectedGoal = goal
                                    onSaveProfile(currentProfile.copy(goal = goal))
                                },
                            border = BorderStroke(1.dp, if (isSel) SleekPinkPrimary else SleekBorderLight)
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = goal.titleFa,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSel) Color.White else SleekTextPrimary,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MacroChip(title: String, grams: String, color: Color, emoji: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        border = BorderStroke(1.dp, color.copy(alpha = 0.3f)),
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = emoji, fontSize = 16.sp)
            Text(
                text = grams,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = SleekTextMuted,
                fontSize = 9.sp
            )
        }
    }
}

// -------------------------------------------------------------
// SECTION 2: DAILY FOOD & CALORIE LOG TRACKER
// -------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyCalorieLogSection(
    profile: CalorieProfile,
    foodLogs: List<FoodLogItem>,
    onAddFood: (String, Int, MealType, Int, Int, Int, Boolean) -> Unit,
    onDeleteFood: (String) -> Unit,
    onClearAll: () -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedMealFilter by remember { mutableStateOf<MealType?>(null) }

    val targetCalories = profile.calculateTargetCalories()
    val targetMacros = profile.calculateMacros()
    val consumedCalories = foodLogs.sumOf { it.calories }
    val consumedProtein = foodLogs.sumOf { it.proteinGrams }
    val remainingCalories = (targetCalories - consumedCalories).coerceAtLeast(0)
    val remainingProtein = (targetMacros.proteinGrams - consumedProtein).coerceAtLeast(0)
    val progressPercent = (consumedCalories.toFloat() / targetCalories.toFloat()).coerceIn(0f, 1f)
    val proteinProgressPercent = if (targetMacros.proteinGrams > 0) (consumedProtein.toFloat() / targetMacros.proteinGrams.toFloat()).coerceIn(0f, 1f) else 0f

    val filteredLogs = if (selectedMealFilter == null) {
        foodLogs
    } else {
        foodLogs.filter { it.mealType == selectedMealFilter }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Daily Calorie & Protein Summary Progress Card
        Card(
            modifier = Modifier.fillMaxWidth().testTag("calorie_progress_card"),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, SleekBorderLight),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Calorie Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "گزارش کالری امروز 🔥",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = SleekTextPrimary
                        )
                        Text(
                            text = "مصرف‌شده: $consumedCalories از $targetCalories کیلوکالری",
                            style = MaterialTheme.typography.bodySmall,
                            color = SleekTextSecondary
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (consumedCalories <= targetCalories) SleekPinkLight else Color(0xFFFEE2E2)
                    ) {
                        Text(
                            text = if (consumedCalories <= targetCalories) "باقیمانده: $remainingCalories kcal" else "مازاد: ${consumedCalories - targetCalories} kcal",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (consumedCalories <= targetCalories) SleekPinkPrimary else Color(0xFFDC2626),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                LinearProgressIndicator(
                    progress = { progressPercent },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(CircleShape),
                    color = if (consumedCalories <= targetCalories) SleekPinkPrimary else Color(0xFFDC2626),
                    trackColor = SleekBorderLight
                )

                // Protein Row (Equal Prominence)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "گزارش پروتئین مصرفی 🥩",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE11D48)
                        )
                        Text(
                            text = "مصرف‌شده: $consumedProtein از ${targetMacros.proteinGrams} گرم",
                            style = MaterialTheme.typography.bodySmall,
                            color = SleekTextSecondary
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFFFF1F2)
                    ) {
                        Text(
                            text = if (consumedProtein >= targetMacros.proteinGrams) "هدف تکمیل شد! 👏" else "باقیمانده: $remainingProtein گرم",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE11D48),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                LinearProgressIndicator(
                    progress = { proteinProgressPercent },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(CircleShape),
                    color = Color(0xFFE11D48),
                    trackColor = SleekBorderLight
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Breakdown per meal
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MealType.entries.forEach { meal ->
                        val mealCal = foodLogs.filter { it.mealType == meal }.sumOf { it.calories }
                        val mealProt = foodLogs.filter { it.mealType == meal }.sumOf { it.proteinGrams }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = meal.emoji, fontSize = 16.sp)
                            Text(
                                text = "${meal.titleFa}: $mealCal kcal",
                                style = MaterialTheme.typography.labelSmall,
                                color = SleekTextSecondary,
                                fontSize = 10.sp
                            )
                            Text(
                                text = "🥩 ${mealProt}g",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFFE11D48),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Action Buttons Row: Quick Add & Filter Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LazyRow(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                item {
                    FilterChip(
                        selected = selectedMealFilter == null,
                        onClick = { selectedMealFilter = null },
                        label = { Text("همه") },
                        shape = RoundedCornerShape(16.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SleekPinkPrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
                items(MealType.entries) { meal ->
                    FilterChip(
                        selected = selectedMealFilter == meal,
                        onClick = { selectedMealFilter = if (selectedMealFilter == meal) null else meal },
                        label = { Text("${meal.emoji} ${meal.titleFa}") },
                        shape = RoundedCornerShape(16.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SleekPinkPrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            Surface(
                shape = CircleShape,
                color = SleekPinkPrimary,
                modifier = Modifier
                    .size(42.dp)
                    .clickable { showAddDialog = true }
                    .testTag("open_add_food_dialog_btn"),
                shadowElevation = 3.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "افزودن غذا",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        // Food Items List
        if (filteredLogs.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, SleekBorderLight)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "🥗", fontSize = 36.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "هنوز غذایی در این وعده ثبت نشده است",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SleekTextMuted
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(filteredLogs, key = { it.id }) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth().testTag("food_log_item_${item.id}"),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, SleekBorderLight),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = SleekPinkLight,
                                    modifier = Modifier.size(40.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(text = item.mealType.emoji, fontSize = 18.sp)
                                    }
                                }

                                Column {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text(
                                            text = item.name,
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = SleekTextPrimary
                                        )
                                        if (item.isAiDetected) {
                                            Surface(
                                                shape = RoundedCornerShape(6.dp),
                                                color = Color(0xFFEDE9FE)
                                            ) {
                                                Text(
                                                    text = "AI ✨",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = Color(0xFF7C3AED),
                                                    fontSize = 9.sp,
                                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                                )
                                            }
                                        }
                                    }
                                    Text(
                                        text = "${item.mealType.titleFa} • پروتئین: ${item.proteinGrams}g • کربوهیدرات: ${item.carbsGrams}g",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = SleekTextMuted
                                    )
                                }
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                // Protein Badge
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFFFFF1F2),
                                    border = BorderStroke(1.dp, Color(0xFFFECDD3))
                                ) {
                                    Text(
                                        text = "🥩 ${item.proteinGrams}g",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFE11D48),
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }

                                Text(
                                    text = "${item.calories} kcal",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = SleekPinkPrimary
                                )

                                IconButton(
                                    onClick = { onDeleteFood(item.id) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "حذف",
                                        tint = SleekTextMuted,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddFoodLogDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name, cal, meal, p, c, f ->
                onAddFood(name, cal, meal, p, c, f, false)
                showAddDialog = false
            }
        )
    }
}

// -------------------------------------------------------------
// SECTION 3: AI FOOD CALORIE DETECTION SCANNER
// -------------------------------------------------------------
@Composable
fun AiFoodScannerSection(
    isAnalyzing: Boolean,
    analysisResult: FoodAnalysisResult?,
    errorMessage: String?,
    onAnalyze: (Bitmap, String) -> Unit,
    onAddAnalyzedToLog: (FoodAnalysisResult, MealType) -> Unit,
    onClearResult: () -> Unit
) {
    val context = LocalContext.current
    var selectedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var userNoteText by remember { mutableStateOf("") }
    var selectedMealForAdd by remember { mutableStateOf(MealType.LUNCH) }
    var showAddedBanner by remember { mutableStateOf(false) }

    // Gallery Picker Launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                selectedBitmap = bitmap
                onClearResult()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // AI Scanner Header Card
        Card(
            modifier = Modifier.fillMaxWidth().testTag("ai_scanner_header_card"),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, SleekBorderLight),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFF3E8FF)
                    ) {
                        Text(
                            text = "تشخیص هوش مصنوعی Gemini ✨",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF7C3AED),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "کالری‌سنج تصویری غذا 📸",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = SleekTextPrimary
                    )
                    Text(
                        text = "از غذای خود عکس بگیرید تا هوش مصنوعی کالری، درشت‌مغذی‌ها و ترکیبات آن را فوراً محاسبه کند.",
                        style = MaterialTheme.typography.bodySmall,
                        color = SleekTextMuted
                    )
                }
            }
        }

        // Image Selection & Preview Box
        Card(
            modifier = Modifier.fillMaxWidth().testTag("image_picker_card"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, SleekBorderLight)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (selectedBitmap != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(SleekBackground),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            bitmap = selectedBitmap!!.asImageBitmap(),
                            contentDescription = "عکس غذای انتخاب‌شده",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(SleekPinkLight.copy(alpha = 0.5f))
                            .clickable { galleryLauncher.launch("image/*") }
                            .testTag("upload_food_placeholder"),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Image,
                                contentDescription = "انتخاب تصویر",
                                tint = SleekPinkPrimary,
                                modifier = Modifier.size(44.dp)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "برای انتخاب عکس غذا از گالری لمس کنید",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = SleekPinkPrimary
                            )
                        }
                    }
                }

                // Sample Foods for quick test
                Text(
                    text = "یا انتخاب از غذاهای نمونه برای تست سریع:",
                    style = MaterialTheme.typography.labelSmall,
                    color = SleekTextMuted
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val sampleFoods = listOf(
                        SampleFoodItem("سالاد سزار مرغ", "🥗", 420),
                        SampleFoodItem("پلو خورش قیمه", "🍛", 580),
                        SampleFoodItem("املت گوجه با نان", "🍳", 340),
                        SampleFoodItem("اوتمیل با توت‌فرنگی", "🥣", 260),
                        SampleFoodItem("ماهی قزل‌آلا گریل", "🐟", 410),
                        SampleFoodItem("دمنوش و شکلات تلخ", "☕", 90)
                    )

                    items(sampleFoods) { sample ->
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = SleekPinkLight,
                            border = BorderStroke(1.dp, SleekBorderLight),
                            modifier = Modifier.clickable {
                                // Create a solid colored dummy bitmap for instant testing
                                val dummyBitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888)
                                selectedBitmap = dummyBitmap
                                userNoteText = sample.title
                                onClearResult()
                            }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(text = sample.emoji, fontSize = 14.sp)
                                Text(
                                    text = sample.title,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = SleekTextPrimary
                                )
                            }
                        }
                    }
                }

                // User optional note
                OutlinedTextField(
                    value = userNoteText,
                    onValueChange = { userNoteText = it },
                    placeholder = { Text("توضیح دلخواه (مثلاً: بدون سس، با روغن زیتون)") },
                    modifier = Modifier.fillMaxWidth().testTag("ai_food_note_input"),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SleekPinkPrimary,
                        unfocusedBorderColor = SleekBorderLight
                    ),
                    singleLine = true
                )

                // Analyze Trigger Button
                Button(
                    onClick = {
                        val bmp = selectedBitmap ?: Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888)
                        onAnalyze(bmp, userNoteText)
                    },
                    enabled = !isAnalyzing,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SleekPinkPrimary),
                    modifier = Modifier.fillMaxWidth().height(48.dp).testTag("analyze_food_btn")
                ) {
                    if (isAnalyzing) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(22.dp),
                            strokeWidth = 2.5.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("هوش مصنوعی در حال بررسی تصویر...")
                    } else {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "تشخیص کالری و مواد مغذی با هوش مصنوعی",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Analysis Result Card (If Available)
        if (analysisResult != null) {
            Card(
                modifier = Modifier.fillMaxWidth().testTag("ai_analysis_result_card"),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.5.dp, SleekPinkPrimary),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
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
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = analysisResult.foodName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = SleekTextPrimary
                            )
                            Text(
                                text = "دقت تحلیل هوش مصنوعی: ${analysisResult.confidenceScore}",
                                style = MaterialTheme.typography.labelSmall,
                                color = SleekTextMuted
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            // Protein Badge
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = Color(0xFFE11D48)
                            ) {
                                Text(
                                    text = "🥩 ${analysisResult.proteinGrams}g پروتئین",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }

                            // Calories Badge
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = SleekPinkPrimary
                            ) {
                                Text(
                                    text = "🔥 ${analysisResult.estimatedCalories} kcal",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }

                    Text(
                        text = "بازه تخمینی: ${analysisResult.calorieRange}",
                        style = MaterialTheme.typography.bodySmall,
                        color = SleekPinkPrimary,
                        fontWeight = FontWeight.Medium
                    )

                    // Macro Breakdown in Result
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        MacroChip(title = "پروتئین", grams = "${analysisResult.proteinGrams}g", color = Color(0xFFE11D48), emoji = "🥩")
                        MacroChip(title = "کربوهیدرات", grams = "${analysisResult.carbsGrams}g", color = Color(0xFFD97706), emoji = "🌾")
                        MacroChip(title = "چربی", grams = "${analysisResult.fatGrams}g", color = Color(0xFF059669), emoji = "🥑")
                        MacroChip(title = "فیبر", grams = "${analysisResult.fiberGrams}g", color = Color(0xFF7C3AED), emoji = "🥦")
                    }

                    // Ingredients
                    Text(
                        text = "ترکیبات تشخیص‌داده‌شده:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = SleekTextPrimary
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(analysisResult.ingredients) { ing ->
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = SleekPinkLight
                            ) {
                                Text(
                                    text = ing,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SleekTextPrimary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    // Health and PMS Advice
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFFDF2F8),
                        border = BorderStroke(1.dp, Color(0xFFFBCFE8))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "💡 نکته سلامت و دوران قاعدگی:",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFBE185D)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${analysisResult.healthTip}\n${analysisResult.pmsBenefit}",
                                style = MaterialTheme.typography.bodySmall,
                                color = SleekTextSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Add to Today's Meals Section
                    Text(
                        text = "افزودن به وعده‌های غذایی امروز:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = SleekTextPrimary
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        MealType.entries.forEach { meal ->
                            val isSel = selectedMealForAdd == meal
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSel) SleekPinkPrimary else SleekPinkLight,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedMealForAdd = meal }
                            ) {
                                Text(
                                    text = "${meal.emoji} ${meal.titleFa}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSel) Color.White else SleekTextPrimary,
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    Button(
                        onClick = {
                            onAddAnalyzedToLog(analysisResult, selectedMealForAdd)
                            showAddedBanner = true
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SleekPinkPrimary),
                        modifier = Modifier.fillMaxWidth().testTag("add_ai_food_to_log_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "ثبت در وعده ${selectedMealForAdd.titleFa} (${analysisResult.estimatedCalories} کالری)",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if (showAddedBanner) {
                        Text(
                            text = "✅ با موفقیت در وعده ${selectedMealForAdd.titleFa} ثبت شد!",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color(0xFF059669),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

private data class SampleFoodItem(val title: String, val emoji: String, val calories: Int)

// -------------------------------------------------------------
// SECTION 4: METABOLISM & PMS NUTRITION GUIDE
// -------------------------------------------------------------
@Composable
fun CalorieMetabolismGuideSection(partnerName: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().testTag("pms_metabolism_card"),
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, SleekBorderLight),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = PmsAccent.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = "علم متابولیسم و چرخه هورمونی 🧬",
                        style = MaterialTheme.typography.labelSmall,
                        color = PmsAccent,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Text(
                    text = "چرا در فاز لوتئال و PMS به کالری بیشتری نیاز است؟",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = SleekTextPrimary
                )

                Text(
                    text = "در فاز لوتئال (یک هفته مانده به پریود)، دمای پایه بدن به دلیل افزایش هورمون پروژسترون افزایش می‌یابد. در این دوران، متابولیسم بدن بین ۱۰۰ تا ۳۰۰ کیلوکالری در روز بیشتر می‌سوزاند. بنابراین احساس گرسنگی کاملاً طبیعی و فیزیولوژیک است!",
                    style = MaterialTheme.typography.bodySmall,
                    color = SleekTextSecondary,
                    lineHeight = 22.sp
                )
            }
        }

        // Golden Nutrition Rules Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, SleekBorderLight),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "توصیه‌های طلایی تغذیه برای $partnerName 🌸",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = SleekTextPrimary
                )

                NutritionTipItem(
                    emoji = "🍫",
                    title = "شکلات تلخ بالای ۷۵٪",
                    desc = "سرشار از منیزیم طبیعی برای تسکین انقباضات رحمی و افزایش سروتونین."
                )

                NutritionTipItem(
                    emoji = "🥑",
                    title = "چربی‌های سالم (آووکادو، زیتون، گردو)",
                    desc = "کمک به تولید متعادل هورمون‌ها و جلوگیری از نوسانات قند خون."
                )

                NutritionTipItem(
                    emoji = "💧",
                    title = "آب‌رسانی و کاهش نمک",
                    desc = "نوشیدن آب کافی و کاهش سدیم از احتباس آب و ورم دوران پریود جلوگیری می‌کند."
                )

                NutritionTipItem(
                    emoji = "🍵",
                    title = "دمنوش زنجبیل و دارچین و بابونه",
                    desc = "خاصیت ضدالتهابی قوی، کاهش دردهای شکمی و ایجاد آرامش عمیق شبانه."
                )
            }
        }
    }
}

@Composable
fun NutritionTipItem(emoji: String, title: String, desc: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.Top
    ) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = SleekPinkLight,
            modifier = Modifier.size(34.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(text = emoji, fontSize = 16.sp)
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = SleekTextPrimary
            )
            Text(
                text = desc,
                style = MaterialTheme.typography.bodySmall,
                color = SleekTextMuted
            )
        }
    }
}

// -------------------------------------------------------------
// ADD FOOD DIALOG
// -------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFoodLogDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, calories: Int, mealType: MealType, protein: Int, carbs: Int, fat: Int) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var caloriesText by remember { mutableStateOf("") }
    var mealType by remember { mutableStateOf(MealType.BREAKFAST) }
    var proteinText by remember { mutableStateOf("") }
    var carbsText by remember { mutableStateOf("") }
    var fatText by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "افزودن غذای جدید 🍽️",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("نام غذا یا نوشیدنی") },
                    modifier = Modifier.fillMaxWidth().testTag("dialog_food_name_input"),
                    shape = RoundedCornerShape(14.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = caloriesText,
                    onValueChange = { if (it.all { c -> c.isDigit() }) caloriesText = it },
                    label = { Text("کالری (کیلوکالری)") },
                    modifier = Modifier.fillMaxWidth().testTag("dialog_food_cal_input"),
                    shape = RoundedCornerShape(14.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                Text(
                    text = "وعده غذایی:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    MealType.entries.forEach { meal ->
                        val isSel = mealType == meal
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSel) SleekPinkPrimary else SleekPinkLight,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { mealType = meal }
                        ) {
                            Text(
                                text = "${meal.emoji}\n${meal.titleFa}",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (isSel) Color.White else SleekTextPrimary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 6.dp)
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    OutlinedTextField(
                        value = proteinText,
                        onValueChange = { if (it.all { c -> c.isDigit() }) proteinText = it },
                        label = { Text("پروتئین (g)") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = carbsText,
                        onValueChange = { if (it.all { c -> c.isDigit() }) carbsText = it },
                        label = { Text("کربوهیدرات (g)") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = fatText,
                        onValueChange = { if (it.all { c -> c.isDigit() }) fatText = it },
                        label = { Text("چربی (g)") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val cal = caloriesText.toIntOrNull() ?: 0
                    val p = proteinText.toIntOrNull() ?: 0
                    val c = carbsText.toIntOrNull() ?: 0
                    val f = fatText.toIntOrNull() ?: 0
                    if (name.isNotBlank() && cal > 0) {
                        onConfirm(name, cal, mealType, p, c, f)
                    }
                },
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SleekPinkPrimary),
                modifier = Modifier.testTag("dialog_food_confirm_btn")
            ) {
                Text("افزودن غذا")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("انصراف")
            }
        }
    )
}
