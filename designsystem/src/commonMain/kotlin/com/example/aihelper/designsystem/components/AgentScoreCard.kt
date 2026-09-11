package com.example.aihelper.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aihelper.designsystem.AgentPalette

@Composable
fun AgentScoreCard(
    score: Int,
    modifier: Modifier = Modifier,
    label: String = "AI 협업 준비도",
    status: String = "개선이 필요해요",
) {
    AgentCard(
        modifier = modifier,
        backgroundColor = AgentPalette.Navy,
        borderColor = AgentPalette.Navy,
    ) {
        Column {
            Text(label, color = AgentPalette.OnDarkMuted, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = score.toString(),
                    color = AgentPalette.OnDark,
                    fontSize = 76.sp,
                    lineHeight = 78.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-3).sp,
                )
                Spacer(Modifier.width(6.dp))
                Text("/100", color = AgentPalette.OnDarkMuted, style = MaterialTheme.typography.titleMedium)
            }
            Spacer(Modifier.height(6.dp))
            Text(status, color = AgentPalette.Mint, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(22.dp))
            Text(
                "코드 품질 점수가 아니라, AI에게 작업 방식을 얼마나 잘 설명하는지 나타냅니다.",
                color = AgentPalette.OnDarkMuted,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
