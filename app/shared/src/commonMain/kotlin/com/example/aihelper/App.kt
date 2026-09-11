package com.example.aihelper

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aihelper.designsystem.*
import com.example.aihelper.designsystem.components.*

private enum class SetupScreen { Connect, Diagnosis, Problems, Recommendation, Review, Test, Apply }

private val setupSteps = listOf(
    AgentStep("프로젝트 연결"), AgentStep("AI 협업 진단"), AgentStep("문제 선택"),
    AgentStep("설정 추천"), AgentStep("내용 확인"), AgentStep("효과 검증"),
)

@Composable
@Preview
fun App() {
    AgentTheme {
        var screen by remember { mutableStateOf(SetupScreen.Connect) }
        val selected = remember { mutableStateListOf(0, 1) }
        AgentAppShell(screen.ordinal.coerceAtMost(5), onReset = { screen = SetupScreen.Connect }) {
            when (screen) {
                SetupScreen.Connect -> ConnectScreen { screen = SetupScreen.Diagnosis }
                SetupScreen.Diagnosis -> DiagnosisScreen { screen = SetupScreen.Problems }
                SetupScreen.Problems -> ProblemsScreen(selected, { index ->
                    if (index in selected) selected.remove(index) else selected.add(index)
                }) { screen = SetupScreen.Recommendation }
                SetupScreen.Recommendation -> RecommendationScreen { screen = SetupScreen.Review }
                SetupScreen.Review -> ReviewScreen { screen = SetupScreen.Test }
                SetupScreen.Test -> TestScreen { screen = SetupScreen.Apply }
                SetupScreen.Apply -> ApplyScreen { screen = SetupScreen.Connect }
            }
        }
    }
}

@Composable
private fun AgentAppShell(currentStep: Int, onReset: () -> Unit, content: @Composable () -> Unit) {
    Column(Modifier.fillMaxSize().background(AgentPalette.Paper)) {
        TopHeader(onReset)
        BoxWithConstraints(Modifier.fillMaxSize()) {
            if (maxWidth >= 880.dp) {
                Row(Modifier.fillMaxSize()) {
                    DesktopSidebar(currentStep)
                    MainContent(Modifier.weight(1f), content)
                }
            } else {
                Column(Modifier.fillMaxSize()) {
                    MobileStepBar(currentStep)
                    MainContent(Modifier.weight(1f), content)
                }
            }
        }
    }
}

@Composable
private fun TopHeader(onReset: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().height(AgentSize.HeaderHeight).background(AgentPalette.Navy)
            .padding(horizontal = AgentSpacing.Xl),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(AgentSpacing.Sm)) {
            Box(Modifier.size(32.dp).clip(AgentRadius.Medium).background(AgentPalette.Mint), contentAlignment = Alignment.Center) {
                Text("A/", color = AgentPalette.Navy, fontWeight = FontWeight.Bold)
            }
            Text("Agent Setup Builder", color = Color.White, style = MaterialTheme.typography.titleMedium)
            AgentBadge("DESIGN SYSTEM", tone = AgentBadgeTone.Dark)
        }
        AgentButton("처음부터 보기", onReset, style = AgentButtonStyle.Ghost)
    }
}

@Composable
private fun DesktopSidebar(currentStep: Int) {
    Column(
        Modifier.width(AgentSize.SidebarWidth).fillMaxHeight().background(AgentPalette.Navy)
            .padding(AgentSpacing.Lg),
    ) {
        AgentStepRail(setupSteps, currentStep)
        Spacer(Modifier.weight(1f))
        Surface(color = Color.Transparent, shape = AgentRadius.Large, border = BorderStroke(1.dp, Color(0xFF344058))) {
            Column(Modifier.padding(AgentSpacing.Md)) {
                Text("프로젝트 밖은 변경하지 않아요", color = Color.White, style = MaterialTheme.typography.labelLarge)
                Spacer(Modifier.height(AgentSpacing.Xs))
                Text("민감 파일은 분석에서 제외하고, 모든 변경은 적용 전에 보여드립니다.", color = AgentPalette.OnDarkMuted, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
private fun MobileStepBar(currentStep: Int) {
    Row(
        Modifier.fillMaxWidth().background(AgentPalette.Navy).horizontalScroll(rememberScrollState())
            .padding(horizontal = AgentSpacing.Md, vertical = AgentSpacing.Xs),
        horizontalArrangement = Arrangement.spacedBy(AgentSpacing.Xs),
    ) {
        setupSteps.forEachIndexed { index, step ->
            AgentBadge(
                if (index < currentStep) "✓ ${step.label}" else "${index + 1} ${step.label}",
                tone = if (index == currentStep) AgentBadgeTone.Success else AgentBadgeTone.Dark,
            )
        }
    }
}

@Composable
private fun MainContent(modifier: Modifier, content: @Composable () -> Unit) {
    Box(
        modifier.fillMaxHeight().verticalScroll(rememberScrollState())
            .padding(horizontal = AgentSpacing.Xl, vertical = AgentSpacing.Xxxl),
        contentAlignment = Alignment.TopCenter,
    ) { Box(Modifier.fillMaxWidth().widthIn(max = AgentSize.ContentMaxWidth)) { content() } }
}

@Composable
private fun SectionIntro(eyebrow: String, title: String, description: String, modifier: Modifier = Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(AgentSpacing.Sm)) {
        Text(eyebrow.uppercase(), color = AgentPalette.Blue, style = MaterialTheme.typography.labelMedium)
        Text(title, color = AgentPalette.Ink, style = MaterialTheme.typography.headlineLarge)
        Text(description, color = AgentPalette.Muted, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun PageHeader(eyebrow: String, title: String, description: String, action: String, onAction: () -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
        SectionIntro(eyebrow, title, description, Modifier.weight(1f))
        Spacer(Modifier.width(AgentSpacing.Lg))
        AgentButton(action, onAction)
    }
}

@Composable
private fun ConnectScreen(onContinue: () -> Unit) {
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val wide = maxWidth >= 820.dp
        val intro: @Composable ColumnScope.() -> Unit = {
            Text("YOUR AGENT'S FIRST DAY", color = AgentPalette.Blue, style = MaterialTheme.typography.labelMedium)
            Text(
                "AI가 프로젝트에서\n일하는 법을 알려주세요.", color = AgentPalette.Ink,
                style = if (wide) MaterialTheme.typography.displayLarge else MaterialTheme.typography.displaySmall,
            )
            Text("저장소를 분석해 부족한 작업 규칙을 찾고, 필요한 설정만 만들어 실제 효과까지 검증합니다.", color = AgentPalette.Muted, style = MaterialTheme.typography.bodyLarge)
            Row(horizontalArrangement = Arrangement.spacedBy(AgentSpacing.Xs)) {
                AgentBadge("잘못된 모듈 수정"); AgentBadge("테스트 누락")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(AgentSpacing.Xs)) {
                AgentButton("샘플로 3분 체험", onContinue)
                AgentButton("내 저장소 분석하기 →", {}, style = AgentButtonStyle.Ghost)
            }
        }
        if (wide) {
            Row(Modifier.fillMaxWidth().padding(vertical = AgentSpacing.Xxxl), horizontalArrangement = Arrangement.spacedBy(AgentSpacing.Xxxl), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1.08f), verticalArrangement = Arrangement.spacedBy(AgentSpacing.Xl), content = intro)
                ProjectConnectCard(onContinue, Modifier.weight(.92f))
            }
        } else {
            Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(AgentSpacing.Xxl)) {
                Column(verticalArrangement = Arrangement.spacedBy(AgentSpacing.Xl), content = intro)
                ProjectConnectCard(onContinue)
            }
        }
    }
}

@Composable
private fun ProjectConnectCard(onContinue: () -> Unit, modifier: Modifier = Modifier) {
    var repository by remember { mutableStateOf("") }
    AgentCard(modifier, contentPadding = PaddingValues(AgentSpacing.Xl)) {
        Column(verticalArrangement = Arrangement.spacedBy(AgentSpacing.Lg)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("프로젝트 연결", style = MaterialTheme.typography.titleMedium)
                AgentBadge("민감 파일 제외", tone = AgentBadgeTone.Success, showDot = true)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(AgentSpacing.Xs), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    repository, { repository = it }, Modifier.weight(1f),
                    placeholder = { Text("https://github.com/owner/repository") }, singleLine = true,
                    shape = AgentRadius.Medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AgentPalette.Blue, unfocusedBorderColor = AgentPalette.Line,
                        focusedContainerColor = AgentPalette.Paper, unfocusedContainerColor = AgentPalette.Paper,
                    ),
                )
                AgentButton("분석", onContinue, style = AgentButtonStyle.Dark)
            }
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.weight(1f).height(1.dp).background(AgentPalette.SoftLine))
                Text("또는", Modifier.padding(horizontal = AgentSpacing.Sm), color = AgentPalette.Muted)
                Box(Modifier.weight(1f).height(1.dp).background(AgentPalette.SoftLine))
            }
            Surface(Modifier.fillMaxWidth().clickable(onClick = onContinue), color = Color.White, shape = AgentRadius.Medium, border = BorderStroke(1.dp, AgentPalette.Line)) {
                Row(Modifier.padding(AgentSpacing.Md), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(AgentSpacing.Sm)) {
                    Box(Modifier.size(44.dp).clip(AgentRadius.Medium).background(AgentPalette.Navy), contentAlignment = Alignment.Center) { Text("Kt", color = AgentPalette.Mint, fontWeight = FontWeight.Bold) }
                    Column(Modifier.weight(1f)) {
                        Text("Commerce Android", style = MaterialTheme.typography.titleMedium)
                        Text("Kotlin · Compose · 멀티모듈 샘플", color = AgentPalette.Muted, style = MaterialTheme.typography.bodyMedium)
                    }
                    Text("→", color = AgentPalette.Blue, style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }
}

@Composable
private fun DiagnosisScreen(onContinue: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(AgentSpacing.Xxl)) {
        PageHeader("AI readiness", "AI가 일하기엔 설명이 조금 부족해요.", "점수보다 중요한 것은 낮은 이유입니다. 모든 진단에 저장소 근거를 표시했어요.", "이 문제 개선하기", onContinue)
        BoxWithConstraints(Modifier.fillMaxWidth()) {
            if (maxWidth >= 780.dp) Row(horizontalArrangement = Arrangement.spacedBy(AgentSpacing.Lg)) {
                ScoreColumn(Modifier.width(340.dp)); FindingColumn(Modifier.weight(1f))
            } else Column(verticalArrangement = Arrangement.spacedBy(AgentSpacing.Lg)) {
                ScoreColumn(Modifier.fillMaxWidth()); FindingColumn(Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
private fun ScoreColumn(modifier: Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(AgentSpacing.Md)) {
        AgentScoreCard(38, Modifier.fillMaxWidth())
        AgentCard(Modifier.fillMaxWidth()) { Column(verticalArrangement = Arrangement.spacedBy(AgentSpacing.Md)) {
            listOf("프로젝트 이해" to 60, "아키텍처" to 25, "테스트 및 검증" to 35, "변경 안전성" to 40).forEach { (label, score) ->
                Column(verticalArrangement = Arrangement.spacedBy(AgentSpacing.Xs)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text(label); Text("$score", fontWeight = FontWeight.Bold) }
                    AgentProgressBar(score / 100f)
                }
            }
        } }
    }
}

@Composable
private fun FindingColumn(modifier: Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(AgentSpacing.Sm)) {
        FindingCard("01", "AI가 모듈별 역할을 알기 어려워요.", "책임과 의존 규칙을 설명하는 문서가 없습니다.", "settings.gradle.kts")
        FindingCard("02", "변경과 테스트가 연결되어 있지 않아요.", "어떤 변경에 어떤 테스트를 실행할지 정의되어 있지 않습니다.", "app/src/test · domain/user/src/test")
        FindingCard("03", "AI의 완료 조건이 없어요.", "빌드, 테스트, Diff 확인을 완료 조건으로 지정한 지침이 없습니다.", "AGENTS.md · CLAUDE.md 없음")
    }
}

@Composable
private fun FindingCard(index: String, title: String, description: String, evidence: String) {
    AgentCard(Modifier.fillMaxWidth()) {
        Row(horizontalArrangement = Arrangement.spacedBy(AgentSpacing.Sm), verticalAlignment = Alignment.Top) {
            Box(Modifier.size(32.dp).clip(AgentRadius.Small).background(AgentPalette.AmberSoft), contentAlignment = Alignment.Center) { Text(index, color = AgentPalette.Amber, style = MaterialTheme.typography.labelMedium) }
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(AgentSpacing.Xs)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text(title, Modifier.weight(1f), style = MaterialTheme.typography.titleMedium); AgentBadge("높은 확신", tone = AgentBadgeTone.Success) }
                Text(description, color = AgentPalette.Muted, style = MaterialTheme.typography.bodyMedium)
                AgentBadge("⌁ $evidence")
            }
        }
    }
}

@Composable
private fun ProblemsScreen(selected: List<Int>, onToggle: (Int) -> Unit, onContinue: () -> Unit) {
    val problems = listOf(
        Triple("AI가 잘못된 모듈을 수정해요.", "모듈 역할과 의존 방향을 작업 전에 확인하게 합니다.", "분석 결과 추천"),
        Triple("구현 후 테스트를 자주 빠뜨려요.", "변경한 영역에 맞는 테스트와 완료 조건을 연결합니다.", "분석 결과 추천"),
        Triple("프로젝트 구조를 매번 설명해야 해요.", "새 대화에서도 공통 구조와 명령을 확인하게 합니다.", "프로젝트 이해"),
        Triple("관련 없는 파일까지 수정해요.", "작업 범위와 기존 변경을 보호하는 원칙을 추가합니다.", "안전"),
    )
    Column(verticalArrangement = Arrangement.spacedBy(AgentSpacing.Xxl)) {
        SectionIntro("Your pain points", "실제로 어떤 점이 불편한가요?", "분석 결과와 관련 있는 문제를 먼저 골라두었어요. 원하는 항목만 남겨주세요.")
        BoxWithConstraints(Modifier.fillMaxWidth()) {
            if (maxWidth >= 720.dp) Column(verticalArrangement = Arrangement.spacedBy(AgentSpacing.Sm)) {
                problems.chunked(2).forEachIndexed { rowIndex, row -> Row(horizontalArrangement = Arrangement.spacedBy(AgentSpacing.Sm)) {
                    row.forEachIndexed { columnIndex, p -> val i = rowIndex * 2 + columnIndex; ProblemCard(p, i in selected, { onToggle(i) }, Modifier.weight(1f)) }
                } }
            } else Column(verticalArrangement = Arrangement.spacedBy(AgentSpacing.Sm)) { problems.forEachIndexed { i, p -> ProblemCard(p, i in selected, { onToggle(i) }, Modifier.fillMaxWidth()) } }
        }
        AgentCard(Modifier.fillMaxWidth()) { Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column { Text("${selected.size}개 문제 선택", style = MaterialTheme.typography.titleMedium); Text("지금 필요한 최소 설정을 추천합니다.", color = AgentPalette.Muted) }
            AgentButton("설정 추천받기", onContinue, enabled = selected.isNotEmpty())
        } }
    }
}

@Composable
private fun ProblemCard(p: Triple<String, String, String>, selected: Boolean, onClick: () -> Unit, modifier: Modifier) = AgentChoiceCard(p.first, p.second, selected, onClick, modifier, p.third)

@Composable
private fun RecommendationScreen(onContinue: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(AgentSpacing.Xxl)) {
        PageHeader("Minimum setup", "이 프로젝트에는 3개면 충분해요.", "항상 필요한 원칙은 짧게, 반복 작업은 필요할 때만 불러오도록 나눴습니다.", "설정 생성하기", onContinue)
        Column(verticalArrangement = Arrangement.spacedBy(AgentSpacing.Sm)) {
            RecommendationCard("MD", "프로젝트 안내서", "프로젝트 구조와 기본 안전 규칙을 모든 작업에서 확인합니다.", "AGENTS.md", "항상 적용")
            RecommendationCard("SK", "Clean Feature 구현 절차", "계층별 구현 순서와 금지된 의존성을 확인합니다.", "implement-clean-feature/SKILL.md", "기능 구현 시")
            RecommendationCard("SK", "변경 검증 절차", "관련 테스트를 찾고 실행 결과를 보고합니다.", "verify-change/SKILL.md", "코드 수정 후")
        }
        AgentCard(Modifier.fillMaxWidth(), AgentPalette.MintSoft, Color(0xFFBDE8CD)) { Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column { Text("설정 복잡도 · 적정", color = AgentPalette.Success, style = MaterialTheme.typography.titleMedium); Text("항상 적용 규칙 11개 · Skill 2개 · 충돌 0개", color = AgentPalette.Muted) }
            AgentBadge("과도한 설정 없음", tone = AgentBadgeTone.Success)
        } }
    }
}

@Composable
private fun RecommendationCard(symbol: String, title: String, description: String, file: String, timing: String) {
    AgentCard(Modifier.fillMaxWidth()) { Row(horizontalArrangement = Arrangement.spacedBy(AgentSpacing.Md), verticalAlignment = Alignment.Top) {
        Box(Modifier.size(44.dp).clip(AgentRadius.Medium).background(AgentPalette.BlueSoft), contentAlignment = Alignment.Center) { Text(symbol, color = AgentPalette.Blue, style = MaterialTheme.typography.labelMedium) }
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(AgentSpacing.Xs)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text(title, style = MaterialTheme.typography.titleMedium); AgentBadge("필수", tone = AgentBadgeTone.Info) }
            Text(description, color = AgentPalette.Muted); Row(horizontalArrangement = Arrangement.spacedBy(AgentSpacing.Xs)) { AgentBadge(file); AgentBadge(timing) }
        }
    } }
}

@Composable
private fun ReviewScreen(onContinue: () -> Unit) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("내용", "Diff", "생성 근거")
    val text = when (selectedTab) {
        1 -> "@@ 새 파일: AGENTS.md @@\n+ # 프로젝트 작업 가이드\n+ ## 프로젝트 구조\n+ - app: 애플리케이션 진입점\n+ - domain: 비즈니스 규칙\n+ ## 완료 조건\n+ - 관련 테스트를 실행한다."
        2 -> "생성 이유\n\nAI가 프로젝트 구조와 완료 조건을 확인할 공통 지침이 필요합니다.\n\n근거\n1. settings.gradle.kts에서 모듈 발견\n2. app/src/test에서 테스트 발견\n3. 기존 지침 파일 없음\n\n확신도: 높음"
        else -> "# 프로젝트 작업 가이드\n\n## 프로젝트 구조\n\n- app: 애플리케이션 진입점과 의존성 조립\n- domain: 비즈니스 규칙과 인터페이스\n- data: Repository 구현과 외부 데이터 접근\n\n## 작업 원칙\n\n- 관련된 파일만 수정한다.\n- domain은 Android 프레임워크에 의존하지 않는다.\n\n## 완료 조건\n\n- 관련 테스트를 실행한다.\n- 완료 전 git diff를 확인한다."
    }
    Column(verticalArrangement = Arrangement.spacedBy(AgentSpacing.Xxl)) {
        PageHeader("Review changes", "만들 내용을 먼저 확인하세요.", "프로젝트 코드는 건드리지 않고 AI 설정 파일 4개만 추가합니다.", "효과 검증하기", onContinue)
        Surface(shape = AgentRadius.Large, border = BorderStroke(1.dp, AgentPalette.Line), color = Color.White) {
            BoxWithConstraints(Modifier.fillMaxWidth().height(560.dp)) {
                if (maxWidth >= 720.dp) Row(Modifier.fillMaxSize()) { FileTree(Modifier.width(245.dp).fillMaxHeight()); EditorPanel(tabs, selectedTab, { selectedTab = it }, text, Modifier.weight(1f)) }
                else Column(Modifier.fillMaxSize()) { FileTree(Modifier.fillMaxWidth().height(170.dp)); EditorPanel(tabs, selectedTab, { selectedTab = it }, text, Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
private fun FileTree(modifier: Modifier) { Column(modifier.background(AgentPalette.Navy).padding(AgentSpacing.Md), verticalArrangement = Arrangement.spacedBy(AgentSpacing.Xs)) {
    Text("GENERATED FILES · 4", color = AgentPalette.OnDarkMuted, style = MaterialTheme.typography.labelMedium); Spacer(Modifier.height(AgentSpacing.Xs))
    listOf("▾ AGENTS.md", "▾ docs/ai/architecture.md", "▾ skills/implement-clean-feature/", "   skills/verify-change/").forEachIndexed { i, text ->
        Text(text, Modifier.fillMaxWidth().clip(AgentRadius.Small).background(if (i == 0) AgentPalette.NavyRaised else Color.Transparent).padding(AgentSpacing.Xs), color = if (i == 0) AgentPalette.Mint else AgentPalette.OnDarkMuted, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
    }
} }

@Composable
private fun EditorPanel(tabs: List<String>, selected: Int, onSelect: (Int) -> Unit, text: String, modifier: Modifier) { Column(modifier.background(Color.White)) {
    Row(Modifier.fillMaxWidth().padding(AgentSpacing.Sm), horizontalArrangement = Arrangement.spacedBy(AgentSpacing.Xs)) { tabs.forEachIndexed { i, label ->
        Surface(Modifier.clickable { onSelect(i) }, color = if (i == selected) AgentPalette.BlueSoft else Color.Transparent, shape = AgentRadius.Small) { Text(label, Modifier.padding(horizontal = AgentSpacing.Sm, vertical = AgentSpacing.Xs), color = if (i == selected) AgentPalette.Blue else AgentPalette.Muted, style = MaterialTheme.typography.labelLarge) }
    } }
    Box(Modifier.fillMaxWidth().height(1.dp).background(AgentPalette.SoftLine))
    Text(text, Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(AgentSpacing.Xl), color = AgentPalette.Ink, fontFamily = FontFamily.Monospace, fontSize = 13.sp, lineHeight = 23.sp)
} }

@Composable
private fun TestScreen(onContinue: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(AgentSpacing.Xxl)) {
        PageHeader("Agent setup test", "설정이 AI의 행동을 바꿨을까요?", "같은 작업과 같은 기준으로 적용 전후의 작업 계획을 비교했습니다.", "검증된 설정 적용하기", onContinue)
        Surface(color = AgentPalette.Navy, shape = AgentRadius.ExtraLarge) { Column(Modifier.padding(AgentSpacing.Xl), verticalArrangement = Arrangement.spacedBy(AgentSpacing.Lg)) {
            Surface(color = AgentPalette.NavyRaised, shape = AgentRadius.Medium, border = BorderStroke(1.dp, Color(0xFF3B4860))) { Column(Modifier.fillMaxWidth().padding(AgentSpacing.Md)) { Text("테스트 작업", color = AgentPalette.OnDarkMuted); Text("“사용자 프로필 조회 기능을 추가해 주세요.”", color = Color.White, style = MaterialTheme.typography.titleMedium) } }
            BoxWithConstraints(Modifier.fillMaxWidth()) { if (maxWidth >= 700.dp) Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(AgentSpacing.Lg)) { TestResult(false, Modifier.weight(1f)); Text("→", color = AgentPalette.Mint, fontSize = 34.sp); TestResult(true, Modifier.weight(1f)) } else Column(verticalArrangement = Arrangement.spacedBy(AgentSpacing.Sm)) { TestResult(false, Modifier.fillMaxWidth()); Text("↓", color = AgentPalette.Mint, modifier = Modifier.align(Alignment.CenterHorizontally)); TestResult(true, Modifier.fillMaxWidth()) } }
            Row(horizontalArrangement = Arrangement.spacedBy(AgentSpacing.Xs)) { listOf("모듈 선택" to "+45", "테스트 계획" to "+65", "범위 통제" to "+35", "안전성" to "+40").forEach { (l, v) -> DeltaCard(l, v, Modifier.weight(1f)) } }
        } }
    }
}

@Composable
private fun TestResult(after: Boolean, modifier: Modifier) { Surface(modifier, color = Color.White, shape = AgentRadius.Large, border = BorderStroke(if (after) 2.dp else 1.dp, if (after) AgentPalette.Mint else AgentPalette.Line)) { Column(Modifier.padding(AgentSpacing.Lg), verticalArrangement = Arrangement.spacedBy(AgentSpacing.Xs)) {
    Text(if (after) "설정 후" else "설정 전", color = AgentPalette.Muted); Text(if (after) "89" else "42", color = if (after) AgentPalette.Success else AgentPalette.Ink, fontSize = 48.sp, fontWeight = FontWeight.Bold)
    val items = if (after) listOf("domain에 인터페이스 작성", "data에 구현체 작성", "관련 모듈 테스트 선택", "변경 범위와 결과 보고") else listOf("app 모듈에 Repository 구현", "domain 계층 계획 없음", "테스트 실행 계획 없음", "전체 빌드만 제안")
    items.forEach { Text("${if (after) "✓" else "×"}  $it", color = if (after) AgentPalette.Success else AgentPalette.Muted, style = MaterialTheme.typography.bodyMedium) }
} } }

@Composable
private fun DeltaCard(label: String, value: String, modifier: Modifier) { Surface(modifier, color = AgentPalette.NavyRaised, shape = AgentRadius.Medium, border = BorderStroke(1.dp, Color(0xFF344159))) { Column(Modifier.padding(AgentSpacing.Sm)) { Text(label, color = AgentPalette.OnDarkMuted, style = MaterialTheme.typography.bodyMedium); Text(value, color = AgentPalette.Mint, style = MaterialTheme.typography.titleMedium) } } }

@Composable
private fun ApplyScreen(onReset: () -> Unit) { Column(Modifier.fillMaxWidth().padding(vertical = AgentSpacing.Xxxl), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(AgentSpacing.Lg)) {
    Box(Modifier.size(78.dp).clip(AgentRadius.ExtraLarge).background(AgentPalette.Mint), contentAlignment = Alignment.Center) { Text("✓", color = AgentPalette.Navy, fontSize = 32.sp, fontWeight = FontWeight.Bold) }
    Text("READY TO WORK", color = AgentPalette.Blue, style = MaterialTheme.typography.labelMedium)
    Text("AI의 첫 출근 준비가 끝났어요.", textAlign = TextAlign.Center, style = MaterialTheme.typography.headlineLarge)
    Text("이제 긴 프로젝트 설명 대신, 구현하고 싶은 기능만 말해보세요.", color = AgentPalette.Muted, textAlign = TextAlign.Center)
    AgentCard(Modifier.widthIn(max = 720.dp).fillMaxWidth()) { Column(verticalArrangement = Arrangement.spacedBy(AgentSpacing.Md)) {
        Text("Git patch로 안전하게 적용", style = MaterialTheme.typography.titleLarge); Text("먼저 충돌 여부를 확인한 뒤 같은 파일을 적용합니다.", color = AgentPalette.Muted)
        Surface(color = AgentPalette.Navy, shape = AgentRadius.Medium) { Text("git apply --check agent-setup.patch\ngit apply agent-setup.patch", Modifier.fillMaxWidth().padding(AgentSpacing.Md), color = Color.White, fontFamily = FontFamily.Monospace, lineHeight = 22.sp) }
        Row(horizontalArrangement = Arrangement.spacedBy(AgentSpacing.Xs)) { AgentBadge("기존 소스 변경 없음", tone = AgentBadgeTone.Success); AgentBadge("AI 설정 파일 4개", tone = AgentBadgeTone.Success) }
    } }
    Row(horizontalArrangement = Arrangement.spacedBy(AgentSpacing.Xs)) { AgentButton("Patch 다운로드", {}); AgentButton("처음부터 다시 보기", onReset, style = AgentButtonStyle.Secondary) }
} }
