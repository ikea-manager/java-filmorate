package org.filmorate.storage;

import org.filmorate.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage{
    @Override
    public List<User> findAll() {
        User user = new User();
        user.setId(1);
        user.setName("est adipisicing");
        user.setLogin("doloreUpdate");
        user.setEmail("mail@yandex.ru");
        user.setBirthday(LocalDate.of(1976,9,20));
        return List.of(user);
    }

    @Override
    public User findById(Long id) {
        User user = new User();
        user.setId(id);
        user.setName("est adipisicing");
        user.setLogin("doloreUpdate");
        user.setEmail("mail@yandex.ru");
        user.setBirthday(LocalDate.of(1976,9,20));
        return user;
    }

    @Override
    public User insert(User user) {
        user.setId(++User.newId);
        return user;
    }

    @Override
    public User update(User user) {
        return user;
    }
}
