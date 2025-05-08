package com.example.schedules.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {
    private Long id;
    private String todo;
    private String writer;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    // 커스텀 생성자 - 일정생성시 사용
    public Schedule(String todo, String writer, String password) {
        this.todo = todo;
        this.writer = writer;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }
}
