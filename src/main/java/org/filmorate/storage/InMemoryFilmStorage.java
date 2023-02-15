package org.filmorate.storage;

import org.filmorate.model.Film;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Component
public class InMemoryFilmStorage implements FilmStorage{
    @Override
    public List<Film> findAll() {
        Film film = new Film();
        film.setId(1);
        film.setName("Film Updated");
        film.setDescription("New film update decription");
        film.setReleaseDate(LocalDate.of(1989,4,17));
        film.setDuration(Duration.ofMinutes(190));
        return List.of(film);
    }

    @Override
    public Film findById(Long id) {
        Film film = new Film();
        film.setId(id);
        film.setName("Film Updated");
        film.setDescription("New film update decription");
        film.setReleaseDate(LocalDate.of(1989,4,17));
        film.setDuration(Duration.ofMinutes(190));
        return film;
    }

    @Override
    public Film insert(Film film) {
        film.setId(++Film.newId);
        return film;
    }

    @Override
    public Film update(Film film) {
        return film;
    }
}
