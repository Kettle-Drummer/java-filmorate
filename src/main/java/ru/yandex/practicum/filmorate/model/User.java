package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

@ToString
@Getter
@EqualsAndHashCode
public class User {
    @Setter
    private int id;
    @Email(message = "электронная почта не может быть пустой и должна содержать символ @")
    private final String email;
    @Pattern(regexp = "^\\S+$", message = "логин не может быть пустым и содержать пробелы")
    private final String login;
    @Setter
    @Nullable
    private String name;
    @PastOrPresent(message = "дата рождения не может быть в будущем")
    private final LocalDate birthday;

    public User(int id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        if (name == null || name.isBlank()) {
            this.name = login;
        } else {
            this.name = name;
        }
        this.birthday = birthday;
    }
}