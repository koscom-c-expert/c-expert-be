## c-expert Backend Repository
![logo](https://private-user-images.githubusercontent.com/58168528/409349047-9cfed1f6-294c-4b11-a2e1-5a2df09c931b.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3Mzg2NDc1NzAsIm5iZiI6MTczODY0NzI3MCwicGF0aCI6Ii81ODE2ODUyOC80MDkzNDkwNDctOWNmZWQxZjYtMjk0Yy00YjExLWEyZTEtNWEyZGYwOWM5MzFiLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNTAyMDQlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjUwMjA0VDA1MzQzMFomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTRlNTY1NzQ1ZDZlNzUxZGEwZWFlOTA5ZDJkODI2MTA5ODgzNmE5ZDRiMDU0ZTM4MWIwYjM2NTJhYjQ5ODQ5NTYmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0In0.gMOKPJiflz0pqYFiBkgN59HJAM-_uoRvTcHXI9I1a_k)
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
