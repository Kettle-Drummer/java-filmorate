package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;


    private static Stream<Arguments> users() {
        return Stream.of(Arguments.of(new User(0, "email.ru", "login", "name", LocalDate.of(1, 2, 3)), HttpStatus.BAD_REQUEST.value()),
                Arguments.of(new User(1, "e@mail.ru", "log in", "name", LocalDate.of(1, 2, 3)), HttpStatus.BAD_REQUEST.value()),
                Arguments.of(new User(2, "e@mail.ru", "login", " ", LocalDate.of(1, 2, 3)), HttpStatus.OK.value()),
                Arguments.of(new User(3, "e@mail.ru", "login", "name", LocalDate.of(2023, 12, 31)), HttpStatus.BAD_REQUEST.value()),
                Arguments.of(new User(), HttpStatus.BAD_REQUEST.value())
        );
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("users")
    void userValidation(User user, int status) {
        mockMvc.perform(
                post("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is(status));
    }
}