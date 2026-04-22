# 일정 관리 API

일정(Schedule), 유저(User), 댓글(Comment) CRUD 기능을 제공하는 RESTful API 서버입니다.

---

## 기술 스택

- Java / Spring Boot
- Spring MVC
- JPA / Hibernate
- MySQL
- Session 기반 인증

---

## Auth패키지의 의미

user패키지와 같은 엔티티를 공유하며 URL도 동일합니다

- user에서 처리하는게 맞다고 생각하지만 기능단위로 분류하고 싶어 패키지를 분리했습니다

---

## common패키지의 의미

공통적으로 사용하거나 전체 패키지에서 사용되는 클래스들을 모아두었습니다

- BaseController: 세션 여부를 확인하는 메서드가 전체 컨트롤러에서 중복되어 추상화를 통해 구현하였습니다

---

## API 목록

[api.md](docs/api.md)


---

## ERD

![ERD](https://velog.velcdn.com/images/dudgus0808/post/1ea0c1d6-f5d0-4388-92c3-56d7632e2b72/image.png)

---