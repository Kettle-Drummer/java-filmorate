package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int id;

    private int createId() {
        return ++id;
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        user.setId(createId());
        users.put(user.getId(), user);
        log.info("Пользователь создан: {}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("Пользователь обновлен: {}", user);
            return user;
        }
        throw new ValidationException("Такого пользователя нет", HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public List<User> findAllUsers() {
        log.info("Выведены все пользователи");
        return new ArrayList<>(users.values());
    }
}