package com.example.schedules.dto;

import com.example.schedules.entity.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String todo;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    // Schedule 엔티티를 DTO로 변환하는 생성자
    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.todo = schedule.getTodo();
        this.writer = schedule.getWriter();
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
    }
}
