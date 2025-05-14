# 일정 관리 시스템

Spring Boot를 활용한 일정 관리 API 서비스입니다. 이 서비스는 일정의 생성, 조회, 수정, 삭제 기능을 제공합니다.

## 기술 스택
* Java 17
* Spring Boot 3.x
* Spring JDBC
* MySQL
* Lombok

## API 명세서

### 1. 일정 생성
* **설명:** 새로운 일정을 등록합니다.
* **HTTP Method:** `POST`
* **Endpoint:** `/api/schedules`
* **Request Body (JSON):**

```json
{
  "todo": "일정 내용 작성하기",
  "writer": "홍길동",
  "password": "1234"
}
```

* **Success Response (201 Created):**

```json
{
  "id": 1,
  "todo": "일정 내용 작성하기",
  "writer": "홍길동",
  "createdAt": "2023-05-08T10:00:00",
  "modifiedAt": "2023-05-08T10:00:00"
}
```

### 2. 전체 일정 조회
* **설명:** 등록된 모든 일정을 조회합니다. 선택적으로 수정일(`date`) 또는 작성자명(`writer`)으로 필터링할 수 있습니다.
* **HTTP Method:** `GET`
* **Endpoint:** `/api/schedules`
* **Query Parameters (Optional):**
    * `date`: 조회할 수정일 (형식: `YYYY-MM-DD`, 예: `2023-05-08`)
    * `writer`: 조회할 작성자명 (예: `홍길동`)
* **Success Response (200 OK):**

```json
[
  {
    "id": 2,
    "todo": "저녁 식사 준비하기",
    "writer": "김철수",
    "createdAt": "2023-05-07T18:00:00",
    "modifiedAt": "2023-05-08T14:00:00"
  },
  {
    "id": 1,
    "todo": "일정 내용 작성하기",
    "writer": "홍길동",
    "createdAt": "2023-05-08T10:00:00",
    "modifiedAt": "2023-05-08T10:00:00"
  }
]
```

### 3. 선택 일정 조회
* **설명:** 특정 ID의 일정을 상세 조회합니다.
* **HTTP Method:** `GET`
* **Endpoint:** `/api/schedules/{id}`
    * **Path Variable:**
        * `id`: 조회할 일정의 고유 ID (숫자)
* **Success Response (200 OK):**

```json
{
  "id": 1,
  "todo": "일정 내용 작성하기",
  "writer": "홍길동",
  "createdAt": "2023-05-08T10:00:00",
  "modifiedAt": "2023-05-08T10:00:00"
}
```

* **Error Response (404 Not Found):**

```json
{
  "message": "일정을 찾을 수 없습니다. id: 1"
}
```

### 4. 선택 일정 수정
* **설명:** 특정 ID 일정의 `할일(todo)` 또는 `작성자명(writer)`을 수정합니다.
* **HTTP Method:** `PUT`
* **Endpoint:** `/api/schedules/{id}`
    * **Path Variable:**
        * `id`: 수정할 일정의 고유 ID (숫자)
* **Request Body (JSON):**

```json
{
  "todo": "수정된 일정 내용",
  "writer": "홍길동",
  "password": "1234"
}
```

* **Success Response (200 OK):**

```json
{
  "id": 1,
  "todo": "수정된 일정 내용",
  "writer": "홍길동",
  "createdAt": "2023-05-08T10:00:00",
  "modifiedAt": "2023-05-08T15:30:00"
}
```

* **Error Response (401 Unauthorized):**

```json
{
  "message": "비밀번호가 일치하지 않습니다."
}
```

### 5. 선택 일정 삭제
* **설명:** 특정 ID의 일정을 삭제합니다.
* **HTTP Method:** `DELETE`
* **Endpoint:** `/api/schedules/{id}`
    * **Path Variable:**
        * `id`: 삭제할 일정의 고유 ID (숫자)
* **Request Body (JSON):**

```json
{
  "password": "1234"
}
```

* **Success Response (204 No Content):**
    * 응답 본문 없음
* **Error Response (401 Unauthorized):**

```json
{
  "message": "비밀번호가 일치하지 않습니다."
}
```

## ERD (Entity Relationship Diagram)
### Schedule 테이블

| 컬럼명 | 데이터 타입 | 제약 조건 | 설명 |
|--------|------------|-----------|------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | 일정 고유 ID |
| todo | VARCHAR(200) | NOT NULL | 할일 내용 |
| writer | VARCHAR(50) | NOT NULL | 작성자명 |
| password | VARCHAR(100) | NOT NULL | 비밀번호 |
| created_at | DATETIME | DEFAULT CURRENT_TIMESTAMP | 생성일시 |
| modified_at | DATETIME | DEFAULT CURRENT_TIMESTAMP | 수정일시 |

## 데이터베이스 스키마 설정

```sql
CREATE TABLE schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    todo VARCHAR(200) NOT NULL,
    writer VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

## 구현 기능
### 레벨 1: 일정 생성 및 조회
* ✅ 일정 생성: 새로운 일정 등록 기능
* ✅ 전체 일정 조회: 등록된 모든 일정을 조회하는 기능 (날짜, 작성자 필터링 가능)
* ✅ 선택 일정 조회: 특정 ID의 일정을 상세 조회하는 기능

### 레벨 2: 일정 수정 및 삭제
* ✅ 선택 일정 수정: 특정 ID 일정의 내용과 작성자를 수정하는 기능 (비밀번호 확인 필요)
* ✅ 선택 일정 삭제: 특정 ID의 일정을 삭제하는 기능 (비밀번호 확인 필요)

### 진행중 및 예정 기능
* ⬜ 레벨 3: 연관 관계 설정
* ⬜ 레벨 4: 페이지네이션
* ⬜ 레벨 5: 예외 발생 처리
* ⬜ 레벨 6: null 체크 및 특정 패턴에 대한 검증 수행

## 프로젝트 설치 및 실행 방법
1. 프로젝트 복제

```bash
git clone https://github.com/balsohn/schedule.git
cd schedule
```

2. 데이터베이스 설정
* MySQL에 `sparta_schedule` 데이터베이스 생성
* `schedule.sql` 파일 실행하여 테이블 생성

3. 애플리케이션 실행

```bash
./gradlew bootRun
```

4. API 테스트
* 기본 URL: `http://localhost:8080/api/schedules`
* Postman 등의 도구를 사용하여 API 테스트