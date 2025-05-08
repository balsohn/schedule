package com.example.schedules.service;

import com.example.schedules.dto.ScheduleRequestDto;
import com.example.schedules.dto.ScheduleResponseDto;
import com.example.schedules.entity.Schedule;
import com.example.schedules.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public ScheduleResponseDto getScheduleById(Long id) {
        // ID로 일정 조회 (없으면 예외 발생)
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);

        // Entity를 DTO를 반환하여 반환
        return new ScheduleResponseDto(schedule);
    }

    @Override
    public List<ScheduleResponseDto> getAllSchedules(LocalDate date, String writer) {
        // 레포지토리에서 필터링 된 일정 목록 조회
        List<Schedule> schedules = scheduleRepository.findAll(date, writer);

        // Entity 목록을 DTO 목록으로 변환하여 반환
        return schedules.stream()
                .map(ScheduleResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {
        // 일정 조회
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);

        // 비밀번호 확인
        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        // 일정 수정
        int updatedRows = scheduleRepository.update(id, requestDto.getTodo(), requestDto.getWriter());

        if (updatedRows == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "일정 수정에 실패했습니다.");
        }

        // 수정된 일정 조회 및 반환
        Schedule updatedSchedule = scheduleRepository.findByIdOrElseThrow(id);

        return new ScheduleResponseDto(updatedSchedule);
    }
}
