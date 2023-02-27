package org.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.filmorate.model.Film;
import org.filmorate.model.Genre;
import org.filmorate.service.GenreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/genres")
@AllArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    @ResponseBody
    public List<Genre> getAll() {
        log.info("/genres get all");
        return genreService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Genre get(@PathVariable Long id) {
        log.info("/genres get by id");
        return genreService.getById(id);
    }
}
