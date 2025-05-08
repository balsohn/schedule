-- 데이터베이스가 존재하지 않으면 생성
CREATE DATABASE IF NOT EXISTS sparta_schedule;
USE sparta_schedule;

-- schedule 테이블이 존재하면 삭제 (개발)
DROP TABLE IF EXISTS schedule;

-- schedule 테이블 생성
CREATE TABLE schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    todo VARCHAR(200) NOT NULL,
    writer VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
