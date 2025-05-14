package com.example.schedules.repository;

import com.example.schedules.entity.Writer;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcWriterRepository implements WriterRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcWriterRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Writer> writerRowMapper = ((rs, rowNum) -> {
        Writer writer = new Writer();
        writer.setId(rs.getLong("id"));
        writer.setName(rs.getString("name"));
        writer.setEmail(rs.getString("email"));
        writer.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        writer.setModifiedAt(rs.getTimestamp("modified_at").toLocalDateTime());
        return writer;
    });

    @Override
    public Writer save(Writer writer) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("writer").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", writer.getName());
        parameters.put("email", writer.getEmail());
        parameters.put("created_at", writer.getCreatedAt());
        parameters.put("modified_at", writer.getModifiedAt());

        Number key = jdbcInsert.executeAndReturnKey(parameters);
        writer.setId(key.longValue());

        return writer;
    }

    @Override
    public Optional<Writer> findById(Long id) {
        String sql = "SELECT * FROM writer WHERE id = ?";
        List<Writer> results = jdbcTemplate.query(sql, writerRowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Writer findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "작성자를 찾을 수 없습니다. id: " + id
                ));
    }

    @Override
    public List<Writer> findAll() {
        String sql = "SELECT * FROM writer ORDER BY name";
        return jdbcTemplate.query(sql, writerRowMapper);
    }

    @Override
    public Optional<Writer> findByEmail(String email) {
        String sql = "SELECT * FROM writer WHERE email = ?";
        List<Writer> results = jdbcTemplate.query(sql, writerRowMapper, email);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public int update(Long id, Writer writer) {
        String sql = "UPDATE writer SET name = ?, email = ?, modified_at = ? WHERE id = ?";
        return jdbcTemplate.update(sql, writer.getName(), writer.getEmail(), LocalDateTime.now(), id);
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM writer WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
