package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final FilmService service;

    @Autowired
    public FilmController(FilmService filmService) {
        this.service = filmService;
    }
    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        return service.create(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return service.update(film);
    }

    @GetMapping(produces = "application/json")
    public List<Film> findAllFilms() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Film findFilmById(@PathVariable("id") Long id) {
        return service.findFilmById(id);
    }

    @PutMapping("{id}/like/{userId}")
    public Film addLike(@PathVariable("id") Long filmId,
                        @PathVariable("userId") Long userId) {
        return service.addLike(filmId, userId);
    }

    @DeleteMapping("{id}/like/{userId}")
    public Film removeLike(
            @PathVariable("id") Long filmId,
            @PathVariable("userId") Long userId) {
        return service.removeLike(filmId, userId);
    }

    @GetMapping("/popular")
    public List<Film> findPopularFilms(
            @RequestParam(defaultValue = "10", required = false) Integer count) {
        return service.findPopularFilms(count);
    }
}