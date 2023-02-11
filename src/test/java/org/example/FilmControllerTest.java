package org.example;

import org.example.controller.FilmController;
import org.example.exceptions.ValidationException;
import org.example.model.Film;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FilmControllerTest {
    @Autowired
    FilmController filmController;
    @Test
    public void validationFilmPutTest() throws ValidationException {
        Film film = new Film();
        film.setId(1);
        film.setName("Название");
        film.setDescription("Описание");
        film.setReleaseDate(LocalDate.of(1990,1,1));
        film.setDuration(Duration.ofHours(2));

        assertEquals(filmController.post(film), film);

        film.setName("");
        Exception exception = assertThrows(ValidationException.class, () -> {
            filmController.post(film);
        });
        String expectedMessage = "Name не может быть пустым";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        film.setName("Название");

        film.setDescription("oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo" +
                        "oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
        exception = assertThrows(ValidationException.class, () -> {
            filmController.post(film);
        });
        expectedMessage = "Максимальная длина описания — 200 символов";
        actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        film.setDescription("Описание");

        film.setReleaseDate(LocalDate.of(1895,1,1));
        exception = assertThrows(ValidationException.class, () -> {
            filmController.post(film);
        });
        expectedMessage = "Дата релиза должна быть не раньше 28 декабря 1895 года";
        actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        film.setReleaseDate(LocalDate.of(1990,1,1));

        film.setDuration(Duration.ofHours(-2));
        exception = assertThrows(ValidationException.class, () -> {
            filmController.post(film);
        });
        expectedMessage = "Продолжительность фильма должна быть положительной";
        actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        film.setDuration(Duration.ofHours(2));
    }
}