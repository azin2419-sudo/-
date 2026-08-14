package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = SleekPinkGradientStart,
    onPrimary = Color.White,
    primaryContainer = SleekPinkDark,
    onPrimaryContainer = SleekPinkLight,
    secondary = SleekBluePrimary,
    onSecondary = Color.White,
    secondaryContainer = DarkSurfaceVariant,
    onSecondaryContainer = SleekBlueLight,
    tertiary = SleekGreyMedium,
    onTertiary = DarkBackground,
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = DarkSurfaceVariant,
    onBackground = DarkTextPrimary,
    onSurface = DarkTextPrimary,
    onSurfaceVariant = DarkTextSecondary
)

private val LightColorScheme = lightColorScheme(
    primary = SleekPinkPrimary,
    onPrimary = SleekSurface,
    primaryContainer = SleekPinkLight,
    onPrimaryContainer = SleekPinkDark,
    secondary = SleekBluePrimary,
    onSecondary = SleekSurface,
    secondaryContainer = SleekBlueLight,
    onSecondaryContainer = SleekBlueText,
    tertiary = SleekGreyText,
    onTertiary = SleekSurface,
    tertiaryContainer = SleekGreySoft,
    onTertiaryContainer = SleekGreyText,
    background = SleekBackground,
    surface = SleekSurface,
    surfaceVariant = SleekSurfaceVariant,
    onBackground = SleekTextPrimary,
    onSurface = SleekTextPrimary,
    onSurfaceVariant = SleekTextSecondary
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep intentional romantic feminine theme colors
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
