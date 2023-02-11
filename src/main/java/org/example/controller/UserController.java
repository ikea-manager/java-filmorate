package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.ValidationException;
import org.example.model.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping
    @ResponseBody
    public Collection<User> get() {
        log.info("/users get");
        return List.of();
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
        if (!user.getEmail().contains("@")) {
            log.error("Email должен содержать символ @");
            throw new ValidationException("Email должен содержать символ @");
        }
        if (user.getLogin().isEmpty()) {
            log.error("Login не может быть пустым");
            throw new ValidationException("Login не может быть пустым");
        }
        if (user.getLogin().contains(" ")) {
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
