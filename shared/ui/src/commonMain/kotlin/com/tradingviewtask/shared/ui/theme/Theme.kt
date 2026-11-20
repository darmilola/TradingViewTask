package com.tradingviewtask.shared.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val PrimaryColor = Color(0xFF2962FF)
val OnPrimaryColor = Color.White
val SecondaryColor = Color(0xFF131722)
val OnSecondaryColor = Color.White

val BackgroundLight = Color(0xFFFFFFFF)
val SurfaceLight = Color(0xFFFFFFFF)
val SurfaceVariantLight = Color(0xFFF8F9FA)
val OnBackgroundLight = Color(0xFF131722)
val OnSurfaceLight = Color(0xFF131722)
val OnSurfaceVariantLight = Color(0xFF787B86)

val BackgroundDark = Color(0xFF131722)
val SurfaceDark = Color(0xFF1E222D)
val SurfaceVariantDark = Color(0xFF2A2E39)
val OnBackgroundDark = Color.White
val OnSurfaceDark = Color.White
val OnSurfaceVariantDark = Color(0xFFB2B5BE)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,
    onPrimary = OnPrimaryColor,
    secondary = SecondaryColor,
    onSecondary = OnSecondaryColor,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = OnPrimaryColor,
    secondary = SecondaryColor,
    onSecondary = OnSecondaryColor,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight
)

object AppThemeDefaults {
    val colors: ColorScheme
        @Composable
        get() = MaterialTheme.colorScheme
}

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
