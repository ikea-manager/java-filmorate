package org.filmorate.service;

import org.filmorate.model.User;
import org.filmorate.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage storage;

    public UserService(@Autowired UserStorage storage) {
        this.storage = storage;
    }

    public List<User> getAll(){
        return storage.findAll();
    }

    public User getById(Long id){
        return storage.findById(id);
    }

    public User add(User user){
        return storage.insert(user);
    }

    public User update(User user){
        return storage.update(user);
    }

    public void addToFriends(Long id, Long friendId){
        this.getById(id).getFriends().add(friendId);
    }

    public void removeFromFriends(Long id, Long friendId){
        this.getById(id).getFriends().remove(friendId);
    }

    public List<User> getFriends(Long id){
        return this.getById(id).getFriends().stream().map(this::getById).toList();
    }

    public Set<User> findCommonFriends(Long id, Long otherId){
        Set<Long> intersection = new HashSet<>(this.getById(id).getFriends());
        intersection.retainAll(this.getById(otherId).getFriends());
        return intersection.stream().map(this::getById).collect(Collectors.toSet());
    }
}
