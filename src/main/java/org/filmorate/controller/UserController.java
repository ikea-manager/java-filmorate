package org.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.filmorate.model.User;
import org.filmorate.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @ResponseBody
    public List<User> getAll() {
        log.info("/users get all");
        return userService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public User get(@PathVariable Long id) {
        log.info("/users get by id");
        return userService.getById(id);
    }

    @PostMapping
    @ResponseBody
    public User post(@RequestBody User user) {
        log.info("/users post");
        return userService.add(user);
    }

    @PutMapping
    @ResponseBody
    public User put(@RequestBody User user) {
        log.info("/users patch");
        return userService.update(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void addToFriends(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("/users add to friends");
        userService.addToFriends(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeFromFriends(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("/users remove from friends");
        userService.removeFromFriends(id, friendId);
    }

    @GetMapping("/{id}/friends")
    @ResponseBody
    public List<User> getFriends(@PathVariable Long id) {
        log.info("/users get friends");
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    @ResponseBody
    public Set<User> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        log.info("/users get common friends");
        return userService.findCommonFriends(id, otherId);
    }
}
