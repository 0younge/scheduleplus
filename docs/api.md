# API 명세서

---

## 목차

- [일정 API](#일정-api)
- [유저 API](#유저-api)
- [댓글 API](#댓글-api)

---

## 일정 API

### 일정 추가

- **Method:** `POST`
- **URL:** `/schedules`

#### Request Body

| 필드 | 타입 | 설명 |
|------|------|------|
| title | String | 일정 제목 |
| content | String | 일정 내용 |

#### Response

없음 (`201 Created`)

---

### 일정 전체 조회

- **Method:** `GET`
- **URL:** `/schedules?pagenum={pagenum}&pagesize={pagesize}`

#### Query Parameters

| 파라미터 | 타입 | 설명 |
|----------|------|------|
| pagenum | Integer | 페이지 번호 |
| pagesize | Integer | 페이지 당 데이터 수 |

#### Request Body

없음

#### Response Body

| 필드 | 타입 | 설명 |
|------|------|------|
| title | String | 일정 제목 |
| content | String | 일정 내용 |
| commentNum | Integer | 댓글 수 |
| createdAt | DateTime | 생성일시 |
| modifiedAt | DateTime | 수정일시 |
| user.name | String | 작성자 이름 |

#### Response 예시

```json
[
  {
    "title": "회의 준비",
    "content": "3시 팀 회의 자료 준비",
    "commentNum": 3,
    "createdAt": "2024-01-01T10:00:00",
    "modifiedAt": "2024-01-01T12:00:00",
    "username": "홍길동"
  }
]
```

---

### 일정 단건 조회

- **Method:** `GET`
- **URL:** `/schedules/{scheduleId}`

#### Path Variables

| 파라미터 | 타입 | 설명 |
|----------|------|------|
| scheduleId | Long | 일정 ID |

#### Request Body

없음

#### Response Body

| 필드 | 타입 | 설명 |
|------|------|------|
| title | String | 일정 제목 |
| content | String | 일정 내용 |
| createdAt | DateTime | 생성일시 |
| modifiedAt | DateTime | 수정일시 |
| user.userId | Long | 유저 식별자 |


#### Response 예시

```json
{
  "title": "회의 준비",
  "content": "3시 팀 회의 자료 준비",
  "createdAt": "2024-01-01T10:00:00",
  "modifiedAt": "2024-01-01T12:00:00",
  "userId": 2
}
```

---

### 일정 수정

- **Method:** `PATCH`
- **URL:** `/schedules/{scheduleId}`

#### Path Variables

| 파라미터 | 타입 | 설명 |
|----------|------|------|
| scheduleId | Long | 일정 ID |

#### Request Body

| 필드 | 타입 | 설명 |
|------|------|------|
| title | String | 수정할 제목 |
| content | String | 수정할 내용 |

#### Response

없음 (`201 OK`)

---

### 일정 삭제

- **Method:** `DELETE`
- **URL:** `/schedules/{scheduleId}`

#### Path Variables

| 파라미터 | 타입 | 설명 |
|----------|------|------|
| scheduleId | Long | 일정 ID |

#### Request Body

없음

#### Response

없음 (`204 No Content`)

---

## 유저 API

### 유저 추가

- **Method:** `POST`
- **URL:** `/users`

#### Request Body

| 필드 | 타입 | 설명 |
|------|------|------|
| name | String | 유저 이름 |
| email | String | 이메일 |
| password | String | 비밀번호 |

#### Response

없음 (`201 Created`)

---

### 유저 전체 조회

- **Method:** `GET`
- **URL:** `/users`

#### Request Body

없음

#### Response Body

| 필드 | 타입 | 설명 |
|------|------|------|
| name | String | 유저 이름 |
| createdAt | DateTime | 생성일시 |
| modifiedAt | DateTime | 수정일시 |

#### Response 예시

```json
[
  {
    "name": "홍길동",
    "createdAt": "2024-01-01T09:00:00",
    "modifiedAt": "2024-01-01T09:00:00"
  }
]
```

---

### 유저 단건 조회

- **Method:** `GET`
- **URL:** `/users/{userId}`

#### Path Variables

| 파라미터 | 타입 | 설명 |
|----------|------|------|
| userId | Long | 유저 ID |

#### Request Body

없음

#### Response Body

| 필드 | 타입 | 설명 |
|------|------|------|
| name | String | 유저 이름 |
| email | String | 이메일 |
| createdAt | DateTime | 생성일시 |
| modifiedAt | DateTime | 수정일시 |

#### Response 예시

```json
{
  "name": "홍길동",
  "email": "hong@example.com",
  "createdAt": "2024-01-01T09:00:00",
  "modifiedAt": "2024-01-01T09:00:00"
}
```

---

### 유저 수정

- **Method:** `PATCH`
- **URL:** `/users/{userId}`

#### Path Variables

| 파라미터 | 타입 | 설명 |
|----------|------|------|
| userId | Long | 유저 ID |

#### Request Body

| 필드 | 타입 | 설명 |
|------|------|------|
| name | String | 수정할 이름 |
| email | String | 수정할 이메일 |

#### Response

없음 (`200 OK`)

---

### 유저 삭제

- **Method:** `DELETE`
- **URL:** `/users/{userId}`

#### Path Variables

| 파라미터 | 타입 | 설명 |
|----------|------|------|
| userId | Long | 유저 ID |

#### Request Body

없음

#### Response

없음 (`204 No Content`)

---

## 댓글 API

### 댓글 추가

- **Method:** `POST`
- **URL:** `/schedules/{scheduleId}/comments`

#### Path Variables

| 파라미터 | 타입 | 설명 |
|----------|------|------|
| scheduleId | Long | 일정 ID |

#### Request Body

| 필드 | 타입 | 설명 |
|------|------|------|
| content | String | 댓글 내용 |

#### Response

없음 (`201 Created`)

---

### 댓글 전체 조회

- **Method:** `GET`
- **URL:** `/schedules/{scheduleId}/comments`

#### Request Body

없음

#### Response Body

| 필드 | 타입 | 설명 |
|------|------|------|
| user.userId | Long | 유저 식별자 |
| content | String | 내용 |
| createdAt | DateTime | 생성일시 |
| modifiedAt | DateTime | 수정일시 |

#### Response 예시

```json
[
  {
    "userId": 3,
    "content": "댓글 내용",
    "createdAt": "2024-01-01T09:00:00",
    "modifiedAt": "2024-01-01T09:00:00"
  }
]
```

---

### 댓글 단건 조회

- **Method:** `GET`
- **URL:** `/schedules/{scheduleId}/comments/{commentsId}`

#### Path Variables

| 파라미터 | 타입 | 설명 |
|----------|------|------|
| scheduleId | Long | 일정 ID |
| commentsId | Long | 댓글 ID |

#### Request Body

없음

#### Response Body

| 필드 | 타입 | 설명 |
|------|------|------|
| user.userId | Long | 유저 식별자 |
| content | String | 댓글 내용 |
| createdAt | DateTime | 생성일시 |
| modifiedAt | DateTime | 수정일시 |


#### Response 예시

```json
{
  "userId": 3,
  "content": "3시 팀 회의 자료 준비",
  "createdAt": "2024-01-01T10:00:00",
  "modifiedAt": "2024-01-01T12:00:00"
}
```

---
### 댓글 수정

- **Method:** `PATCH`
- **URL:** `/schedules/{scheduleId}/comments/{commentId}`

#### Path Variables

| 파라미터 | 타입 | 설명 |
|----------|------|------|
| scheduleId | Long | 일정 ID |
| commentId | Long | 댓글 ID |

#### Request Body

| 필드 | 타입 | 설명 |
|------|------|------|
| password | String | 비밀번호 확인 |
| content | String | 수정할 댓글 내용 |

#### Response

없음 (`200 OK`)

---

### 댓글 삭제

- **Method:** `DELETE`
- **URL:** `/schedules/{scheduleId}/comments/{commentId}`

#### Path Variables

| 파라미터 | 타입 | 설명 |
|----------|------|------|
| scheduleId | Long | 일정 ID |
| commentId | Long | 댓글 ID |

#### Request Body

없음

#### Response

없음 (`204 No Content`)

---
