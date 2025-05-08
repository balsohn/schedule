package com.example.schedules.service;

import com.example.schedules.dto.ScheduleRequestDto;
import com.example.schedules.dto.ScheduleResponseDto;

public interface ScheduleService {
    // 일정 생성
    ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto);
}
