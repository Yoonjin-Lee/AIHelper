package com.example.aihelper.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val AgentLightColors = lightColorScheme(
    primary = AgentPalette.Blue,
    onPrimary = AgentPalette.OnDark,
    primaryContainer = AgentPalette.BlueSoft,
    onPrimaryContainer = AgentPalette.Ink,
    secondary = AgentPalette.Navy,
    onSecondary = AgentPalette.OnDark,
    secondaryContainer = AgentPalette.NavyRaised,
    onSecondaryContainer = AgentPalette.OnDark,
    tertiary = AgentPalette.Success,
    onTertiary = AgentPalette.OnDark,
    tertiaryContainer = AgentPalette.MintSoft,
    onTertiaryContainer = AgentPalette.Ink,
    error = AgentPalette.Danger,
    background = AgentPalette.Paper,
    onBackground = AgentPalette.Ink,
    surface = AgentPalette.Surface,
    onSurface = AgentPalette.Ink,
    surfaceVariant = AgentPalette.Paper,
    onSurfaceVariant = AgentPalette.Muted,
    outline = AgentPalette.Line,
    outlineVariant = AgentPalette.SoftLine,
)

private val AgentDarkColors = darkColorScheme(
    primary = AgentPalette.Mint,
    onPrimary = AgentPalette.Navy,
    primaryContainer = AgentPalette.NavyRaised,
    onPrimaryContainer = AgentPalette.OnDark,
    secondary = AgentPalette.Blue,
    onSecondary = AgentPalette.OnDark,
    tertiary = AgentPalette.Mint,
    onTertiary = AgentPalette.Navy,
    background = AgentPalette.Navy,
    onBackground = AgentPalette.OnDark,
    surface = AgentPalette.NavyRaised,
    onSurface = AgentPalette.OnDark,
    surfaceVariant = AgentPalette.NavyRaised,
    onSurfaceVariant = AgentPalette.OnDarkMuted,
    outline = AgentPalette.Muted,
)

private val AgentTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 68.sp,
        lineHeight = 70.sp,
        letterSpacing = (-3.2).sp,
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        lineHeight = 46.sp,
        letterSpacing = (-1.4).sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 42.sp,
        letterSpacing = (-1.2).sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        letterSpacing = (-0.8).sp,
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 26.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        letterSpacing = 1.sp,
    ),
)

@Composable
fun AgentTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) AgentDarkColors else AgentLightColors,
        typography = AgentTypography,
        shapes = androidx.compose.material3.Shapes(
            extraSmall = AgentRadius.Small,
            small = AgentRadius.Medium,
            medium = AgentRadius.Large,
            large = AgentRadius.ExtraLarge,
            extraLarge = AgentRadius.ExtraLarge,
        ),
        content = content,
    )
}
