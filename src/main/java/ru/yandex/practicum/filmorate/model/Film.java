package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import ru.yandex.practicum.filmorate.validator.ValidReleaseDate;


@Data
public class Film {
    @NonNull
    @Setter
    private int id;
    @NotBlank(message = "название не может быть пустым")
    private final String name;
    @Size(max = 200, message = "максимальная длина описания — 200 символов")
    private final String description;
    @ValidReleaseDate(message = "дата релиза — не раньше 28 декабря 1895 года")
    private final LocalDate releaseDate;
    @Positive(message = "продолжительность фильма должна быть положительной")
    private final int duration;
}