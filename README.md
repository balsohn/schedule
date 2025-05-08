# 일정 관리 애플리케이션 API 명세서

## 1. 개요

본 문서는 일정 관리 애플리케이션의 API 사용 방법을 정의합니다.
모든 요청과 응답의 본문(body)은 JSON 형식을 사용합니다.
응답으로 반환되는 일정 정보에는 `비밀번호`가 포함되지 않습니다.

## 2. API 목록

### 2.1. 일정 생성

-   **설명:** 새로운 일정을 등록합니다.
-   **HTTP Method:** `POST`
-   **Endpoint:** `/schedules`
-   **Request Body (JSON):**
    ```json
    {
      "task": "API 명세서 작성 완료하기",
      "assignee": "개발자A",
      "password": "securePassword123"
    }
    ```
-   **Success Response (201 Created):**
    -   **Body (JSON):**
        ```json
        {
          "id": 1,
          "task": "API 명세서 작성 완료하기",
          "assignee": "개발자A",
          "createdAt": "2024-05-07T13:00:00",
          "modifiedAt": "2024-05-07T13:00:00"
        }
        ```

### 2.2. 전체 일정 조회

-   **설명:** 등록된 모든 일정을 조회합니다. 선택적으로 수정일(`modifiedAt`) 또는 작성자명(`assignee`)으로 필터링할 수 있습니다. 일정은 `수정일` 기준 내림차순으로 정렬됩니다.
-   **HTTP Method:** `GET`
-   **Endpoint:** `/schedules`
-   **Query Parameters (Optional):**
    -   `modifiedAt`: 조회할 수정일 (형식: `YYYY-MM-DD`, 예: `2024-05-07`)
    -   `assignee`: 조회할 작성자명 (예: `개발자A`)
-   **Success Response (200 OK):**
    -   **Body (JSON):**
        ```json
        [
          {
            "id": 2,
            "task": "저녁 식사 메뉴 정하기",
            "assignee": "개발자B",
            "createdAt": "2024-05-06T18:00:00",
            "modifiedAt": "2024-05-07T14:00:00"
          },
          {
            "id": 1,
            "task": "API 명세서 작성 완료하기",
            "assignee": "개발자A",
            "createdAt": "2024-05-07T13:00:00",
            "modifiedAt": "2024-05-07T13:00:00"
          }
        ]
        ```
    -   필터링 조건에 맞는 일정이 없거나, 등록된 일정이 없을 경우 빈 배열 `[]`을 반환합니다.

### 2.3. 선택 일정 조회

-   **설명:** 특정 ID의 일정을 상세 조회합니다.
-   **HTTP Method:** `GET`
-   **Endpoint:** `/schedules/{id}`
    -   **Path Variable:**
        -   `id`: 조회할 일정의 고유 ID (숫자)
-   **Success Response (200 OK):**
    -   **Body (JSON):**
        ```json
        {
          "id": 1,
          "task": "API 명세서 작성 완료하기",
          "assignee": "개발자A",
          "createdAt": "2024-05-07T13:00:00",
          "modifiedAt": "2024-05-07T13:00:00"
        }
        ```
-   **Error Response (404 Not Found):**
    -   해당 ID의 일정이 존재하지 않을 경우.
    -   **Body (JSON):**
        ```json
        {
          "message": "선택한 일정을 찾을 수 없습니다."
        }
        ```

### 2.4. 선택 일정 수정

-   **설명:** 특정 ID 일정의 `할일(task)` 또는 `작성자명(assignee)`을 수정합니다. 수정을 위해서는 해당 일정의 `비밀번호`를 함께 전달해야 합니다. `작성일`은 변경되지 않으며, `수정일`은 현재 시간으로 업데이트됩니다.
-   **HTTP Method:** `PUT`
-   **Endpoint:** `/schedules/{id}`
    -   **Path Variable:**
        -   `id`: 수정할 일정의 고유 ID (숫자)
-   **Request Body (JSON):**
    -   `task` 또는 `assignee` 중 하나 이상을 포함해야 합니다.
    ```json
    {
      "task": "수정된 할일 내용",
      "assignee": "변경된 담당자",
      "password": "securePassword123"
    }
    ```
-   **Success Response (200 OK):**
    -   **Body (JSON):**
        ```json
        {
          "id": 1,
          "task": "수정된 할일 내용",
          "assignee": "변경된 담당자",
          "createdAt": "2024-05-07T13:00:00",
          "modifiedAt": "2024-05-07T15:30:00"
        }
        ```
-   **Error Response (404 Not Found):**
    -   해당 ID의 일정이 존재하지 않을 경우.
    -   **Body (JSON):**
        ```json
        {
          "message": "선택한 일정을 찾을 수 없습니다."
        }
        ```
-   **Error Response (401 Unauthorized 또는 400 Bad Request):**
    -   비밀번호가 일치하지 않을 경우.
    -   **Body (JSON):**
        ```json
        {
          "message": "비밀번호가 일치하지 않습니다."
        }
        ```

### 2.5. 선택 일정 삭제

-   **설명:** 특정 ID의 일정을 삭제합니다. 삭제를 위해서는 해당 일정의 `비밀번호`를 함께 전달해야 합니다.
-   **HTTP Method:** `DELETE`
-   **Endpoint:** `/schedules/{id}`
    -   **Path Variable:**
        -   `id`: 삭제할 일정의 고유 ID (숫자)
-   **Request Body (JSON):**
    -   비밀번호 확인을 위해 필요합니다.
    ```json
    {
      "password": "securePassword123"
    }
    ```
-   **Success Response (200 OK 또는 204 No Content):**
    -   **Body (JSON - 200 OK 예시):**
        ```json
        {
          "message": "일정이 성공적으로 삭제되었습니다."
        }
        ```
    -   또는 응답 본문 없이 204 No Content 상태 코드를 반환할 수도 있습니다.
-   **Error Response (404 Not Found):**
    -   해당 ID의 일정이 존재하지 않을 경우.
    -   **Body (JSON):**
        ```json
        {
          "message": "선택한 일정을 찾을 수 없습니다."
        }
        ```
-   **Error Response (401 Unauthorized 또는 400 Bad Request):**
    -   비밀번호가 일치하지 않을 경우.
    -   **Body (JSON):**
        ```json
        {
          "message": "비밀번호가 일치하지 않습니다."
        }
        ```

---

## ERD

### Schedule Table

| Column     | Data Type    | Constraints                 | Description          |
| :--------- | :----------- | :-------------------------- | :------------------- |
| id         | BIGINT       | PRIMARY KEY, AUTO_INCREMENT | 일정 고유 ID         |
| task       | VARCHAR(200) | NOT NULL                    | 할일 내용            |
| assignee   | VARCHAR(255) |                             | 작성자명             |
| password   | VARCHAR(255) | NOT NULL                    | 비밀번호 (해시 저장 권장)|
| createdAt  | DATETIME     |                             | 생성일시             |
| modifiedAt | DATETIME     |                             | 수정일시             |