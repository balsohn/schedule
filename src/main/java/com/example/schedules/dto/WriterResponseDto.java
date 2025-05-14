package com.example.schedules.dto;

import com.example.schedules.entity.Writer;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class WriterResponseDto {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public WriterResponseDto(Writer writer) {
        this.id = writer.getId();
        this.name = writer.getName();
        this.email = writer.getEmail();
        this.createdAt = writer.getCreatedAt();
        this.modifiedAt = writer.getModifiedAt();
    }
}
