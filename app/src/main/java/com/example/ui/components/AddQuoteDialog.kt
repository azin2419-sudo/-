package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.QuoteCategory

@Composable
fun AddQuoteDialog(
    onDismiss: () -> Unit,
    onAddQuote: (text: String, author: String, category: QuoteCategory) -> Unit
) {
    var quoteText by remember { mutableStateOf("") }
    var authorName by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(QuoteCategory.ROMANTIC) }
    var isError by remember { mutableStateOf(false) }

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
                    imageVector = Icons.Default.Create,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "افزودن جمله یا دل‌نوشته عاشقانه",
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
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "دسته‌بندی پیام:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    QuoteCategory.entries.filter { it != QuoteCategory.ALL }.take(3).forEach { cat ->
                        FilterChip(
                            selected = selectedCategory == cat,
                            onClick = { selectedCategory = cat },
                            label = { Text("${cat.iconEmoji} ${cat.titleFa}", fontSize = 11.sp) }
                        )
                    }
                }

                OutlinedTextField(
                    value = quoteText,
                    onValueChange = {
                        quoteText = it
                        if (it.isNotBlank()) isError = false
                    },
                    label = { Text("متن جمله عاشقانه یا پیام دلجویی") },
                    placeholder = { Text("مثال: تو نور چشم و آرامش قلب منی...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("quote_text_input"),
                    minLines = 3,
                    maxLines = 5,
                    isError = isError,
                    supportingText = if (isError) {
                        { Text("لطفاً متن پیام را بنویسید") }
                    } else null
                )

                OutlinedTextField(
                    value = authorName,
                    onValueChange = { authorName = it },
                    label = { Text("نام گوینده / منبع (اختیاری)") },
                    placeholder = { Text("مثال: دل‌نوشته من / فروغ فرخزاد") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("author_name_input"),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (quoteText.isBlank()) {
                        isError = true
                    } else {
                        onAddQuote(quoteText.trim(), authorName.trim(), selectedCategory)
                    }
                },
                modifier = Modifier.testTag("submit_quote_button")
            ) {
                Text("افزودن به نوبت پیام‌ها")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.testTag("cancel_add_quote_button")
            ) {
                Text("انصراف")
            }
        }
    )
}
