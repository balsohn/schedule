package com.example.schedules.service;

import com.example.schedules.dto.ScheduleRequestDto;
import com.example.schedules.dto.ScheduleResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    // 일정 생성
    ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto);
    ScheduleResponseDto getScheduleById(Long id);
    List<ScheduleResponseDto> getAllSchedules(LocalDate date, String writer);
    ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto);
    void deleteSchedule(Long id, ScheduleRequestDto requestDto);
}
