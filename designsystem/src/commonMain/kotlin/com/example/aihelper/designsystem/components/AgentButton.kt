package com.example.aihelper.designsystem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.aihelper.designsystem.AgentPalette
import com.example.aihelper.designsystem.AgentRadius
import com.example.aihelper.designsystem.AgentSize

enum class AgentButtonStyle {
    Primary,
    Secondary,
    Dark,
    Ghost,
}

@Composable
fun AgentButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: AgentButtonStyle = AgentButtonStyle.Primary,
) {
    val sizedModifier = modifier.defaultMinSize(minHeight = AgentSize.ControlHeight)
    val padding = PaddingValues(horizontal = 18.dp, vertical = 11.dp)

    when (style) {
        AgentButtonStyle.Primary -> Button(
            onClick = onClick,
            modifier = sizedModifier,
            enabled = enabled,
            shape = AgentRadius.Medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = AgentPalette.Blue,
                contentColor = Color.White,
                disabledContainerColor = AgentPalette.Line,
                disabledContentColor = AgentPalette.Muted,
            ),
            contentPadding = padding,
        ) { Text(text) }

        AgentButtonStyle.Secondary -> OutlinedButton(
            onClick = onClick,
            modifier = sizedModifier,
            enabled = enabled,
            shape = AgentRadius.Medium,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = AgentPalette.Ink),
            border = ButtonDefaults.outlinedButtonBorder(enabled).copy(brush = androidx.compose.ui.graphics.SolidColor(AgentPalette.Line)),
            contentPadding = padding,
        ) { Text(text) }

        AgentButtonStyle.Dark -> Button(
            onClick = onClick,
            modifier = sizedModifier,
            enabled = enabled,
            shape = AgentRadius.Medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = AgentPalette.Navy,
                contentColor = Color.White,
            ),
            contentPadding = padding,
        ) { Text(text) }

        AgentButtonStyle.Ghost -> TextButton(
            onClick = onClick,
            modifier = sizedModifier,
            enabled = enabled,
            shape = AgentRadius.Medium,
            colors = ButtonDefaults.textButtonColors(contentColor = AgentPalette.Muted),
            contentPadding = padding,
        ) { Text(text) }
    }
}
