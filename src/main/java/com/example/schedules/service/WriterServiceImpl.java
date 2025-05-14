package com.example.schedules.service;

import com.example.schedules.dto.WriterRequestDto;
import com.example.schedules.dto.WriterResponseDto;
import com.example.schedules.entity.Writer;
import com.example.schedules.repository.WriterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WriterServiceImpl implements WriterService {

    private final WriterRepository writerRepository;

    public WriterServiceImpl(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    @Override
    public WriterResponseDto createWriter(WriterRequestDto requestDto) {
        writerRepository.findByEmail(requestDto.getEmail())
                .ifPresent(writer -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 등록된 이메일입니다.");
                });

        Writer writer = new Writer(
                requestDto.getName(),
                requestDto.getEmail()
        );

        Writer savedWriter = writerRepository.save(writer);

        return new WriterResponseDto(savedWriter);
    }

    @Override
    public WriterResponseDto getWriterById(Long id) {
        Writer writer = writerRepository.findByIdOrElseThrow(id);
        return new WriterResponseDto(writer);
    }

    @Override
    public List<WriterResponseDto> getAllWriters() {
        List<Writer> writers = writerRepository.findAll();
        return writers.stream()
                .map(WriterResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public WriterResponseDto updateWriter(Long id, WriterRequestDto requestDto) {
        Writer writer = writerRepository.findByIdOrElseThrow(id);

        if (!writer.getEmail().equals(requestDto.getEmail())) {
            writerRepository.findByEmail(requestDto.getEmail())
                    .ifPresent(w -> {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 등록된 이메일입니다.");
                    });
        }

        writer.setName(requestDto.getName());
        writer.setEmail(requestDto.getEmail());
        writer.setModifiedAt(LocalDateTime.now());

        int updated = writerRepository.update(id, writer);

        if (updated == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "작성자 정보 수정에 실패했습니다.");
        }

        Writer updatedWriter = writerRepository.findByIdOrElseThrow(id);

        return new WriterResponseDto(updatedWriter);
    }

    @Override
    public void deleteWriter(Long id) {
        writerRepository.findByIdOrElseThrow(id);

        int delete = writerRepository.delete(id);

        if (delete == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "작성자 삭제에 실패했습니다.");
        }

    }
}
