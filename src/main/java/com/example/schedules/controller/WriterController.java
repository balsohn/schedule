package com.example.schedules.controller;

import com.example.schedules.dto.WriterRequestDto;
import com.example.schedules.dto.WriterResponseDto;
import com.example.schedules.entity.Writer;
import com.example.schedules.service.WriterService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/writers")
public class WriterController {

    private final WriterService writerService;

    public WriterController(WriterService writerService) {
        this.writerService = writerService;
    }

    @PostMapping
    public ResponseEntity<WriterResponseDto> createWriter(@RequestBody WriterRequestDto requestDto) {
        WriterResponseDto responseDto = writerService.createWriter(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WriterResponseDto> getWriterById(@PathVariable Long id) {
        WriterResponseDto responseDto = writerService.getWriterById(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<WriterResponseDto>> getAllWriters() {
        List<WriterResponseDto> writers = writerService.getAllWriters();
        return ResponseEntity.ok(writers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WriterResponseDto> updateWriter(
            @PathVariable Long id,
            @RequestBody WriterRequestDto requestDto
    ) {
        WriterResponseDto responseDto = writerService.updateWriter(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WriterResponseDto> deleteWriter(
            @PathVariable Long id
    ) {
        writerService.deleteWriter(id);
        return ResponseEntity.noContent().build();
    }
}
