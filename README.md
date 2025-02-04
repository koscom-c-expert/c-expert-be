## C-Expert Backend Repository
![mock-logo-on-black](https://github.com/user-attachments/assets/681c6097-3768-4c70-8633-da792643061d)


### 개요
Java version: 17
Spring Boot version: 3.4.2

### 배포 주소
http://121.141.60.109/

### git flow
- main: production 배포 브랜치
- develop: development 배포 브랜치
- feature/<상세 기능>: 세부 기능 구현 브랜치

feature -> develop -> main 으로 PR 및 머지

### API 테스트
Swagger를 사용한다.
`localhost:8080/swagger-ui/index.html`

### BE 아키텍처
![be](https://github.com/user-attachments/assets/dada1599-aa85-462d-840d-a8966f138df9)

배포 시, `application.yml`과 `application-dev.yml`을 참고하여 환경변수를 세팅한다.

### 기타
#### intelliJ 환경설정
- profile 설정
  - 서버 실행에 사용되는 Configuration > Edit... 에서 Active Profiles를 `local` or `dev` 로 설정
- open ai api-key 설정
  - 서버 실행에 사용되는 Configuration > Edit... 에서 Modify option > Environment Variables에 `OPENAI_API_KEY=<key값>`을 입력

#### H2 Database
1. localhost:8080/h2-console 접속
2. JDBC URL에 `jdbc:h2:mem:test` 입력 후 Connect
