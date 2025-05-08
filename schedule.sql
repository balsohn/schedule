-- 데이터베이스가 존재하지 않으면 생성
-- CREATE DATABASE IF NOT EXISTS schedule;
-- USE schedule;

-- schedule 테이블이 존재하면 삭제 (개발)
DROP TABLE IF EXISTS schedule;

-- schedule 테이블 생성
CREATE TABLE IF NOT EXISTS schedule (
    id BIGINT AUTO_INCREMENT,
    task VARCHAR(200) NOT NULL,
    assignee VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    createAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    modifiedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
)


-- (선택 사항) 테스트를 위한 초기 데이터 삽입 예시
INSERT INTO schedule (task, assignee, password, createdAt, modifiedAt)
VALUES ('스프링 부트 스터디', '홍길동', '1234', NOW(), NOW());

INSERT INTO schedule (task, assignee, password, createdAt, modifiedAt)
VALUES ('Lv0 과제 완료하기', '김철수', '1234', '2024-05-06 10:00:00', '2024-05-06 10:00:00');

INSERT INTO schedule (task, assignee, password, createdAt, modifiedAt)
VALUES ('운동하기', '이영희', '1234', CURDATE(), CURDATE());