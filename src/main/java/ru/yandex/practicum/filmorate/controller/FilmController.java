package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private int id;

    private int createId() {
        return ++id;
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        film.setId(createId());
        films.put(film.getId(), film);
        log.info("Фильм добавлен: {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Фильм обновлен");
            return film;
        } else {
            log.info("Не удалось обновить фильм " + film);
            throw new ValidationException("Такого фильма нет", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(produces = "application/json")
    public List<Film> findAllFilms() {
        log.info("Выведены все фильмы");
        return new ArrayList<>(films.values());
    }
}