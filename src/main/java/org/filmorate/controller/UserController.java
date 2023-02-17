package org.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.filmorate.exceptions.ValidationException;
import org.filmorate.model.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private static final String SPACE = " ";
    private static final String AT = "@";
    @GetMapping
    @ResponseBody
    public List<User> get() {
        log.info("/users get");
        User user = new User();
        user.setId(1);
        user.setName("Название");
        user.setLogin("login");
        user.setEmail("email@mail.com");
        user.setBirthday(LocalDate.of(1990,1,1));
        return List.of(user);
    }

    @PostMapping
    @ResponseBody
    public User post(@RequestBody User user) throws ValidationException {
        log.info("/users post");
        this.validate(user);
        return user;
    }

    @PatchMapping
    @ResponseBody
    public User patch(@RequestBody User user) {
        log.info("/users patch");
        return user;
    }

    private void validate(User user) throws ValidationException {
        if (user.getEmail().isEmpty()) {
            log.error("Email не может быть пустым");
            throw new ValidationException("Email не может быть пустым");
        }
        if (!user.getEmail().contains(AT)) {
            log.error("Email должен содержать символ @");
            throw new ValidationException("Email должен содержать символ @");
        }
        if (user.getLogin().isEmpty()) {
            log.error("Login не может быть пустым");
            throw new ValidationException("Login не может быть пустым");
        }
        if (user.getLogin().contains(SPACE)) {
            log.error("Login не может содержать пробелы");
            throw new ValidationException("Login не может содержать пробелы");
        }
        if (user.getName().isEmpty()) user.setName(user.getLogin());
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Birthday не может быть в будущем");
            throw new ValidationException("Birthday не может быть в будущем");
        }
    }
}
