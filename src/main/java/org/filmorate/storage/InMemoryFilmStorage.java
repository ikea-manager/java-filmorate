package org.filmorate.storage;

import org.filmorate.model.Film;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryFilmStorage implements FilmStorage{
    private final List<Film> films = new ArrayList<>();
    @Override
    public List<Film> findAll() {
        return films;
    }

    @Override
    public Film findById(Long id) {
        return films.stream().filter(film->film.getId()==id).findFirst().orElse(null);
    }

    @Override
    public Film insert(Film film) {
        film.setId(++Film.newId);
        films.add(film);
        return film;
    }

    @Override
    public Film update(Film film) {
        films.remove(this.findById(film.getId()));
        films.add(film);
        return film;
    }
}
