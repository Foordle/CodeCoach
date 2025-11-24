# CodeCoach
우아한테크코스8기프리코스_4,5주차

## 📑 목차
- [a. 주제설명](#a-주제설명)
- [b. 기능목록](#b-기능목록)
- [c. 실행예시](#c-실행예시)
- [d. 문제 요구사항](#d-성장-포인트)
- [e. 개발계획 순서](#e-개발계획-순서)
- [f. 프로그램 실행 방법 (사용 가이드)](#f-프로그램-실행-방법-사용-가이드)
- [g. 프로그램 실행 영상 (YouTube)](#g-프로그램-실행-영상-(YouTube))
     
---
## a. 주제설명
해당 프로젝트의 주제는 작성한 코드에 대해 평가하고 피드백을 제공하는 교육툴의 개발이 목적이다.<br>

### 주제선정의 이유
이번 프리코스를 진행하면서 하나의 주제를 바탕으로 스스로 성장할 방향을 모색하는 과정이 신선했다.<br>
다만 한가지 아쉬운점이 있다면 내가 성장하고 있는 것에 대한 피드백이 아쉬웠다. 

예를들어 상호 리뷰를 통해 mvc패턴을 알게 되어 이를 적용해 보지만 초심자의 입장에서 내 코드에 맞게 적용이 되었는지 알기가 어려웠다(물론 정답은 없고 고민하는 과정 자체가 성장의 과정이긴 하다.)
이에 나는 내가 짠 코드를 LLM서비스에 넣어 평가를 받고 피드백을 받았었다.

이에 이번 프로젝트에서 이런 평가와 피드백을 조금더 편리하고 정확하게 할 수 있는 도구를 만들어 보고자 하였다. 

---
## b. 기능 목록
해당 프로그램이 수행하는 기능요구사항은 다음과 같다.

### 1. 평가 기준 선택
| 기능 | 설명 |
|---|---|
| 아키텍쳐패턴 | 평가 받을 아키텍처패턴을 선택한다. |
| 목적 | 코딩테스트, 유지보수고려 등 목적을 선택한다.|


### 2. 컴파일 기능
docker를 사용하고 gcc와 같은 컴파일러를 이용하여 해당 코드를 컴파일하고 결과를 확인할 수 있게 한다. 

| 기능 | 설명 |
|---|---|
| 언어선택 | C++, JAVA등과 같은 언어를 컴파일 하고 결과를 확인할 수 있다. |

### 3. 코드 평가, 피드백
Gemini, Chat GPT등의 API를 사용하여 해당 기능을 구현한다.

| 기능 | 설명 |
|---|---|
| 아키텍쳐패턴 | 선택한 아키텍쳐패턴을 준수하였는 평가하고 부족한 부분을 피드백한다. |
| 목적 | 선택한 목적에 가장 적합한 코드인지 평가한다. ex)코딩테스트 통과를 위한 가장 간결하고 제한 사항에 부합하는 코드인지|

### 4. 예외 처리
사용자가 잘못된 값을 입력할 경우 `IllegalArgumentException`을 발생시키고, `[ERROR]`로 시작하는 에러 메시지를 출력 후 해당 부분부터 입력을 다시 받는다.

| 구분 | 예외 상황 |
|---|---|
|  | |

---
## c. 실행예시
### **[Gemini API 호출 및 Docker 실행 결과 (Java 코드 입력 시)]**

1.  **Console Output (Docker 실행 결과):**
    ```
    SUCCESS: Hello from container!
    Java Sum: 30
    ```
2.  **AI Feedback (Gemini 평가 결과):**
    ```json
    {
      "score": 95,
      "feedback": "## 코드 품질 분석\n\n**1. 일반 피드백:** 변수명이 명확하고 코드가 간결합니다.\n\n**2. 상세 분석 (RSVC/유지보수성):** 이 코드는 단일 클래스이므로 아키텍처 패턴을 평가하기 어렵습니다. 다만, `10`과 `20`과 같은 **매직 넘버**를 상수로 분리하면 유지보수성이 향상됩니다."
    }
    ```
---
## d. 성장 포인트  💡
이번 프로젝트를 통해 성장하고 싶은 내용은
1. ***java, spring boot***을 이용한 웹개발 익숙해지기<br>
- 기존에도 진행한 적이있었지만 프리코스를 통해 성장한 부분(코드 메소드 사용법, mvc패턴, TTD식 개발, README를 개발 계획표로 사용하는 것, git commit coment 작성법)등 을 적용해 본다.

2. ***AI api***사용해 보기
- 앞으로는 ai를 사용하는 서비스는 필수적일 것이다. 이번 기회에 ai를 직접 러닝해서 사용하지는 못하더라도 LLM모델의 파인튜닝을 통해 원하는 결과를 얻는 방식에 대해 학습해 볼것이다.

3. ***docker***를 이용한 환경 설정
- 어떠한 시스템 환경에서도 프로그램을 일괄적으로 실행시키기 위해서는 docker를 이용한 환경설정이 필수적이다. 이번기회에 docker를 이용해서 컴파일러도 구현해 보고 웹프로젝트도 docker를 바탕으로 구현해 보면서 사용법을 익힌다.
---
## e. 개발계획 순서


개발순서는 다음과 같다.<br>
- [x] 웹 서비스의 기본 데이터 파이프라인 작성<br>
&darr;<br>
- [x] API를 이용한 AI 간이 평가 기능 구현<br>
 &darr;<br>
- [x] docker를 이용한 컴파일러 구현<br>
  &darr;<br>
- [x] 웹기반으로 업그레이드 or AI평가 성능 파인튜닝?<br>
  &darr;<br>
아래의 부분은 이후 평가가 끝난후 개선할 사항들
- [ ] 웹프로젝트 docker환경으로 이전<br>
    &darr;<br>
- [ ] 모든 프로젝트를 개인서버로 이전<br>
  &darr;<br>
- [ ] OAuth기반 로그인 기능 구현
  &darr;<br>
- [ ] 챗봇처럼 프로젝트 로그를 남겨서 내 코드의 개선 사항에 대해 종합적 평가, 그래프 그리기 기능 추가
---
## f. 프로그램 실행 방법 (사용 가이드)

본 프로젝트는 Spring Boot, Gradle, 그리고 Docker를 사용합니다. 프로그램을 실행하기 위해서는 반드시 **Docker Desktop**이 필요합니다.

### 1\. 필수 전제 조건 (Prerequisites)

- **Java 21 이상** (JDK)
- **Gradle** (IntelliJ IDEA 사용 시 자동 지원)
- **Docker Desktop** (반드시 실행 상태여야 함)
- **Gemini API Key** (유효한 API 키)

### 2\. 환경 설정 (API Key)

민감 정보인 Gemini API 키는 **`.env`** 파일을 통해 환경 변수로 주입합니다.

1.  프로젝트 루트 디렉토리에 **`.env`** 파일을 생성합니다.
2.  유효한 API 키를 다음 형식으로 저장합니다:
    ```bash
    GEMINI_API_KEY="AIzaSyA...[실제 유효 키]" 
    ```
### 📁 프로젝트 구조 및 환경 변수 설정

`.env` 파일은 프로젝트의 **최상위 루트 디렉토리**에 생성해야 합니다.

```text
CodeCoach/
├── .env                  <-- 📢 여기에 생성하세요 (GEMINI_API_KEY=...)
├── build.gradle
├── Dockerfile.runner
└── src/

------------ 좀더 자세히!

CodeCoach/
├── .env            <---- # 📢 필수: API 키 설정 파일 (프로젝트 루트에 위치)
├── .gitignore
├── build.gradle
├── settings.gradle
├── Dockerfile.runner     # Docker 컴파일러 이미지 빌드 파일
├── gradlew
├── gradlew.bat
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/CodeCoach/
│       │       ├── CodeCoachApplication.java
│       │       ├── controller/
│       │       ├── domain/
│       │       ├── repository/
│       │       └── service/
│       └── resources/
│           ├── static/
│           │   └── main_view.html
│           └── application.properties
├── temp_code_storage/    # (자동 생성) 코드 실행 임시 폴더
└── temp_repos/           # (자동 생성) Git Clone 임시 폴더
```

### 3\. Docker 컴파일러 이미지 빌드 (최초 1회)

코드 실행 환경인 `code-runner-env` 이미지를 빌드합니다.

```bash
# 프로젝트 루트 디렉토리에서 실행
docker build -f Dockerfile.runner -t code-runner-env .
```
확인: 이 명령이 완료되면 docker images 목록에 code-runner-env 이미지가 나타나야 합니다.

### 4\. Spring Boot 애플리케이션 실행
   A. IntelliJ IDEA를 사용하는 경우 (권장)
   1. 실행 구성 (Run Configuration) 설정:
        - CodeCoachApplication 실행 구성을 편집합니다. 
        - Environment Variables 섹션에 GEMINI_API_KEY=[유효한 키]가 설정되어 있는지 확인합니다.

   2. CodeCoachApplication.java 파일을 열고 **녹색 실행 버튼(Run)**을 클릭하여 서버를 구동합니다.

   B. Gradle 명령어를 사용하는 경우
   ```bash
   # 환경 변수를 설정한 터미널에서 실행
  # Windows CMD: set GEMINI_API_KEY="키"
  ./gradlew bootRun
   ```
### 5.\ 프로그램 사용 및 테스트
1. docker desktop을 실행한다.
2. CodeCoachApplication을 실행한다.
3. 접속: 웹 브라우저에서 http://localhost:8080/main_view.html 에 접속합니다.
4. 평가 실행: 코드 입력, 모델 선택 후 ▶ RUN & EVALUATE 버튼을 클릭합니다.
5. 결과 확인:
   - Console Output: Docker 컨테이너에서 컴파일 및 실행된 결과가 표시됩니다.
   - AI Feedback: Gemini API 호출 결과가 JSON 파싱되어 점수와 상세 피드백이 표시됩니다.
---
## g. 프로그램 실행 영상 (YouTube)

### 0. 실행 환경
- Docker Desktop이 실행되어 있어야 함
- 웹 브라우저에서 접속:  
  http://localhost:8080/main_view.html

[![CodeCoach Setting](http://img.youtube.com/vi/JNP7pG5D0Xk/0.jpg)](https://www.youtube.com/watch?v=JNP7pG5D0Xk)

---

### 1. 단일 코드 평가
코드를 직접 입력하여 Docker 기반으로 컴파일·실행한 뒤 AI 평가를 받는 과정 시연 영상입니다.

[![CodeCoach Single Code](http://img.youtube.com/vi/lM3cOb09-O8/0.jpg)](https://www.youtube.com/watch?v=lM3cOb09-O8)

---

### 2. GitHub 링크 평가
GitHub Repository URL과 브랜치를 입력하여 전체 프로젝트 구조를 분석·평가하는 기능 시연 영상입니다.

[![CodeCoach GitHub Evaluation](http://img.youtube.com/vi/IxunNgup_jE/0.jpg)](https://www.youtube.com/watch?v=IxunNgup_jE)
