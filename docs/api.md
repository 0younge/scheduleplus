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

없음

#### 상태코드
(`201 Created`)

#### Error
| 에러코드 | 상황 | message |
|------|------|------|
| 401 | 비로그인시 | 로그인이 필요한 작업입니다. |
| 404 | 유저가 서버에 없을시 | 존재하지 않는 유저입니다. |
| 400 | 제목 내용이 없을시 | 제목은 공백을 허용하지 않습니다. |
| 400 | 제목이 30자 이상일시 | 제목은 30자 이내로 작성해야 합니다. |
| 400 | 내용이 없을시 | 내용은 공백을 허용하지 않습니다. |
| 400 | 내용이 100자 이상일시 | 내용은 100자 이내로 작성해야 합니다. |


---

### 일정 전체 조회

- **Method:** `GET`
- **URL:** `/schedules?pagenum={pagenum}&pagesize={pagesize}`

#### Query Parameters

| 파라미터 | 타입 | 설명 |
|----------|------|------|
| pagenum | int | 페이지 번호 |
| pagesize | int | 페이지 당 데이터 수 |

#### Request Body

없음

#### Response Body

| 필드 | 타입 | 설명 |
|------|------|------|
| title | String | 일정 제목 |
| content | String | 일정 내용 |
| commentNum | Long | 댓글 수 |
| createdAt | DateTime | 생성일시 |
| modifiedAt | DateTime | 수정일시 |
| user.name | String | 작성자 |
| currentPage | int | 현재 페이지 |
| totalPages | int | 총 페이지 수 |
| totalElements | int | 총 요소 |

#### Response 예시

```json
{
    "schedules": [
        {
            "title": "제목",
            "content": "asdfasdf",
            "commentCount": 0,
            "createdAt": "2026-04-21T20:11:41.82595",
            "modifiedAt": "2026-04-21T20:11:41.82595",
            "userName": "작성자1"
        }
    ],
    "currentPage": 0,
    "totalPages": 1,
    "totalElements": 1
}
```
#### 상태코드
(`200 Ok`)

#### Error
없음(일정 없을때 빈 리스트 반환)

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
| commentCount | Long | 댓글 수 |
| createdAt | DateTime | 생성일시 |
| modifiedAt | DateTime | 수정일시 |
| user.name | String | 작성자 |


#### Response 예시

```json
{
    "title": "제목",
    "content": "asdfasdf",
    "commentCount": 0,
    "createdAt": "2026-04-21T20:11:41.82595",
    "modifiedAt": "2026-04-21T20:11:41.82595",
    "userName": "작성자1"
}
```
#### 상태코드
(`200 Ok`)

#### Error
| 에러코드 | 상황 | message |
|------|------|------|
| 404 | 일정이 없을시 | 없는 일정입니다. |

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

없음

#### 상태코드

(`200 OK`)

#### Error
| 에러코드 | 상황 | message |
|------|------|------|
| 401 | 비로그인시 | 로그인이 필요한 작업입니다. |
| 403 | 작성자와 로그인유저가 다를시 | 작성자가 일치하지 않습니다. |
| 400 | 제목 내용이 없을시 | 제목은 공백을 허용하지 않습니다. |
| 400 | 제목이 30자 이상일시 | 제목은 30자 이내로 작성해야 합니다. |
| 400 | 내용이 없을시 | 내용은 공백을 허용하지 않습니다. |
| 400 | 내용이 100자 이상일시 | 내용은 100자 이내로 작성해야 합니다. |

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

없음

#### 상태코드

(`204 No Content`)

#### Error
| 에러코드 | 상황 | message |
|------|------|------|
| 401 | 비로그인시 | 로그인이 필요한 작업입니다. |
| 403 | 작성자와 로그인유저가 다를시 | 작성자가 일치하지 않습니다. |

---

## 유저 API

### 회원가입

- **Method:** `POST`
- **URL:** `/users`

#### Request Body

| 필드 | 타입 | 설명 |
|------|------|------|
| name | String | 유저 이름 |
| email | String | 이메일 |
| password | String | 비밀번호 |

#### Response

없음

#### 상태코드

(`201 Created`)

#### Error
| 에러코드 | 상황 | message |
|------|------|------|
| 400 | 이름 내용이 없을시 | 이름은 공백을 허용하지 않습니다. |
| 400 | 이름이 5자 초과일시 | 이름은 5글자 이내로 작성해야 합니다. |
| 400 | 메일 내용이 없을시 | 메일은 공백을 허용하지 않습니다. |
| 400 | 메일 형식이 아닐시 | 메일 형식을 지켜야 합니다. |
| 400 | 비밀번호가 없을시 | 이름은 공백을 허용하지 않습니다. |
| 400 | 비밀번호가 8자 미만일시 | 비밀번호는 최소 8자 이상이여야 합니다. |

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

#### 상태코드

(`200 Ok`)
#### Error

없음(유저 없을때 빈 리스트 반환)

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
#### 상태코드

(`200 Ok`)

#### Error
| 에러코드 | 상황 | message |
|------|------|------|
| 404 | 유저가 없을시 | 없는 유저입니다. |

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

없음

#### 상태코드

(`200 OK`)

#### Error
| 에러코드 | 상황 | message |
|------|------|------|
| 401 | 비로그인시 | 로그인이 필요한 작업입니다. |
| 403 | 작성자와 로그인유저가 다를시 | 작성자가 일치하지 않습니다. |
| 400 | 이름 내용이 없을시 | 이름은 공백을 허용하지 않습니다. |
| 400 | 이름이 5자 초과일시 | 이름은 5글자 이내로 작성해야 합니다. |
| 400 | 메일 내용이 없을시 | 메일은 공백을 허용하지 않습니다. |
| 400 | 메일 형식이 아닐시 | 메일 형식을 지켜야 합니다. |

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

없음

#### 상태코드

(`204 No Content`)

#### Error
| 에러코드 | 상황 | message |
|------|------|------|
| 401 | 비로그인시 | 로그인이 필요한 작업입니다. |
| 403 | 작성자와 로그인유저가 다를시 | 작성자가 일치하지 않습니다. |

---

### 로그인

- **Method:** `POST`
- **URL:** `/users/login`

#### Request Body

| 필드 | 타입 | 설명 |
|------|------|------|
| email | String | 이메일 |
| password | String | 비밀번호 |

#### Response

없음

#### 상태코드

(`200 Ok`)

#### Error
| 에러코드 | 상황 | message |
|------|------|------|
| 400 | 메일 내용이 없을시 | 메일은 공백을 허용하지 않습니다. |
| 400 | 메일 형식이 아닐시 | 메일 형식을 지켜야 합니다. |
| 400 | 비밀번호가 없을시 | 이름은 공백을 허용하지 않습니다. |
| 400 | 비밀번호가 8자 미만일시 | 비밀번호는 최소 8자 이상이여야 합니다. |
| 400 | 메일과 비밀번호가 일치하지 않을시 | 요청 오류: 비밀번호가 일치하지 않습니다. |
| 404 | 메일이 서버에 없을시 | 없는 유저입니다. |

---

### 로그아웃

- **Method:** `POST`
- **URL:** `/users/logout`

#### Request Body

없음

#### Response

없음

#### 상태코드

(`200 Ok`)


#### Error
| 에러코드 | 상황 | message |
|------|------|------|
| 401 | 비로그인시 | 로그인이 필요한 작업입니다. |


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

없음

#### 상태코드

(`201 Created`)

#### Error
| 에러코드 | 상황 | message |
|------|------|------|
| 401 | 비로그인시 | 로그인이 필요한 작업입니다. |
| 404 | 유저가 서버에 없을시 | 존재하지 않는 유저입니다. |
| 400 | 내용이 없을시 | 내용은 공백을 허용하지 않습니다. |
| 400 | 내용이 5자 초과일시 | 내용은 100글자 이내로 작성해야 합니다. |

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
| schedule.scheduleId | Long | 일정 식별자 |
| content | String | 내용 |
| createdAt | DateTime | 생성일시 |
| modifiedAt | DateTime | 수정일시 |

#### Response 예시

```json
[
    {
        "userId": 1,
        "scheduleId": 1,
        "content": "asdfasdf",
        "createdAt": "2026-04-21T20:25:04.460879",
        "modifiedAt": "2026-04-21T20:25:04.460879"
    }
]
```
#### 상태코드

(`200 Ok`)
#### Error

없음(일정 없을때 빈 리스트 반환)

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
| schedule.scheduleId | Long | 일정 식별자 |
| content | String | 댓글 내용 |
| createdAt | DateTime | 생성일시 |
| modifiedAt | DateTime | 수정일시 |


#### Response 예시

```json
{
    "userId": 1,
    "scheduleId": 1,
    "content": "asdfasdf",
    "createdAt": "2026-04-21T20:25:04.460879",
    "modifiedAt": "2026-04-21T20:25:04.460879"
}
```
#### 상태코드

(`200 Ok`)

#### Error
| 에러코드 | 상황 | message |
|------|------|------|
| 404 | 댓글이 없을시 | 없는 댓글입니다. |

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

없음

#### 상태코드

(`200 OK`)

#### Error
| 에러코드 | 상황 | message |
|------|------|------|
| 401 | 비로그인시 | 로그인이 필요한 작업입니다. |
| 403 | 작성자와 로그인유저가 다를시 | 작성자가 일치하지 않습니다. |
| 400 | 내용이 없을시 | 내용은 공백을 허용하지 않습니다. |


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

없음

#### 상태코드

(`204 No Content`)

#### Error
| 에러코드 | 상황 | message |
|------|------|------|
| 401 | 비로그인시 | 로그인이 필요한 작업입니다. |
| 403 | 작성자와 로그인유저가 다를시 | 작성자가 일치하지 않습니다. |

---

# ERD
![](https://velog.velcdn.com/images/dudgus0808/post/1ea0c1d6-f5d0-4388-92c3-56d7632e2b72/image.png)
