package org.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.filmorate.model.Film;
import org.filmorate.service.FilmService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@AllArgsConstructor
public class FilmController {

    private final FilmService filmService;

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
    public Film post(@RequestBody Film film) {
        log.info("/films post");
        return filmService.add(film);
    }

    @PutMapping
    @ResponseBody
    public Film put(@RequestBody Film film) {
        log.info("/films patch");
        return filmService.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void like(@PathVariable Long id, @PathVariable Long userId) {
        log.info("/films like");
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("/films remove like");
        filmService.removeLike(id, userId);
    }

    @GetMapping(value = "/popular")
    @ResponseBody
    public List<Film> getTop(@RequestParam(defaultValue = "10") Integer count) {
        log.info("/films get top");
        return filmService.getTopNFilms(count);
    }
}
