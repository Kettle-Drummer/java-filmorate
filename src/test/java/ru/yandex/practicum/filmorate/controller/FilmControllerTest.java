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
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FilmControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    private static Stream<Arguments> films() {
        return Stream.of(
                Arguments.of(new Film(0L,"", "desc", LocalDate.of(2023, 12, 31), 1, new HashSet<>()), HttpStatus.BAD_REQUEST.value()),
                Arguments.of(new Film(1L, "name", "straight up 201 of 1 111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111", LocalDate.parse("2020-10-05"), 30, new HashSet<>()), HttpStatus.BAD_REQUEST.value()),
                Arguments.of(new Film(2L, "name", "desc", LocalDate.of(1, 2, 3), 1, new HashSet<>()), HttpStatus.BAD_REQUEST.value()),
                Arguments.of(new Film(3L, "name", "desc", LocalDate.of(2023, 12, 31), -1, new HashSet<>()), HttpStatus.BAD_REQUEST.value()),
                Arguments.of(new Film(4L, "name", "desc", LocalDate.of(1895, 12, 28), 1, new HashSet<>()), HttpStatus.OK.value()),
                Arguments.of(new Film(), HttpStatus.BAD_REQUEST.value())
        );
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("films")
    void filmValidation(Film film, int status) {
        mockMvc.perform(
                post("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is(status));
    }
}