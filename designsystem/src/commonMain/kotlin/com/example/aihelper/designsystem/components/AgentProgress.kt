package com.example.aihelper.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.aihelper.designsystem.AgentPalette
import com.example.aihelper.designsystem.AgentRadius

@Composable
fun AgentProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    trackColor: Color = AgentPalette.SoftLine,
    progressColor: Color = AgentPalette.Blue,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(6.dp)
            .clip(AgentRadius.Pill)
            .background(trackColor),
    ) {
        androidx.compose.foundation.layout.BoxWithConstraints {
            Box(
                Modifier
                    .width(maxWidth * progress.coerceIn(0f, 1f))
                    .height(6.dp)
                    .clip(AgentRadius.Pill)
                    .background(progressColor),
            )
        }
    }
}
