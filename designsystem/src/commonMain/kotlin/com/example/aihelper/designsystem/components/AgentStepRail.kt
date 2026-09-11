package com.example.aihelper.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aihelper.designsystem.AgentPalette
import com.example.aihelper.designsystem.AgentRadius
import com.example.aihelper.designsystem.AgentSpacing

data class AgentStep(
    val label: String,
)

@Composable
fun AgentStepRail(
    steps: List<AgentStep>,
    currentStep: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(AgentSpacing.Xs)) {
        steps.forEachIndexed { index, step ->
            val isActive = index == currentStep
            val isDone = index < currentStep
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(AgentRadius.Medium)
                    .background(if (isActive) AgentPalette.NavyRaised else Color.Transparent)
                    .padding(horizontal = AgentSpacing.Sm, vertical = AgentSpacing.Xs),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AgentSpacing.Sm),
            ) {
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(AgentRadius.Pill)
                        .background(if (isActive) AgentPalette.Mint else Color.Transparent),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = if (isDone) "✓" else "${index + 1}",
                        color = when {
                            isActive -> AgentPalette.Navy
                            isDone -> AgentPalette.Mint
                            else -> AgentPalette.OnDarkMuted
                        },
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Text(
                    text = step.label,
                    color = if (isActive || isDone) AgentPalette.OnDark else AgentPalette.OnDarkMuted,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}
