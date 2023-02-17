package org.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.filmorate.exceptions.ValidationException;
import org.filmorate.model.ErrorResponse;
import org.filmorate.model.Film;
import org.filmorate.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    public FilmController(@Autowired FilmService filmService) {
        this.filmService = filmService;
    }

    private static final int MAX_DESCRIPTION_LENGTH = 200;
    private static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    @GetMapping
    @ResponseBody
    public List<Film> getAll() {
        log.info("/films get all");
        return filmService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Film get(@PathVariable Long id) {
        log.info("/films get by id");
        return filmService.getById(id);
    }

    @PostMapping
    @ResponseBody
    public Film post(@RequestBody Film film) throws ValidationException {
        log.info("/films post");
        this.validate(film);
        return filmService.add(film);
    }

    @PutMapping
    @ResponseBody
    public Film put(@RequestBody Film film) throws Exception {
        log.info("/films patch");
        this.validate(film);
        return filmService.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void like(@PathVariable Long id, @PathVariable Long userId) throws ValidationException {
        log.info("/films like");
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeLike(@PathVariable Long id, @PathVariable Long userId) throws ValidationException {
        log.info("/films remove like");
        filmService.removeLike(id, userId);
    }

    @GetMapping("/popular?count={count}")
    @ResponseBody
    public List<Film> getTop(@PathVariable Integer count) {
        log.info("/films get top");
        return filmService.getTopNFilms(count);
    }

    private void validate(Film film) throws ValidationException {
        if (film.getName()==null||film.getName().isEmpty()) {
            log.error("Name не может быть пустым");
            throw new ValidationException("Name не может быть пустым");
        }
        if (film.getDescription()!=null && film.getDescription().length()>MAX_DESCRIPTION_LENGTH) {
            log.error("Максимальная длина описания — 200 символов");
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate()!=null && film.getReleaseDate().isBefore(MIN_RELEASE_DATE)){
            log.error("Дата релиза должна быть не раньше 28 декабря 1895 года");
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года");
        }
        if (film.getDuration()!=null && film.getDuration()<0) {
            log.error("Продолжительность фильма должна быть положительной");
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(ValidationException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e) {
        return new ErrorResponse(e.getMessage());
    }
}
