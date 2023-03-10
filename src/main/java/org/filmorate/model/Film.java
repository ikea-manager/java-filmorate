package org.filmorate.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Film {
    public static int newId = 0;
    private long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Long duration;
    private Set<Long> likes = new HashSet<>();
    private int rate;
    private Mpa mpa;
    private Set<Genre> genres = new HashSet<>();
}
