package com.example.schedules.service;

import com.example.schedules.dto.WriterRequestDto;
import com.example.schedules.dto.WriterResponseDto;

import java.util.List;

public interface WriterService {
    WriterResponseDto createWriter(WriterRequestDto requestDto);
    WriterResponseDto getWriterById(Long id);
    List<WriterResponseDto> getAllWriters();
    WriterResponseDto updateWriter(Long id, WriterRequestDto requestDto);
    void deleteWriter(Long id);
}
