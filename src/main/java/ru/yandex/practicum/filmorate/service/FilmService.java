package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(@Valid @RequestBody Film film) {
        return filmStorage.update(film);
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film findFilmById(Long id) {
        return filmStorage.findFilmById(id);
    }

    public Film addLike(Long filmId, Long userId) {
        User user = userStorage.findUserById(userId);
        Film film = filmStorage.findFilmById(filmId);
        if (user != null && film != null) {
        film.getLikeId().add(userId);
        log.info("Фильму " + film.getName() + " поставлен лайк пользователя " + user.getName());
        return filmStorage.findFilmById(filmId);
        } else {
            log.info("Такой пары фильм-пользователь нет");
            throw new ResourceNotFoundException();
        }

    }

    public Film removeLike(Long filmId, Long userId) {
        User user = userStorage.findUserById(userId);
        Film film = filmStorage.findFilmById(filmId);
        if (user != null && film != null) {
            film.getLikeId().remove(userId);
            log.info("У фильма " + film.getName() + " снят лайк пользователя " + user.getName());
            return filmStorage.findFilmById(filmId);
        } else
            log.info("Такой пары фильм-пользователь нет");
        throw new ResourceNotFoundException();
    }

    public List<Film> findPopularFilms(Integer count) {
        return filmStorage.findAll().stream()
                .sorted((film1, film2) -> film2.getLikeId().size() - film1.getLikeId().size())
                .limit(count)
                .collect(Collectors.toList());
    }
}
