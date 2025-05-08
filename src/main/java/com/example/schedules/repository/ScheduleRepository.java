package com.example.schedules.repository;

import com.example.schedules.entity.Schedule;

public interface ScheduleRepository {
    Schedule save(Schedule schedule);   // 일정 저장
}
