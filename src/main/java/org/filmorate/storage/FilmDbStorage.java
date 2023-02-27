package org.filmorate.storage;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.filmorate.exceptions.NotFoundException;
import org.filmorate.model.Film;
import org.filmorate.model.Genre;
import org.filmorate.model.Mpa;
import org.filmorate.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Slf4j
@Primary
@Component("FilmDbStorage")
@AllArgsConstructor
public class FilmDbStorage implements FilmStorage{

    private JdbcTemplate jdbcTemplate;
    private GenreStorage genreStorage;
    private MpaStorage mpaStorage;

    private final RowMapper<Film> rowMapper = new RowMapper(){
        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Film film = new Film();
            film.setId(rs.getInt("id"));
            film.setName(rs.getString("name"));
            film.setDescription(rs.getString("description"));
            film.setReleaseDate(rs.getDate("releaseDate").toLocalDate());
            film.setDuration((long)rs.getInt("duration"));
            film.setLikes(new TreeSet<Long>(jdbcTemplate.queryForList("SELECT userId FROM LIKES WHERE filmId=?"
                    ,new Object[]{film.getId()}, Long.class)));
            long mpaId = rs.getLong("mpaId");
            if(!rs.wasNull()) {
                film.setMpa(mpaStorage.findById(mpaId));
            }
            film.setGenres(genreStorage.findByFilm(film.getId()));

            return film;
        }
    };


    @Override
    public List<Film> findAll() {
        return jdbcTemplate.query("SELECT * FROM FILMS", rowMapper);
    }

    @Override
    public Film findById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM FILMS WHERE id=?", new Object[]{id}, rowMapper);
        }catch(DataAccessException e) {
            throw new NotFoundException("Film not found");
        }
    }

    @Override
    public Film insert(Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO FILMS (name, description, releaseDate, duration, mpaId) VALUES (?,?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS
                            );
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, Date.valueOf(film.getReleaseDate()));
            ps.setLong(4, film.getDuration());
            if(film.getMpa()!=null) {
                ps.setLong(5, film.getMpa().getId());
            } else {
                ps.setNull(5, Types.INTEGER);
            }
            return ps;
        }, keyHolder);
        film.setId(keyHolder.getKey().longValue());
        for(long userId: film.getLikes()) {
            try {
                jdbcTemplate.update("INSERT INTO LIKES (filmId,userId) VALUES (?,?)",
                        film.getId(), userId
                );
            }catch (DataAccessException e){
                log.warn("error on insert: "+e);
            }
        }
        for(Genre genre: film.getGenres()) {
            try {
                jdbcTemplate.update("INSERT INTO FILMGENRES (filmId,genreId) VALUES (?,?)",
                        film.getId(), genre.getId()
                );
            }catch (DataAccessException e){
                log.warn("error on insert: "+e);
            }
        }
        return findById(film.getId());
    }

    @Override
    public Film update(Film film) {
        int cnt = jdbcTemplate.update("UPDATE FILMS SET name=?, description=?, releaseDate=?, duration=?, mpaId=? WHERE id=?",
                film.getName(), film.getDescription(),
                Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                (film.getMpa()!=null) ? film.getMpa().getId() : null,
                film.getId()
        );
        if(cnt==0) {
            throw new NotFoundException("Film not found");
        }
        jdbcTemplate.update("DELETE FROM LIKES WHERE filmId=?", new Object[]{film.getId()});
        for(long userId: film.getLikes()) {
            try {
                jdbcTemplate.update("INSERT INTO LIKES (filmId,userId) VALUES (?,?)",
                        film.getId(), userId
                );
            }catch (DataAccessException e){
                log.warn("error on insert: "+e);
            }
        }
        jdbcTemplate.update("DELETE FROM FILMGENRES WHERE filmId=?", new Object[]{film.getId()});
        for(Genre genre: film.getGenres()) {
            try{
                jdbcTemplate.update("INSERT INTO FILMGENRES (filmId,genreId) VALUES (?,?)",
                        film.getId(), genre.getId()
                );
            }catch (DataAccessException e){
                log.warn("error on insert: "+e);
            }
        }
        return findById(film.getId());
    }
}
