package org.filmorate.storage;

import org.filmorate.exceptions.NotFoundException;
import org.filmorate.model.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
class GenreDbStorageTest {

    @Autowired
    private GenreStorage genreStorage;

    @Test
    void findAll() {
        List<Genre> list = genreStorage.findAll();
        assertNotNull(list);
        assertNotEquals(0, list.size());
    }

    @Test
    void findByFilm() {
        List<Genre> list = genreStorage.findByFilm(1);
        assertNotNull(list);
        assertEquals(2, list.size());
    }

    @Test
    void findById() {
        Genre genre = genreStorage.findById(2l);
        assertNotNull(genre);
        assertEquals("Драма", genre.getName());

        assertThrows(NotFoundException.class, ()-> {genreStorage.findById(15l);});
    }

    @Test
    void insert() {
        Genre genre = new Genre();
        genre.setName("Новый жанр");
        genreStorage.insert(genre);
        Genre newGenre = genreStorage.findById(genre.getId());
        assertEquals(genre, newGenre);
    }

    @Test
    void update() {
        Genre genre = genreStorage.findById(3l);
        genre.setName("Обновленный жанр");
        genreStorage.update(genre);
        Genre updGenre = genreStorage.findById(genre.getId());
        assertEquals(genre, updGenre);
    }
}