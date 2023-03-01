package org.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.filmorate.model.Genre;
import org.filmorate.model.Mpa;
import org.filmorate.service.GenreService;
import org.filmorate.service.MpaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
@AllArgsConstructor
public class MpaController {

    private final MpaService mpaService;

    @GetMapping
    @ResponseBody
    public List<Mpa> getAll() {
        log.info("/mpa get all");
        return mpaService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Mpa get(@PathVariable Long id) {
        log.info("/mpa get by id");
        return mpaService.getById(id);
    }
}
