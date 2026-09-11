# Agent Setup Builder 디자인시스템

## 요약

Agent Setup Builder의 시각 언어와 공통 UI를 `designsystem` Kotlin Multiplatform 모듈에서 관리한다. 제품 화면은 이 모듈의 토큰과 컴포넌트를 사용하며, 기능 및 화면별 코드에서 임의의 색상과 크기를 추가하지 않는 것을 기본 원칙으로 한다.

## 시각 원칙

- 개발 도구에 어울리는 짙은 Navy 프레임과 밝은 작업 영역을 사용한다.
- Blue는 주요 행동과 정보 강조, Mint는 완료와 안전 상태에 사용한다.
- 결과보다 근거가 먼저 읽히도록 카드 계층과 보조 텍스트 대비를 유지한다.
- 본문은 최소 14sp, 주요 본문은 16sp 이상으로 제공한다.
- 데스크톱에서는 단계 사이드바, 좁은 화면에서는 가로 단계 표시를 사용한다.

## 토큰

- `AgentPalette`: 브랜드 및 의미 기반 색상
- `AgentSpacing`: 4dp 기반 간격 체계
- `AgentRadius`: 컴포넌트 모서리 체계
- `AgentSize`: 헤더, 사이드바와 컨트롤 기준 크기
- `AgentTheme`: Material 3 색상과 타이포그래피 연결

## 공통 컴포넌트

- `AgentButton`: Primary, Secondary, Dark, Ghost 행동
- `AgentCard`: 기본 표면과 테두리
- `AgentBadge`: Neutral, Info, Success, Warning, Dark 상태
- `AgentChoiceCard`: 문제 선택 카드
- `AgentProgressBar`: 준비도 및 진행 상태
- `AgentScoreCard`: AI 협업 준비도 요약
- `AgentStepRail`: 전체 설정 흐름 단계 표시

## 관련 코드

- `designsystem/src/commonMain/kotlin/com/example/aihelper/designsystem`
- `app/shared/src/commonMain/kotlin/com/example/aihelper/App.kt`
- `prototype/index.html`

## 출처

- 사용자가 승인한 1차 HTML 프로토타입: `prototype/index.html`

## 미결 사항

- 실제 브랜드 폰트 확정 및 웹 폰트 리소스 구성
- 키보드 포커스와 스크린리더 사용성 검증
- 색상 대비 자동 검사와 컴포넌트 스냅샷 테스트
