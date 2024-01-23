package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService;


    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        if (filmStorage.findById(film.getId()) == null) {
            throw new ResourceNotFoundException("Фильм не найден");
        }
        return filmStorage.update(film);
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film findById(Long id) {
        Film film = filmStorage.findById(id);
        if (film == null) {
            throw new ResourceNotFoundException("Фильм не найден");
        }
        return film;
    }

    public Film addLike(Long filmId, Long userId) {
        if (filmStorage.findById(filmId) == null) {
            throw new ResourceNotFoundException("Фильм не найден");
        }
        if (userService.findById(userId) == null) {
            throw new ResourceNotFoundException("Пользователь не найден");
        }
        filmStorage.addLike(filmId, userId);
        return filmStorage.findById(filmId);
    }

    public Film removeLike(Long filmId, Long userId) {
        if (filmStorage.findById(filmId) == null) {
            throw new ResourceNotFoundException("Фильм не найден");
        }
        if (userService.findById(userId) == null) {
            throw new ResourceNotFoundException("Пользователь не найден");
        }
        filmStorage.deleteLike(filmId, userId);
        return filmStorage.findById(filmId);
    }

    public List<Film> findPopular(Integer count) {
        List<Film> films = filmStorage.findAll();

        Map<Long, Set<Long>> filmLikesMap = filmStorage.getLikesOfFilm(films);

        return films.stream()
                .peek(film -> film.setLikesUser(filmLikesMap.get(film.getId())))
                .sorted((o1, o2) -> o2.getLikesUser().size() - o1.getLikesUser().size())
                .limit(count)
                .collect(Collectors.toList());
    }
}
