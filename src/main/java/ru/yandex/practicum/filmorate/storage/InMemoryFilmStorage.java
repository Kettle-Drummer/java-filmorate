package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    public Film create(Film film) {
        film.setId(createId());
        films.put(film.getId(), film);
        log.info("Фильм добавлен: {}", film);
        return film;
    }

    public Film update(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Фильм обновлен");
            return film;
        } else {
            throw new ResourceNotFoundException("Фильм не найден. Id= " + film.getId());
        }
    }

    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    public Film findFilmById(Long id) {
        if (films.containsKey(id)) {
            return films.get(id);
        } else
            throw new ResourceNotFoundException("Фильм не найден. Id= " + id);
    }

    private final Map<Long, Film> films = new HashMap<>();
    private long id;

    private long createId() {
        return ++id;
    }
}
