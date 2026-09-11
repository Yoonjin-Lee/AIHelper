package com.example.aihelper.designsystem

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object AgentPalette {
    val Ink = Color(0xFF101828)
    val Muted = Color(0xFF667085)
    val Line = Color(0xFFD9DFE8)
    val SoftLine = Color(0xFFE9EDF3)
    val Paper = Color(0xFFF5F7FB)
    val Surface = Color(0xFFFFFFFF)
    val Navy = Color(0xFF101A2E)
    val NavyRaised = Color(0xFF18243C)
    val Blue = Color(0xFF3157E6)
    val BluePressed = Color(0xFF2749C9)
    val BlueSoft = Color(0xFFEEF2FF)
    val Mint = Color(0xFF7CFFB2)
    val MintSoft = Color(0xFFEAFFF1)
    val Success = Color(0xFF187747)
    val Amber = Color(0xFFC96B16)
    val AmberSoft = Color(0xFFFFF6E8)
    val Danger = Color(0xFFC43B4D)
    val OnDark = Color(0xFFFFFFFF)
    val OnDarkMuted = Color(0xFFAEB8CA)
}

object AgentSpacing {
    val None = 0.dp
    val Xxs = 4.dp
    val Xs = 8.dp
    val Sm = 12.dp
    val Md = 16.dp
    val Lg = 20.dp
    val Xl = 24.dp
    val Xxl = 32.dp
    val Xxxl = 44.dp
    val Section = 56.dp
}

object AgentRadius {
    val Small = RoundedCornerShape(7.dp)
    val Medium = RoundedCornerShape(11.dp)
    val Large = RoundedCornerShape(18.dp)
    val ExtraLarge = RoundedCornerShape(24.dp)
    val Pill = RoundedCornerShape(999.dp)
}

object AgentSize {
    val HeaderHeight = 68.dp
    val SidebarWidth = 248.dp
    val ContentMaxWidth = 1160.dp
    val ControlHeight = 46.dp
    val TouchTarget = 48.dp
}
