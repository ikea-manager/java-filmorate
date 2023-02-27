package org.filmorate.storage;

import org.filmorate.exceptions.NotFoundException;
import org.filmorate.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
class UserDbStorageTest {

    @Autowired
    private UserDbStorage userDbStorage;

    @Test
    void findAll() {
        List<User> list = userDbStorage.findAll();
        assertNotNull(list);
    }

    @Test
    void findById() {
        User user = userDbStorage.findById(2l);
        assertNotNull(user);
        assertEquals(2, user.getId());
        assertThrows(NotFoundException.class, ()->{userDbStorage.findById(9999l);});
    }

    @Test
    void insert() {
        User user = new User();
        user.setEmail("mail@google.com");
        user.setLogin("login");
        user.setBirthday(LocalDate.of(1999,1,5));

        User newUser = userDbStorage.insert(user);
        assertNotNull(newUser);

        user = userDbStorage.findById(newUser.getId());
        assertEquals(user, newUser);
    }

    @Test
    void update() {
        User user = userDbStorage.findById(3l);
        user.setEmail("newmail@yandex.ru");
        user.getFriends().add(1l);
        userDbStorage.update(user);
        User updUser = userDbStorage.findById(user.getId());
        assertEquals(user, updUser);

        user.setId(9999l);
        assertThrows(NotFoundException.class, ()->{userDbStorage.update(user);});
    }
}