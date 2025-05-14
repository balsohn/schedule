package com.example.schedules.repository;

import com.example.schedules.entity.Writer;

import java.util.List;
import java.util.Optional;

public interface WriterRepository {
    Writer save(Writer writer);
    Optional<Writer> findById(Long id);
    Writer findByIdOrElseThrow(Long id);
    List<Writer> findAll();
    Optional<Writer> findByEmail(String email);
    int update(Long id, Writer writer);
    int delete(Long id);
}
