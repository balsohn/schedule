package com.example.schedules.repository;

import com.example.schedules.entity.Schedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    Schedule save(Schedule schedule);   // 일정 저장
    Optional<Schedule> findById(Long id);
    Schedule findByIdOrElseThrow(Long id);
    List<Schedule> findAll(LocalDate date, String writer);
    int update(Long id, String todo, String writer);

}
