package org.filmorate;

import org.filmorate.controller.UserController;
import org.filmorate.exceptions.ValidationException;
import org.filmorate.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserControllerTest {
    @Autowired
    UserController userController;
    User user = new User();

    @BeforeEach
    public void setUp() {
        user.setId(1);
        user.setName("Название");
        user.setLogin("login");
        user.setEmail("email@mail.com");
        user.setBirthday(LocalDate.of(1990, 1, 1));
    }

    @Test
    public void validationUserPutTest_Ok() throws ValidationException {
        assertEquals(userController.post(user), user);
    }

    @Test
    public void validationUserPutTest_ValidationException_EmailEmpty() throws ValidationException {
        user.setEmail("");
        Exception exception = assertThrows(ValidationException.class, () -> {
            userController.post(user);
        });
        String expectedMessage = "Email не может быть пустым";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void validationUserPutTest_ValidationException_EmailNotHasAt() throws ValidationException {
        user.setEmail("email.com");
        Exception exception = assertThrows(ValidationException.class, () -> {
            userController.post(user);
        });
        String expectedMessage = "Email должен содержать символ @";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void validationUserPutTest_ValidationException_LoginEmpty() throws ValidationException {
        user.setLogin("");
        Exception exception = assertThrows(ValidationException.class, () -> {
            userController.post(user);
        });
        String expectedMessage = "Login не может быть пустым";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void validationUserPutTest_ValidationException_LoginHasSpace() throws ValidationException {
        user.setLogin("log in");
        Exception exception = assertThrows(ValidationException.class, () -> {
            userController.post(user);
        });
        String expectedMessage = "Login не может содержать пробелы";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void validationUserPutTest_ValidationException_BirthdayInFuture() throws ValidationException {
        user.setBirthday(LocalDate.now().plusDays(1));
        Exception exception = assertThrows(ValidationException.class, () -> {
            userController.post(user);
        });
        String expectedMessage = "Birthday не может быть в будущем";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}