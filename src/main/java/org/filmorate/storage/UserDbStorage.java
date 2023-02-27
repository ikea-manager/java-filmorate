package org.filmorate.storage;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.filmorate.exceptions.NotFoundException;
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
import java.util.List;
import java.util.TreeSet;

@Slf4j
@Primary
@Component("UserDbStorage")
@AllArgsConstructor
public class UserDbStorage implements UserStorage {

    private JdbcTemplate jdbcTemplate;

    private final RowMapper<User> rowMapper = new RowMapper(){
        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setLogin(rs.getString("login"));
            user.setName(rs.getString("name"));
            user.setBirthday(rs.getDate("birthday").toLocalDate());
            user.setFriends(new TreeSet<Long>(jdbcTemplate.queryForList("SELECT friendId FROM FRIENDS WHERE userId=?"
                    ,new Object[]{user.getId()}, Long.class)));
            return user;
        }
    };

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM USERS", rowMapper);
    }

    @Override
    public User findById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE id=?", new Object[]{id}, rowMapper);
        }catch(DataAccessException e) {
            throw new NotFoundException("User not found");
        }
    }

    @Override
    public User insert(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO USERS (email, login, name, birthday) VALUES (?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS
                            );
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getName());
            ps.setDate(4, Date.valueOf(user.getBirthday()));
            return ps;
        }, keyHolder);
        user.setId(keyHolder.getKey().longValue());
        for(long friendId: user.getFriends()) {
            try {
                jdbcTemplate.update("INSERT INTO FRIENDS (userId,friendId) VALUES (?,?)",
                        user.getId(), friendId
                );
            }catch (DataAccessException e){
                log.warn("error on insert: "+e);
            }
        }
        return user;
    }

    @Override
    public User update(User user) {
        int cnt = jdbcTemplate.update("UPDATE USERS SET email=?, login=?, name=?, birthday=? WHERE id=?",
                user.getEmail(), user.getLogin(), user.getName(),
                Date.valueOf(user.getBirthday()),
                user.getId()
                );
        if(cnt==0) {
            throw new NotFoundException("User not found");
        }
        jdbcTemplate.update("DELETE FROM FRIENDS WHERE userId=?", new Object[]{user.getId()});
        for(long friendId: user.getFriends()) {
            try {
                jdbcTemplate.update("INSERT INTO FRIENDS (userId,friendId) VALUES (?,?)",
                        user.getId(), friendId
                );
            }catch (DataAccessException e){
                log.warn("error on insert: "+e);
            }
        }
        return user;
    }
}
