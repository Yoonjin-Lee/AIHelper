package com.example.aihelper.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aihelper.designsystem.AgentPalette
import com.example.aihelper.designsystem.AgentRadius
import com.example.aihelper.designsystem.AgentSpacing

@Composable
fun AgentChoiceCard(
    title: String,
    description: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
) {
    Surface(
        modifier = modifier
            .semantics { this.selected = selected }
            .clickable(role = Role.Checkbox, onClick = onClick),
        color = if (selected) AgentPalette.BlueSoft else AgentPalette.Surface,
        shape = AgentRadius.Large,
        border = BorderStroke(if (selected) 2.dp else 1.dp, if (selected) AgentPalette.Blue else AgentPalette.Line),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AgentSpacing.Lg),
            horizontalArrangement = Arrangement.spacedBy(AgentSpacing.Md),
            verticalAlignment = Alignment.Top,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(AgentSpacing.Xs),
            ) {
                if (label != null) {
                    Text(label, color = AgentPalette.Blue, style = MaterialTheme.typography.labelLarge)
                }
                Text(title, color = AgentPalette.Ink, style = MaterialTheme.typography.titleMedium)
                Text(description, color = AgentPalette.Muted, style = MaterialTheme.typography.bodyMedium)
            }
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(AgentRadius.Small)
                    .background(if (selected) AgentPalette.Blue else Color.White),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = if (selected) "✓" else "",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}
