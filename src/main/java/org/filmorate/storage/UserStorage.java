package org.filmorate.storage;

import org.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    List<User> findAll();
    User findById(Long id);
    User insert(User user);
    User update(User user);
}
