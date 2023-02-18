package org.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.filmorate.exceptions.ValidationException;
import org.filmorate.model.Film;
import org.filmorate.storage.FilmStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FilmService {
    private static final int MAX_DESCRIPTION_LENGTH = 200;
    private static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    private final FilmStorage storage;

    public List<Film> getAll(){
        return storage.findAll();
    }

    public Film getById(Long id){
        return storage.findById(id);
    }

    public Film add(Film film){
        this.validate(film);
        return storage.insert(film);
    }

    public Film update(Film film) {
        this.validate(film);
        return storage.update(film);
    }

    public void addLike(Long id, Long userId){
        this.getById(id).getLikes().add(userId);
    }

    public void removeLike(Long id, Long userId){
        this.getById(id).getLikes().remove(userId);
    }

    public List<Film> getTopNFilms(Integer count){
        List<Film> films = this.getAll();
        films.sort(Comparator.comparingInt(film -> film.getLikes().size()));
        return films.subList(0, count==null?10:count);
    }

    private void validate(Film film) {
        if (film.getName()==null||film.getName().isEmpty()) {
            log.error("Name не может быть пустым");
            throw new ValidationException("Name не может быть пустым");
        }
        if (film.getDescription()!=null && film.getDescription().length()>MAX_DESCRIPTION_LENGTH) {
            log.error("Максимальная длина описания — "+MAX_DESCRIPTION_LENGTH+" символов");
            throw new ValidationException("Максимальная длина описания — "+MAX_DESCRIPTION_LENGTH+" символов");
        }
        if (film.getReleaseDate()!=null && film.getReleaseDate().isBefore(MIN_RELEASE_DATE)){
            log.error("Дата релиза должна быть не раньше 28 декабря 1895 года");
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года");
        }
        if (film.getDuration()!=null && film.getDuration()<0) {
            log.error("Продолжительность фильма должна быть положительной");
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
    }
}
