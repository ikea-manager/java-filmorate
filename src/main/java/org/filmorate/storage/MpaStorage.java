package org.filmorate.storage;

import org.filmorate.model.Genre;
import org.filmorate.model.Mpa;

import java.util.List;

public interface MpaStorage {
    List<Mpa> findAll();

    Mpa findById(Long id);

    Mpa insert(Mpa film);

    Mpa update(Mpa film);
}
