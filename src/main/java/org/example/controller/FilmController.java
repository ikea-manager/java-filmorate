package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.ValidationException;
import org.example.model.Film;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    @GetMapping
    @ResponseBody
    public Collection<Film> get() {
        log.info("/films get");
        return List.of();
    }

    @PostMapping
    @ResponseBody
    public Film post(@RequestBody Film film) throws ValidationException {
        log.info("/films post");
        this.validate(film);
        return film;
    }

    @PatchMapping
    @ResponseBody
    public Film patch(@RequestBody Film film) {
        log.info("/films patch");
        return film;
    }

    private void validate(Film film) throws ValidationException {
        if (film.getName().isEmpty()) {
            log.error("Name не может быть пустым");
            throw new ValidationException("Name не может быть пустым");
        }
        if (film.getDescription().length()>200) {
            log.error("Максимальная длина описания — 200 символов");
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))){
            log.error("Дата релиза должна быть не раньше 28 декабря 1895 года");
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года");
        }
        if (film.getDuration().isNegative()) {
            log.error("Продолжительность фильма должна быть положительной");
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
    }
}
