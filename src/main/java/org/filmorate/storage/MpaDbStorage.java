package org.filmorate.storage;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.filmorate.exceptions.NotFoundException;
import org.filmorate.model.Mpa;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Slf4j
@Component("MpaDbStorage")
@AllArgsConstructor
public class MpaDbStorage implements MpaStorage {

    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Mpa> rowMapper = new RowMapper() {
        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Mpa mpa = new Mpa();
            mpa.setId(rs.getInt("id"));
            mpa.setName(rs.getString("name"));
            return mpa;
        }
    };

    @Override
    public List<Mpa> findAll() {
        return jdbcTemplate.query("SELECT id, name FROM MPA", rowMapper);
    }

    @Override
    public Mpa findById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT id, name FROM MPA WHERE id=?", new Object[]{id}, rowMapper);
        } catch (DataAccessException e) {
            throw new NotFoundException("Mpa not found");
        }
    }

    @Override
    public Mpa insert(Mpa mpa) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO MPA (name) VALUES (?)",
                            Statement.RETURN_GENERATED_KEYS
                    );
            ps.setString(1, mpa.getName());
            return ps;
        }, keyHolder);
        mpa.setId(keyHolder.getKey().longValue());
        return mpa;
    }

    @Override
    public Mpa update(Mpa mpa) {
        int cnt = jdbcTemplate.update("UPDATE MPA SET name=? WHERE id=?",
                mpa.getName(), mpa.getId()
        );
        if (cnt == 0) {
            throw new NotFoundException("Film not found");
        }
        return mpa;
    }
}
