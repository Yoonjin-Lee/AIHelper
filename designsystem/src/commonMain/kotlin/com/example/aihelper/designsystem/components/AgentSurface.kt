package com.example.aihelper.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.aihelper.designsystem.AgentPalette
import com.example.aihelper.designsystem.AgentRadius
import com.example.aihelper.designsystem.AgentSpacing

@Composable
fun AgentCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = AgentPalette.Surface,
    borderColor: Color = AgentPalette.Line,
    contentPadding: PaddingValues = PaddingValues(AgentSpacing.Lg),
    tonalElevation: Dp = 0.dp,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier,
        color = backgroundColor,
        shape = AgentRadius.Large,
        border = BorderStroke(1.dp, borderColor),
        tonalElevation = tonalElevation,
    ) {
        Box(modifier = Modifier.padding(contentPadding)) {
            content()
        }
    }
}
