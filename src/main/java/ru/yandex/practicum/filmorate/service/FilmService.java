package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {
    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
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

        film.getLikeId().add(userId);
        log.info("Фильму {} поставлен лайк пользователя {}", film.getName(), user.getName());
        return filmStorage.findFilmById(filmId);
    }

    public Film removeLike(Long filmId, Long userId) {
        User user = userStorage.findUserById(userId);
        Film film = filmStorage.findFilmById(filmId);

        film.getLikeId().remove(userId);
        log.info("У фильма {} снят лайк пользователя {}", film.getName(), user.getName());
        return filmStorage.findFilmById(filmId);
    }

    public List<Film> findPopularFilms(Integer count) {
        return filmStorage.findAll().stream()
                .sorted((film1, film2) -> film2.getLikeId().size() - film1.getLikeId().size())
                .limit(count)
                .collect(Collectors.toList());
    }

    private final FilmStorage filmStorage;

    private final UserStorage userStorage;
}
