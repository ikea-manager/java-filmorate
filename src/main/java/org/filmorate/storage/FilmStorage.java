package org.filmorate.storage;

import org.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    List<Film> findAll();

    Film findById(Long id);

    Film insert(Film film);

    Film update(Film film);
}
