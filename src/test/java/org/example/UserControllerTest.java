package org.example;

import org.example.controller.UserController;
import org.example.exceptions.ValidationException;
import org.example.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerTest {
    @Autowired
    UserController userController;
    @Test
    public void validationUserPutTest() throws ValidationException {
        User user = new User();
        user.setId(1);
        user.setName("Название");
        user.setLogin("login");
        user.setEmail("email@mail.com");
        user.setBirthday(LocalDate.of(1990,1,1));

        assertEquals(userController.post(user), user);

        user.setEmail("");
        Exception exception = assertThrows(ValidationException.class, () -> {
            userController.post(user);
        });
        String expectedMessage = "Email не может быть пустым";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        user.setEmail("email@mail.com");

        user.setEmail("email.com");
        exception = assertThrows(ValidationException.class, () -> {
            userController.post(user);
        });
        expectedMessage = "Email должен содержать символ @";
        actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        user.setEmail("email@mail.com");

        user.setLogin("");
        exception = assertThrows(ValidationException.class, () -> {
            userController.post(user);
        });
        expectedMessage = "Login не может быть пустым";
        actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        user.setLogin("login");

        user.setLogin("log in");
        exception = assertThrows(ValidationException.class, () -> {
            userController.post(user);
        });
        expectedMessage = "Login не может содержать пробелы";
        actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        user.setLogin("login");

        user.setBirthday(LocalDate.now().plusDays(1));
        exception = assertThrows(ValidationException.class, () -> {
            userController.post(user);
        });
        expectedMessage = "Birthday не может быть в будущем";
        actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        user.setBirthday(LocalDate.of(1990,1,1));
    }
}