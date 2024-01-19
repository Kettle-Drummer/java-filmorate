package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    public User create(User user) {
        FillEmptyName(user);
        return userStorage.create(user);
    }

    public User update(User user) {
        FillEmptyName(user);
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

            if (!id.equals(friendId)) {
                user.getFriendsId().add(friendId);
                friend.getFriendsId().add(id);
                log.info("Пользователи {} и {} теперь друзья", user.getName(), friend.getName());
                return friend;
            } else {
               throw new ResourceNotFoundException("Нет такой пары пользователей");
            }

    }

    public User removeFriend(Long id, Long friendId) {
        User user = userStorage.findUserById(id);
        User friend = userStorage.findUserById(friendId);

        if (!id.equals(friendId)) {
            user.getFriendsId().remove(friendId);
            friend.getFriendsId().remove(id);
            log.info("Пользователи {} и {} больше не друзья", user.getName(), friend.getName());
            return friend;
        } else {
            throw new ResourceNotFoundException("Нет такой пары пользователей");
        }
    }

    public List<User> findFriends(Long userId) {
            return userStorage.findAll().stream()
                    .filter(user -> user.getFriendsId().contains(userId))
                    .collect(Collectors.toList());

    }

    public List<User> findCommonFriends(Long id, Long otherUserId) {
        User user = userStorage.findUserById(id);
        User friend = userStorage.findUserById(otherUserId);

        if (!id.equals(otherUserId)) {
            Set<Long> commonFriends = new HashSet<>(user.getFriendsId());
            commonFriends.retainAll(friend.getFriendsId());
            List<User> result = new ArrayList<>();
            for (Long friendId : commonFriends) {
                result.add(userStorage.findUserById(friendId));
            }
            return result;
        } else {
            throw new ResourceNotFoundException("Нет такой пары пользователей");
        }
    }

    private void FillEmptyName(User user) {
        if (Objects.isNull(user.getName()) || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    private final UserStorage userStorage;
}
