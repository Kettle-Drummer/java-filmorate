package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService userService) {
        this.service = userService;
    }
    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        return service.create(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        return service.update(user);
    }

    @GetMapping
    public List<User> findAllUsers() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable("id") Long id) {
        return service.findUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable("id") Long id,
                          @PathVariable("friendId") Long friendId) {
        return service.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeFriend(@PathVariable("id") Long userId,
                             @PathVariable("friendId") Long friendId) {
        return service.removeFriend(userId, friendId);
    }

    @GetMapping("{id}/friends")
    public List<User> findFriends(@PathVariable("id") Long userId) {
        return service.findFriends(userId);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public List<User> findCommonFriends(@PathVariable("id") Long userId,
                                        @PathVariable("otherId") Long otherUserId) {
        return service.findCommonFriends(userId, otherUserId);
    }
}