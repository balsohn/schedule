package com.example.schedules.service;

import com.example.schedules.dto.ScheduleRequestDto;
import com.example.schedules.dto.ScheduleResponseDto;
import com.example.schedules.entity.Schedule;
import com.example.schedules.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        // 요청 DTO를 Entity로 변환
        Schedule schedule = new Schedule(
                requestDto.getTodo(),
                requestDto.getWriter(),
                requestDto.getPassword()
        );

        // Repository를 통해 저장
        Schedule saveSchedule = scheduleRepository.save(schedule);

        // Entity를 응답 DTO로 변환하여 반환
        return new ScheduleResponseDto(saveSchedule);
    }
}
