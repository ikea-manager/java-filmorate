package org.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.filmorate.exceptions.ValidationException;
import org.filmorate.model.User;
import org.filmorate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    private static final String SPACE = " ";
    private static final String AT = "@";
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
    public User post(@RequestBody User user) throws ValidationException {
        log.info("/users post");
        this.validate(user);
        return userService.add(user);
    }

    @PutMapping
    @ResponseBody
    public User put(@RequestBody User user) throws ValidationException {
        log.info("/users patch");
        this.validate(user);
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

    private void validate(User user) throws ValidationException {
        if (user.getEmail()==null||user.getEmail().isEmpty()) {
            log.error("Email не может быть пустым");
            throw new ValidationException("Email не может быть пустым");
        }
        if (!user.getEmail().contains(AT)) {
            log.error("Email должен содержать символ @");
            throw new ValidationException("Email должен содержать символ @");
        }
        if (user.getLogin()==null||user.getLogin().isEmpty()) {
            log.error("Login не может быть пустым");
            throw new ValidationException("Login не может быть пустым");
        }
        if (user.getLogin().contains(SPACE)) {
            log.error("Login не может содержать пробелы");
            throw new ValidationException("Login не может содержать пробелы");
        }
        if (user.getName()==null||user.getName().isEmpty()) user.setName(user.getLogin());
        if (user.getBirthday()!=null && user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Birthday не может быть в будущем");
            throw new ValidationException("Birthday не может быть в будущем");
        }
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationException(ValidationException e) {
        return e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e) {
        return e.getMessage();
    }
}
