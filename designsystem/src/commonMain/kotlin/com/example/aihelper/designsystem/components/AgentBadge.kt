package com.example.aihelper.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.aihelper.designsystem.AgentPalette
import com.example.aihelper.designsystem.AgentRadius

enum class AgentBadgeTone(
    val background: Color,
    val foreground: Color,
) {
    Neutral(AgentPalette.Paper, AgentPalette.Muted),
    Info(AgentPalette.BlueSoft, AgentPalette.Blue),
    Success(AgentPalette.MintSoft, AgentPalette.Success),
    Warning(AgentPalette.AmberSoft, AgentPalette.Amber),
    Dark(AgentPalette.NavyRaised, AgentPalette.OnDark),
}

@Composable
fun AgentBadge(
    text: String,
    modifier: Modifier = Modifier,
    tone: AgentBadgeTone = AgentBadgeTone.Neutral,
    showDot: Boolean = false,
) {
    Row(
        modifier = modifier
            .clip(AgentRadius.Pill)
            .background(tone.background)
            .then(Modifier),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(Modifier.width(10.dp))
        if (showDot) {
            Spacer(
                Modifier
                    .size(6.dp)
                    .clip(AgentRadius.Pill)
                    .background(tone.foreground),
            )
            Spacer(Modifier.width(6.dp))
        }
        Text(
            text = text,
            color = tone.foreground,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier,
        )
        Spacer(Modifier.width(10.dp))
        Spacer(Modifier.size(width = 0.dp, height = 28.dp))
    }
}
