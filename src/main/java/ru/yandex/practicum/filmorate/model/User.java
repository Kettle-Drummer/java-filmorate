package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import lombok.*;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @EqualsAndHashCode.Exclude
    private int id;
    @NonNull
    @Email(message = "электронная почта не может быть пустой и должна содержать символ @")
    private String email;
    @Pattern(regexp = "^\\S+$", message = "логин не может быть пустым и содержать пробелы")
    private String login;
    @Nullable
    private String name;
    @PastOrPresent(message = "дата рождения не может быть в будущем")
    private  LocalDate birthday;
}