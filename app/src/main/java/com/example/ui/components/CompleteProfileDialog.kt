package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.ActivityLevel
import com.example.data.model.CalorieProfile
import com.example.data.model.PartnerProfile
import com.example.data.model.UserGender
import com.example.data.model.WeightGoal
import com.example.ui.theme.SleekBorderLight
import com.example.ui.theme.SleekPinkLight
import com.example.ui.theme.SleekPinkPrimary
import com.example.ui.theme.SleekTextMuted
import com.example.ui.theme.SleekTextPrimary
import com.example.ui.theme.SleekTextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompleteProfileDialog(
    partnerProfile: PartnerProfile,
    calorieProfile: CalorieProfile,
    onSave: (PartnerProfile, CalorieProfile) -> Unit,
    onDismiss: () -> Unit
) {
    var partnerName by remember { mutableStateOf(partnerProfile.name.ifBlank { "فرشته" }) }
    var partnerNickname by remember { mutableStateOf(partnerProfile.nickname.ifBlank { "فرشته خوشگلم" }) }
    var senderName by remember { mutableStateOf(partnerProfile.senderName.ifBlank { "پوریا" }) }

    var ageText by remember { mutableStateOf(calorieProfile.age.toString()) }
    var heightText by remember { mutableStateOf(calorieProfile.heightCm.toInt().toString()) }
    var weightText by remember { mutableStateOf(calorieProfile.weightKg.toInt().toString()) }
    var selectedGender by remember { mutableStateOf(calorieProfile.gender) }
    var selectedActivity by remember { mutableStateOf(calorieProfile.activityLevel) }
    var selectedGoal by remember { mutableStateOf(calorieProfile.goal) }

    var cycleLengthText by remember { mutableStateOf(partnerProfile.cycleLengthDays.toString()) }
    var periodDurationText by remember { mutableStateOf(partnerProfile.periodDurationDays.toString()) }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .fillMaxWidth(0.94f)
            .testTag("complete_profile_dialog"),
        content = {
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.5.dp, SleekPinkPrimary)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Title Header
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
                                modifier = Modifier.size(38.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        tint = SleekPinkPrimary,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            }
                            Column {
                                Text(
                                    text = "تکمیل مشخصات کامل 📋",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = SleekTextPrimary
                                )
                                Text(
                                    text = "برای شخصی‌سازی پیام‌ها، کالری، پروتئین و چرخه",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = SleekTextMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        IconButton(onClick = onDismiss) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "بستن", tint = SleekTextMuted)
                        }
                    }

                    if (errorMessage != null) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFFEE2E2),
                            border = BorderStroke(1.dp, Color(0xFFF87171))
                        ) {
                            Text(
                                text = errorMessage!!,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFDC2626),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                        }
                    }

                    // Section 1: Names
                    Text(
                        text = "۱. اسامی و مشخصات طرفین ❤️",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = SleekPinkPrimary
                    )

                    OutlinedTextField(
                        value = partnerName,
                        onValueChange = { partnerName = it },
                        label = { Text("نام بانو (مثال: فرشته)") },
                        modifier = Modifier.fillMaxWidth().testTag("profile_input_partner_name"),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SleekPinkPrimary,
                            unfocusedBorderColor = SleekBorderLight
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = partnerNickname,
                            onValueChange = { partnerNickname = it },
                            label = { Text("لقب عاشقانه") },
                            modifier = Modifier.weight(1f).testTag("profile_input_partner_nickname"),
                            shape = RoundedCornerShape(16.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = SleekPinkPrimary,
                                unfocusedBorderColor = SleekBorderLight
                            )
                        )

                        OutlinedTextField(
                            value = senderName,
                            onValueChange = { senderName = it },
                            label = { Text("نام آقا (مثال: پوریا)") },
                            modifier = Modifier.weight(1f).testTag("profile_input_sender_name"),
                            shape = RoundedCornerShape(16.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = SleekPinkPrimary,
                                unfocusedBorderColor = SleekBorderLight
                            )
                        )
                    }

                    // Section 2: Physical measurements & Calorie / Protein Profile
                    Text(
                        text = "۲. مشخصات بدنی و متابولیسم ⚖️",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = SleekPinkPrimary
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = ageText,
                            onValueChange = { if (it.all { c -> c.isDigit() } && it.length <= 3) ageText = it },
                            label = { Text("سن (سال)") },
                            modifier = Modifier.weight(1f).testTag("profile_input_age"),
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
                            onValueChange = { if (it.all { c -> c.isDigit() } && it.length <= 3) heightText = it },
                            label = { Text("قد (cm)") },
                            modifier = Modifier.weight(1f).testTag("profile_input_height"),
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
                            onValueChange = { if (it.all { c -> c.isDigit() } && it.length <= 3) weightText = it },
                            label = { Text("وزن (kg)") },
                            modifier = Modifier.weight(1f).testTag("profile_input_weight"),
                            shape = RoundedCornerShape(16.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = SleekPinkPrimary,
                                unfocusedBorderColor = SleekBorderLight
                            )
                        )
                    }

                    // Gender Selection
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        UserGender.entries.forEach { g ->
                            val isSel = selectedGender == g
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = if (isSel) SleekPinkPrimary else SleekPinkLight,
                                border = BorderStroke(1.dp, if (isSel) SleekPinkPrimary else SleekBorderLight),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedGender = g }
                            ) {
                                Box(
                                    modifier = Modifier.padding(vertical = 10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = g.titleFa,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSel) Color.White else SleekTextPrimary
                                    )
                                }
                            }
                        }
                    }

                    // Section 3: Cycle & PMS Info
                    Text(
                        text = "۳. تنظیمات چرخه و دوران PMS 🌸",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = SleekPinkPrimary
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = cycleLengthText,
                            onValueChange = { if (it.all { c -> c.isDigit() } && it.length <= 2) cycleLengthText = it },
                            label = { Text("طول چرخه (مثلا ۲۸ روز)") },
                            modifier = Modifier.weight(1f).testTag("profile_input_cycle_length"),
                            shape = RoundedCornerShape(16.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = SleekPinkPrimary,
                                unfocusedBorderColor = SleekBorderLight
                            )
                        )

                        OutlinedTextField(
                            value = periodDurationText,
                            onValueChange = { if (it.all { c -> c.isDigit() } && it.length <= 2) periodDurationText = it },
                            label = { Text("مدت پریود (مثلا ۵ روز)") },
                            modifier = Modifier.weight(1f).testTag("profile_input_period_duration"),
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

                    // Submit Button
                    Button(
                        onClick = {
                            if (partnerName.isBlank()) {
                                errorMessage = "لطفاً نام بانو را وارد کنید"
                                return@Button
                            }
                            if (senderName.isBlank()) {
                                errorMessage = "لطفاً نام همراه/آقا را وارد کنید"
                                return@Button
                            }
                            val age = ageText.toIntOrNull() ?: 24
                            val height = heightText.toFloatOrNull() ?: 165f
                            val weight = weightText.toFloatOrNull() ?: 58f
                            val cycleLen = cycleLengthText.toIntOrNull()?.coerceIn(21, 45) ?: 28
                            val periodDur = periodDurationText.toIntOrNull()?.coerceIn(2, 10) ?: 5

                            val updatedPartnerProfile = partnerProfile.copy(
                                name = partnerName.trim(),
                                nickname = partnerNickname.trim().ifBlank { partnerName.trim() },
                                senderName = senderName.trim(),
                                cycleLengthDays = cycleLen,
                                periodDurationDays = periodDur,
                                isProfileCompleted = true
                            )

                            val updatedCalorieProfile = calorieProfile.copy(
                                age = age.coerceIn(12, 100),
                                heightCm = height.coerceIn(100f, 230f),
                                weightKg = weight.coerceIn(30f, 250f),
                                gender = selectedGender,
                                activityLevel = selectedActivity,
                                goal = selectedGoal
                            )

                            onSave(updatedPartnerProfile, updatedCalorieProfile)
                            onDismiss()
                        },
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SleekPinkPrimary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("save_complete_profile_btn")
                    ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "ذخیره و ثبت مشخصات کامل ✨",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    )
}
