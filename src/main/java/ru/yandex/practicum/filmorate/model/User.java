package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @EqualsAndHashCode.Exclude
    private Long id;
    @NotNull
    @NotEmpty
    @Email(message = "электронная почта не может быть пустой и должна содержать символ @")
    private String email;
    @NotBlank
    @Pattern(regexp = "^\\S+$", message = "логин не может быть пустым и содержать пробелы")
    private String login;
    @Nullable
    private String name;
    @NotNull
    @PastOrPresent(message = "дата рождения не может быть в будущем")
    private  LocalDate birthday;
    @JsonIgnore
    private Set<Long> friendsId = new HashSet<>();
}