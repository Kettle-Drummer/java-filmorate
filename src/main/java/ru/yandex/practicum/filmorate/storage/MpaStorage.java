package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Map;
import java.util.Optional;

public interface MpaStorage {
    Optional<Mpa> findById(Long id);

    Map<Long, Mpa> findAll();
}
