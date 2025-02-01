## c-expert Backend Repository
### 개요
Java version: 17

### git flow
- main: production 배포 브랜치
- develop: development 배포 브랜치
- feature/<상세 기능>: 세부 기능 구현 브랜치

feature -> develop -> main 으로 PR

### 환경설정
- profile 설정
  - `SPRING_PROFILES_ACTIVE` 환경변수를 `local`로 설정
  - intellij: 서버 실행시 Configuration > Edit... 에서 Active Profiles를 `local`로 설정

### 기타
#### H2 Database
1. localhost:8080/h2-console 접속
2. JDBC URL에 `jdbc:h2:mem:test` 입력 후 Connect
