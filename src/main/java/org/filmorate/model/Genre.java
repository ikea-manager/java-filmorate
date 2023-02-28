package org.filmorate.model;

import lombok.Data;

@Data
public class Genre implements Comparable<Genre> {
    private long id;
    private String name;

    @Override
    public int compareTo(Genre genre) {
        return (int)(id-genre.getId());
    }
}
