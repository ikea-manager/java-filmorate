package org.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.filmorate.model.Genre;
import org.filmorate.model.Mpa;
import org.filmorate.storage.GenreStorage;
import org.filmorate.storage.MpaStorage;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MpaService {

    private final MpaStorage mpaStorage;

    public List<Mpa> getAll() {
        return mpaStorage.findAll();
    }

    public Mpa getById(Long id) {
        return mpaStorage.findById(id);
    }
}
