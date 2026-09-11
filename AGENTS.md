# AIHelper 프로젝트 가이드

## 프로젝트 설명

AIHelper는 Kotlin Multiplatform 기반의 AI 도우미 서비스입니다. 하나의 코드베이스에서 Compose Multiplatform Web UI와 Ktor 서버를 개발하며, 여러 플랫폼에서 재사용할 수 있는 비즈니스 로직은 공통 모듈에 둡니다.

현재 프로젝트는 초기 구성 단계입니다. 구현되지 않은 제품 요구사항이나 동작을 추측하지 말고, 불명확한 핵심 요구사항은 사용자에게 확인한 뒤 구현합니다.

### 기술 구성

- 언어: Kotlin
- 클라이언트: Compose Multiplatform (Kotlin/JS, Kotlin/Wasm)
- 서버: Ktor + Netty
- 빌드: Gradle Kotlin DSL
- 공통 코드: Kotlin Multiplatform

### 현재 모듈

- `app:webApp`: Web 애플리케이션의 진입점
- `app:shared`: 공유 UI와 프레젠테이션 로직
- `core`: 플랫폼에 독립적인 공통 비즈니스 로직
- `designsystem`: 공유 디자인 토큰, 테마와 재사용 가능한 Compose UI 컴포넌트
- `server`: Ktor 서버와 외부 요청을 처리하는 진입점

## 아키텍처 원칙

모든 신규 기능과 구조 변경에는 Clean Architecture를 적용합니다. 핵심 규칙은 의존성이 항상 바깥 계층에서 안쪽 계층으로 향해야 한다는 것입니다.

```text
Framework / UI / Server -> Interface Adapters -> Application -> Domain
```

안쪽 계층은 바깥 계층의 구현을 알면 안 됩니다. Domain과 Application 계층에서 Compose, Ktor, 브라우저 API, 데이터베이스, 네트워크 SDK 같은 프레임워크 세부사항을 직접 참조하지 않습니다.

### 계층별 책임

1. **Domain**
   - 엔티티, 값 객체, 도메인 규칙을 포함합니다.
   - 플랫폼과 프레임워크에 독립적인 순수 Kotlin으로 작성합니다.
   - 다른 계층에 의존하지 않습니다.

2. **Application**
   - 유스케이스와 애플리케이션 흐름을 정의합니다.
   - 외부 기능이 필요하면 인터페이스(port)를 선언합니다.
   - Domain에만 의존합니다.

3. **Interface Adapters**
   - Repository 구현, API DTO 변환, Presenter 또는 ViewModel 등 계층 간 변환을 담당합니다.
   - 외부 데이터 모델을 Domain 모델로 변환하며, 외부 모델이 안쪽 계층으로 유출되지 않게 합니다.

4. **Framework / Delivery**
   - Compose UI, Ktor route, 브라우저 API, 데이터베이스 및 외부 AI SDK 설정을 포함합니다.
   - 비즈니스 규칙을 두지 않고 Application의 유스케이스를 호출합니다.

### 모듈 및 의존성 규칙

- 공유 가능한 Domain/Application 코드는 `core`에 둡니다.
- Compose 화면과 UI 상태 표현은 `app:shared`에 둡니다.
- 브라우저별 구현과 Web 진입점은 각각 플랫폼 소스셋과 `app:webApp`에 둡니다.
- HTTP 라우팅과 서버 프레임워크 코드는 `server`에 둡니다.
- 외부 AI 서비스, 저장소, 네트워크 클라이언트는 인터페이스 뒤에 숨기고 생성자 주입으로 연결합니다.
- 순환 의존성을 만들지 않습니다.
- 단순한 기능에 불필요한 추상화를 추가하지 않되, 계층 경계와 의존성 역전은 지킵니다.

## 구현 규칙

- 패키지는 기능을 중심으로 구성하고, 각 기능 안에서 `domain`, `application`, `data`, `presentation` 경계를 명확히 합니다.
- UI와 Ktor route에서 직접 데이터 접근이나 핵심 비즈니스 판단을 하지 않습니다.
- DTO, 영속성 모델, UI 모델과 Domain 모델의 역할을 구분합니다.
- 상태와 의존성은 명시적으로 전달하며 전역 가변 상태를 피합니다.
- 새 유스케이스와 도메인 규칙에는 단위 테스트를 작성합니다.
- 변경 후 영향받는 모듈의 테스트 또는 컴파일 검사를 실행합니다.
- 기존 코드 스타일과 Kotlin 공식 코딩 컨벤션을 따릅니다.

## LLM Wiki

프로젝트의 문맥과 의사결정을 지속적으로 축적하기 위해 `docs` 디렉터리를 LLM Wiki로 사용합니다.

### 디렉터리 구조

- `docs/raw`: 회의 메모, 요구사항, 조사 결과, 외부 문서 발췌 등 가공하지 않은 원본 자료
- `docs/wiki`: 원본 자료와 코드 분석을 바탕으로 정리한 프로젝트 지식 문서

### 관리 규칙

- 원본 자료는 의미를 바꾸어 편집하지 않고 `docs/raw`에 보존합니다.
- 정리된 지식은 주제별 Markdown 문서로 `docs/wiki`에 작성합니다.
- Wiki 문서에는 관련 원본의 상대 경로를 출처로 남깁니다.
- 확인된 사실과 추정 또는 제안 내용을 명확히 구분합니다.
- 코드와 문서가 충돌하면 현재 동작을 코드로 확인하고 Wiki를 갱신합니다.
- 기능, 아키텍처 또는 중요한 의사결정이 변경되면 관련 Wiki 문서도 함께 갱신합니다.
- 비밀키, 토큰, 개인정보 및 공개하면 안 되는 원문은 문서에 저장하지 않습니다.
- 새 문서를 추가하면 `docs/wiki/README.md`의 문서 목록도 갱신합니다.

## 주요 명령어

- 서버 실행: `./gradlew :server:run`
- Web(Wasm) 실행: `./gradlew :app:webApp:wasmJsBrowserDevelopmentRun`
- Web(JS) 실행: `./gradlew :app:webApp:jsBrowserDevelopmentRun`
- 전체 검사: `./gradlew check`
