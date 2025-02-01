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
  - intellij: 서버 실행에 사용되는 Configuration > Edit... 에서 Active Profiles를 `local`로 설정
- open ai api-key 설정
  - intellij: 서버 실행에 사용되는 Configuration > Edit... 에서 Modify option > Environment Variables에 `OPENAI_API_KEY=<key값>`을 입력

### API
#### /api/v1/classification POST
request:
```json
{
  "stockCategories": [
        "반도체", "반도체-파운더리", "기타"

  ],
  "stocks": [
        "삼성", "엔비디아", "삼양"
  ]
}
```

response:
```json
{
  "status": "success",
  "message": null,
  "data": {
    "stockCategories": [
      {
        "id": 0,
        "name": "반도체"
      },
      {
        "id": 1,
        "name": "반도체-파운더리"
      },
      {
        "id": 2,
        "name": "기타"
      }
    ],
    "stocks": [
      {
        "name": "삼성",
        "categoryId": 1
      },
      {
        "name": "엔비디아",
        "categoryId": 0
      },
      {
        "name": "삼양",
        "categoryId": 2
      }
    ]
  }
}
```

### 기타
#### Swagger
`localhost:8080/swagger-ui/index.html`
#### H2 Database
1. localhost:8080/h2-console 접속
2. JDBC URL에 `jdbc:h2:mem:test` 입력 후 Connect
