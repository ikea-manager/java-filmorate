package org.filmorate.storage;

import org.filmorate.exceptions.NotFoundException;
import org.filmorate.model.Mpa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
class MpaDbStorageTest {

    @Autowired
    private MpaStorage mpaStorage;

    @Test
    void findAll() {
        List<Mpa> list = mpaStorage.findAll();
        assertNotNull(list);
        assertNotEquals(0, list.size());
    }

    @Test
    void findById() {
        Mpa mpa = mpaStorage.findById(2l);
        assertNotNull(mpa);
        assertEquals("PG", mpa.getName());

        assertThrows(NotFoundException.class, ()->{mpaStorage.findById(100l);});
    }

    @Test
    void insert() {
        Mpa mpa = new Mpa();
        mpa.setName("Unknown");
        mpaStorage.insert(mpa);
        Mpa newMpa = mpaStorage.findById(mpa.getId());
        assertEquals(mpa,newMpa);
    }

    @Test
    void update() {
        Mpa mpa = mpaStorage.findById(3l);
        mpa.setName("Updated");
        mpaStorage.update(mpa);
        Mpa updMpa = mpaStorage.findById(mpa.getId());
        assertEquals(mpa, updMpa);
    }
}