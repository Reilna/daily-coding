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

Есть 3 профиля: `dev` для локального PostgreSQL, `test` для запуска тестов с H2, `prod` для PostgreSQL в Docker.
1. Создать базу данных `java_learning` в PostgreSQL на `localhost:5432` с пользователем `postgres`.
2. Указать переменную окружения `DB_PASSWORD`, можно задать в конфигурации запуска IntelliJ.
```powershell
$env:SPRING_PROFILES_ACTIVE="dev"
$env:DB_PASSWORD="пароль"
.\mvnw.cmd spring-boot:run
```
3. Запустить класс `BookApiApplication`.
4. API будет доступен по адресу `http://localhost:8080`.


## Запуск через Docker

1. Понадобится `Java 21` и запущенный `Docker Desktop`.
2. `Compose` автоматически активирует `prod`.
3. Указать пароль от `БД` как `DB_PASSWORD`, можно прописать в `.env` рядом с `compose.yml`.
4. Собрать приложение и запустить контейнеры.

```powershell
.\mvnw.cmd clean package
docker compose up -d --build
```
5. API доступен по адресу `http://localhost:8080/books`.
6. Остановить проект можно с помощью `docker compose down`.
7. Данные от `PostgreSQL` сохраняются в volume между запусками. Команда `docker compose down -v` удаляет volume вместе с данными.
8. Команды выполняются из папки `day14/book-api`.

## Healthcheck

- `/actuator/health` - общий статус приложения и подключений.
- `/actuator/health/liveness` - приложение запущено.
- `/actuator/health/readiness` - приложение готово принимать запросы и подключено к PostgreSql.
- Docker проверяет readiness каждые 10 секунд.
- Если PostgreSQL недоступна, `app` получает статус `unhealthy`.

## Тесты

- Интеграционные тесты используют профиль `test` и `H2` в памяти и не требуют запущенной `PostgreSQL`.

```powershell
.\mvnw.cmd test
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