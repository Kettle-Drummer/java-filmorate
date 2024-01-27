# java-filmorate

Изображение структуры базы данных:

![Структура базы данных](/readme_files/database.png)

Примеры Sql запросов:

- получение всех фильмов по жанру
```
SELECT
    id,
    name
FROM films
RIGHT JOIN film_genre ON film.id = film_genre.film_id 
WHERE genre = 'Драма';
```
- получение топ-10 фильмов по количеству лайков
```
SELECT films.id,
       films.name
       COUNT(user_id) AS count_of_likes
FROM film_likes
LEFT JOIN film ON film_likes.film_id = film.id
GROUP BY films.id
ORDER BY count_of_likes DESC
LIMIT 10;
```