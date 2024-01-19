package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User create(User user) {
        if (Objects.isNull(user.getName()) || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    return userStorage.create(user);
    }

    public User update(@Valid @RequestBody User user) {
        userStorage.findUserById(user.getId());
        if (Objects.isNull(user.getName()) || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return userStorage.update(user);
    }

    public List<User> findAll() {
        return userStorage.findAll();
    }

    public User findUserById(Long id) {
        return userStorage.findUserById(id);
    }

    public User addFriend(Long id, Long friendId) {
        User user = userStorage.findUserById(id);
        User friend = userStorage.findUserById(friendId);

            if (user != null && friend != null && !id.equals(friendId)) {
                user.getFriendsId().add(friendId);
                friend.getFriendsId().add(id);
                log.info("Пользователи " + user.getName() +
                        " и " + friend.getName() +
                        " теперь друзья");
                return userStorage.findUserById(friendId);
            } else {
                log.info("Нет такой пары пользователей");
                throw new ResourceNotFoundException();
            }

    }

    public User removeFriend(Long id, Long friendId) {
        User user = userStorage.findUserById(id);
        User friend = userStorage.findUserById(friendId);
        if (user != null && friend != null && !id.equals(friendId)) {
            user.getFriendsId().remove(friendId);
            friend.getFriendsId().remove(id);
            log.info("Дружба " + user.getName() +
                    " и " + friend.getName() +
                    " закончилась, теперь null его лучший друг");
            return userStorage.findUserById(friendId);
        } else {
            log.info("Нет такой пары пользователей");
            throw new ResourceNotFoundException();
        }
    }

    public List<User> findFriends(Long userId) {
        if (userStorage.findUserById(userId) != null) {
            return userStorage.findAll().stream()
                    .filter(user -> user.getFriendsId().contains(userId))
                    .collect(Collectors.toList());
        } else {
            log.info("Такого пользователя нет");
            throw new ResourceNotFoundException();
        }
    }

    public List<User> findCommonFriends(Long id, Long otherUserId) {
        User user = userStorage.findUserById(id);
        User friend = userStorage.findUserById(otherUserId);
        if (user != null && friend != null && !id.equals(otherUserId)) {
            Set<Long> commonFriends = new HashSet<>(user.getFriendsId());
            commonFriends.retainAll(friend.getFriendsId());
            List<User> result = new ArrayList<>();
            for (Long friendId : commonFriends) {
                result.add(userStorage.findUserById(friendId));
            }
            return result;
        } else {
            log.info("Нет такой пары пользователей");
            throw new ResourceNotFoundException();
        }
    }
}
