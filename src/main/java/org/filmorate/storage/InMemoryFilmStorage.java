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
    public Film update(Film film) throws Exception {
        Film findFilm = this.findById(film.getId());
        if(findFilm==null) throw new Exception("Film not found");
        films.remove(findFilm);
        films.add(film);
        return film;
    }
}
