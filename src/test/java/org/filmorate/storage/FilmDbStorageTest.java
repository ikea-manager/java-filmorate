package org.filmorate.storage;

import org.filmorate.exceptions.NotFoundException;
import org.filmorate.model.Film;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
class FilmDbStorageTest {

    @Autowired
    private FilmDbStorage filmDbStorage;

    @Test
    void findAll() {
        List<Film> list = filmDbStorage.findAll();
        assertNotNull(list);
    }

    @Test
    void findById() {
        Film film = filmDbStorage.findById(1l);
        assertNotNull(film);
        assertEquals(1l, film.getId());
        assertThrows(NotFoundException.class,()->{filmDbStorage.findById(999l);});
    }

    @Test
    void insert() {
        Film film = new Film();
        film.setName("new film");
        film.setDescription("very cool film");
        film.setReleaseDate(LocalDate.of(2020,7,12));
        film.setDuration(95l);

        Film newFilm = filmDbStorage.insert(film);

        film = filmDbStorage.findById(newFilm.getId());
        assertEquals(newFilm, film);
    }

    @Test
    void update() {
        Film film = filmDbStorage.findById(2l);
        film.setName("updated film");
        film.getLikes().add(1l);
        filmDbStorage.update(film);

        Film updFilm = filmDbStorage.findById(film.getId());
        assertEquals(film, updFilm);
    }
}