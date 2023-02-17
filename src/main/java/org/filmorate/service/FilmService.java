package org.filmorate.service;

import org.filmorate.model.Film;
import org.filmorate.model.User;
import org.filmorate.storage.FilmStorage;
import org.filmorate.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class FilmService {

    private final FilmStorage storage;

    public FilmService(@Autowired FilmStorage storage) {
        this.storage = storage;
    }

    public List<Film> getAll(){
        return storage.findAll();
    }

    public Film getById(Long id){
        return storage.findById(id);
    }

    public Film add(Film film){
        return storage.insert(film);
    }

    public Film update(Film film) throws Exception {
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
}
