package org.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.filmorate.exceptions.ValidationException;
import org.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private static final int MAX_DESCRIPTION_LENGTH = 200;
    private static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    @GetMapping
    @ResponseBody
    public List<Film> get() {
        log.info("/films get");
        Film film = new Film();
        film.setId(1);
        film.setName("Название");
        film.setDescription("Описание");
        film.setReleaseDate(LocalDate.of(1990,1,1));
        film.setDuration(Duration.ofHours(2));
        return List.of(film);
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
        if (film.getDescription().length()>MAX_DESCRIPTION_LENGTH) {
            log.error("Максимальная длина описания — 200 символов");
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(MIN_RELEASE_DATE)){
            log.error("Дата релиза должна быть не раньше 28 декабря 1895 года");
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года");
        }
        if (film.getDuration().isNegative()) {
            log.error("Продолжительность фильма должна быть положительной");
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
    }
}
