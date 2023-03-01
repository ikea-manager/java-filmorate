package org.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.filmorate.model.Genre;
import org.filmorate.storage.GenreStorage;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class GenreService {

    private final GenreStorage genreStorage;

    public List<Genre> getAll() {
        return genreStorage.findAll();
    }

    public Genre getById(Long id) {
        return genreStorage.findById(id);
    }
}
