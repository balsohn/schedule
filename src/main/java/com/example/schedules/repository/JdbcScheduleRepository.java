package com.example.schedules.repository;

import com.example.schedules.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        schedule.setCreateAt(rs.getTimestamp("created_at").toLocalDateTime());
        schedule.setModifiedAt(rs.getTimestamp("modified_at").toLocalDateTime());
        return schedule;
    });

    // 일정 저장
    @Override
    public Schedule save(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedules").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("todo", schedule.getTodo());
        parameters.put("writer", schedule.getWriter());
        parameters.put("password", schedule.getPassword());
        parameters.put("created_at", schedule.getCreateAt());
        parameters.put("modified_at", schedule.getModifiedAt());

        // 저장 후 생성된 키 값 가져오기
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        schedule.setId(key.longValue());

        return schedule;
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        // 쿼리
        String sql = "SELECT * FROM schedules WHERE id = ?";

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
}
