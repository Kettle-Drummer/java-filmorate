package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private long id;

    private long createId() {
        return ++id;
    }

    public User create(User user) {
        user.setId(createId());
        users.put(user.getId(), user);
        log.info("Пользователь создан: {}", user);
        return user;
    }

    public User update(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("Пользователь обновлен: {}", user);
            return user;
        } else {
            log.info("Не удалось обновить пользователя " + user);
            throw new ResourceNotFoundException();
        }
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public User findUserById(Long id) {
        if (users.containsKey(id)) {
            return users.get(id);
        } else
        throw new ResourceNotFoundException();
        }
}
