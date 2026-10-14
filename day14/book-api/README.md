# Book API

REST API для управления книгами.

## Стек

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Maven
- JUnit 5
- Mockito

## Запуск

1. Создать базу данных `java_learning` в PostgreSQL на `localhost:5432` с пользователем `postgres`.
2. Указать переменную окружения `DB_PASSWORD`, можно задать в конфигурации запуска IntelliJ.
3. Запустить класс `BookApiApplication`.
4. API будет доступен по адресу `http://localhost:8080`.

## Тесты

- Тесты используют `H2` в памяти и не требуют запущенной `PostgreSQL`.

```text
mvn test
```

## Endpoints

| Метод | URL | Назначение |
|---|---|---|
| GET | `/books` | Получить список книг |
| GET | `/books/{id}` | Получить книгу |
| POST | `/books` | Создать книгу |
| PUT | `/books/{id}` | Обновить книгу |
| DELETE | `/books/{id}` | Удалить книгу |

Фильтрация по автору: `GET /books?author=name%20surname`

## Пример создания книги

```json
{
  "title": "Effective Java",
  "author": "Joshua Bloch"
}
```


## Статусы ответов

- `200 OK` — успешное получение или обновление книги.
- `201 Created` — книга создана.
- `204 No Content` — книга удалена, тело ответа отсутствует.
- `400 Bad Request` — некорректные входные данные.
- `404 Not Found` — книга не найдена.