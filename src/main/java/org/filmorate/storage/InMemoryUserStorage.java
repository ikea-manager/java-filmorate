package org.filmorate.storage;

import org.filmorate.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage{
    private final List<User> users = new ArrayList<>();
    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User findById(Long id) {
        return users.stream().filter(user->user.getId()==id).findFirst().orElse(null);
    }

    @Override
    public User insert(User user) {
        user.setId(++User.newId);
        users.add(user);
        return user;
    }

    @Override
    public User update(User user) throws Exception {
        User findUser = findById(user.getId());
        if(findUser==null) throw new Exception("User not found");
        users.remove(findUser);
        users.add(user);
        return user;
    }
}
