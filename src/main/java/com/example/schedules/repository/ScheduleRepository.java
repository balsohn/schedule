package com.example.schedules.repository;

import com.example.schedules.entity.Schedule;

import java.util.Optional;

public interface ScheduleRepository {
    Schedule save(Schedule schedule);   // 일정 저장
    Optional<Schedule> findById(Long id);
    Schedule findByIdOrElseThrow(Long id);

}
