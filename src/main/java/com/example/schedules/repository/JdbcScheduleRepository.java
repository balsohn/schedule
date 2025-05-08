package com.example.schedules.repository;

import com.example.schedules.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Schedule> scheduleRowMapper = ((rs, rowNum) -> {
        Schedule schedule = new Schedule();
        schedule.setId(rs.getLong("id"));
        schedule.setTodo(rs.getString("todo"));
        schedule.setWriter(rs.getString("writer"));
        schedule.setPassword(rs.getString("password"));
        schedule.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        schedule.setModifiedAt(rs.getTimestamp("modified_at").toLocalDateTime());
        return schedule;
    });

    // 일정 저장
    @Override
    public Schedule save(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("todo", schedule.getTodo());
        parameters.put("writer", schedule.getWriter());
        parameters.put("password", schedule.getPassword());
        parameters.put("created_at", schedule.getCreatedAt());
        parameters.put("modified_at", schedule.getModifiedAt());

        // 저장 후 생성된 키 값 가져오기
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        schedule.setId(key.longValue());

        return schedule;
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        // 쿼리
        String sql = "SELECT * FROM schedule WHERE id = ?";

        // 쿼리 실행
        List<Schedule> results = jdbcTemplate.query(sql, scheduleRowMapper, id);

        // 결과가 있으면 첫번째 결과를 Optional로 감싸서 반환, 없으면 빈 Optional반환
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Schedule findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다. id: " + id
                ));
    }

    @Override
    public List<Schedule> findAll(LocalDate date, String writer) {
        String sql = "SELECT * FROM schedule WHERE 1=1";
        List<Object> params = new ArrayList<>();

        // 날짜로 필터링
        if (date != null) {
            sql += " AND DATE(modified_at) = ?";
            params.add(date);
        }

        // 작성자로 필터링
        if (writer != null) {
            sql += " AND writer = ?";
            params.add(writer);
        }

        // 수정일 내림차순 정렬
        sql += " ORDER BY modified_at DESC";

        return jdbcTemplate.query(sql, scheduleRowMapper, params.toArray());
    }

    @Override
    public int update(Long id, String todo, String writer) {
        String sql = "UPDATE schedule SET todo = ?, writer = ?, modified_at = ? WHERE id = ?";
        return jdbcTemplate.update(sql, todo, writer, LocalDateTime.now(), id);
    }
}
