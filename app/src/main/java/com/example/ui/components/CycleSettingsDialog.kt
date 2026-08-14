package com.example.ui.components

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.concurrent.TimeUnit

@Composable
fun CycleSettingsDialog(
    currentLastPeriodMillis: Long,
    currentCycleLength: Int,
    currentPeriodDuration: Int,
    onDismiss: () -> Unit,
    onSave: (lastPeriodMillis: Long, cycleLength: Int, periodDuration: Int) -> Unit
) {
    val now = System.currentTimeMillis()
    val initialDaysAgo = TimeUnit.MILLISECONDS.toDays(now - currentLastPeriodMillis).toInt().coerceIn(0, 40)

    var daysAgo by remember { mutableIntStateOf(initialDaysAgo) }
    var cycleLength by remember { mutableIntStateOf(currentCycleLength) }
    var periodDuration by remember { mutableIntStateOf(currentPeriodDuration) }

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(32.dp),
        containerColor = androidx.compose.ui.graphics.Color.White,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "تنظیمات چرخه پریود و P.M.S",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 1. Last period start date
                Column {
                    Text(
                        text = "تاریخ شروع آخرین پریود:",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = when (daysAgo) {
                            0 -> "شروع از امروز 🌸"
                            1 -> "دیروز (۱ روز پیش)"
                            else -> "$daysAgo روز پیش شروع شده است"
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Preset Quick Chips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        FilterChip(
                            selected = daysAgo == 0,
                            onClick = { daysAgo = 0 },
                            label = { Text("امروز", fontSize = 12.sp) }
                        )
                        FilterChip(
                            selected = daysAgo == 3,
                            onClick = { daysAgo = 3 },
                            label = { Text("۳ روز پیش", fontSize = 12.sp) }
                        )
                        FilterChip(
                            selected = daysAgo == 10,
                            onClick = { daysAgo = 10 },
                            label = { Text("۱۰ روز پیش", fontSize = 12.sp) }
                        )
                        FilterChip(
                            selected = daysAgo == 20,
                            onClick = { daysAgo = 20 },
                            label = { Text("۲۰ روز پیش (PMS)", fontSize = 12.sp) }
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Slider(
                        value = daysAgo.toFloat(),
                        onValueChange = { daysAgo = it.toInt() },
                        valueRange = 0f..35f,
                        steps = 34,
                        modifier = Modifier.testTag("days_ago_slider")
                    )
                }

                // 2. Cycle Length (default 28)
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "طول کل چرخه (معمولاً ۲۸ روز):",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "$cycleLength روز",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        IconButton(
                            onClick = { if (cycleLength > 20) cycleLength-- },
                            modifier = Modifier.testTag("decrease_cycle_length")
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "کاهش")
                        }

                        Slider(
                            value = cycleLength.toFloat(),
                            onValueChange = { cycleLength = it.toInt() },
                            valueRange = 21f..38f,
                            steps = 16,
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(
                            onClick = { if (cycleLength < 40) cycleLength++ },
                            modifier = Modifier.testTag("increase_cycle_length")
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "افزایش")
                        }
                    }
                }

                // 3. Period Duration (default 5)
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "مدت زمان خونریزی پریود:",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "$periodDuration روز",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Slider(
                        value = periodDuration.toFloat(),
                        onValueChange = { periodDuration = it.toInt() },
                        valueRange = 3f..8f,
                        steps = 4,
                        modifier = Modifier.testTag("period_duration_slider")
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val computedMillis = now - (daysAgo.toLong() * 24 * 60 * 60 * 1000)
                    onSave(computedMillis, cycleLength, periodDuration)
                },
                modifier = Modifier.testTag("save_cycle_settings_button")
            ) {
                Text("ذخیره و محاسبه مجدد")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.testTag("cancel_cycle_dialog")
            ) {
                Text("انصراف")
            }
        }
    )
}
