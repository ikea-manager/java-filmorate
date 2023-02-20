package org.filmorate;

import org.filmorate.controller.FilmController;
import org.filmorate.exceptions.ValidationException;
import org.filmorate.model.Film;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FilmControllerTest {
    @Autowired
    FilmController filmController;
    Film film = new Film();

    @BeforeEach
    public void setUp() {
        film.setId(1);
        film.setName("Название");
        film.setDescription("Описание");
        film.setReleaseDate(LocalDate.of(1990, 1, 1));
        film.setDuration(120L);
    }

    @Test
    public void validationFilmPutTest_Ok() throws ValidationException {
        assertEquals(filmController.post(film), film);
    }

    @Test
    public void validationFilmPutTest_ValidationException_NameEmpty() throws ValidationException {
        film.setName("");
        Exception exception = assertThrows(ValidationException.class, () -> {
            filmController.post(film);
        });
        String expectedMessage = "Name не может быть пустым";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void validationFilmPutTest_ValidationException_MaxDescriptionLength() throws ValidationException {
        film.setDescription("o".repeat(201));
        Exception exception = assertThrows(ValidationException.class, () -> {
            filmController.post(film);
        });
        String expectedMessage = "Максимальная длина описания — 200 символов";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void validationFilmPutTest_ValidationException_MinReleaseDate() throws ValidationException {
        film.setReleaseDate(LocalDate.of(1895, 1, 1));
        Exception exception = assertThrows(ValidationException.class, () -> {
            filmController.post(film);
        });
        String expectedMessage = "Дата релиза должна быть не раньше 28 декабря 1895 года";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void validationFilmPutTest_ValidationException_DurationPositive() throws ValidationException {
        film.setDuration(-120L);
        Exception exception = assertThrows(ValidationException.class, () -> {
            filmController.post(film);
        });
        String expectedMessage = "Продолжительность фильма должна быть положительной";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}