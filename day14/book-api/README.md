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
- OpenAPI 3
- Swagger UI
- Flyway
- Prometheus
- Grafana
- Testcontainers

## Запуск

Есть 3 профиля: `dev` для локального PostgreSQL, `test` для PostgreSQL в Testcontainers, `prod` для PostgreSQL в Docker.
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
Сервисы:

- Book API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- Prometheus: http://localhost:9091
- Grafana: http://localhost:3000

5. API доступен по адресу `http://localhost:8080/books`.
6. Остановить проект можно с помощью `docker compose down`.
7. Данные от `PostgreSQL` сохраняются в volume между запусками. Команда `docker compose down -v` удаляет volumes PostgreSQL, Grafana и Prometheus вместе с сохранёнными данными и историей метрик.
8. Команды выполняются из папки `day14/book-api`.

## Healthcheck

- `/actuator/health` - общий статус приложения и подключений.
- `/actuator/health/liveness` - приложение запущено.
- `/actuator/health/readiness` - приложение готово принимать запросы и подключено к PostgreSql.
- Docker проверяет readiness каждые 10 секунд.
- Если PostgreSQL недоступна, `app` получает статус `unhealthy`.

## Prometheus

- Prometheus запускается отдельным Docker-сервисом.
- Конфигурация находится в `prometheus.yml`.
- Prometheus собирает метрики из `/actuator/prometheus`.
- Веб-интерфейс доступен по адресу `http://localhost:9091`.
- Внутри Docker Prometheus работает на порту `9090`.

## Monitoring

- Prometheus собирает метрики из `/actuator/prometheus`.
- Prometheus UI: `http://localhost:9091`.
- Grafana: `http://localhost:3000`.
- Grafana использует Prometheus как источник данных.
- Dashboard показывает скорость HTTP-запросов и ошибки API.
- `books_total` - количество успешно созданных книг.
- Пример PromQL: `rate(books_total[1m])`.
- История Prometheus сохраняется в volume `prometheus_data`.

## Grafana provisioning

- Источник Prometheus настраивается через `grafana/provisioning/datasources/prometheus.yml`.
- Dashboards загружается из `grafana/dashboards/book-api-monitoring.json`.
- Конфигурация dashboard описана в `grafana/provisioning/dashboards/dashboards.yml`.
- Grafana доступна по адресу `http://localhost:3000`.

## Тесты

- Интеграционные тесты используют профиль `test` и PostgreSQL в Testcontainers.
- Flyway применяет миграции, а Hibernate проверяет схему через `ddl-auto=validate`.
- Для запуска тестов требуется запущенный Docker Desktop.

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

## Пагинация, сортировка и фильтрация

Endpoint `GET /books` поддерживает параметры:

- `author` - поиск по части имени автора без учёта регистра;
- `title` - поиск по части названия без учёта регистра;
- `page` - номер страницы, начиная с `0`;
- `size` - размер страницы, максимум `100`;
- `sort` - поле и направление сортировки.

Примеры:

```text
GET /books?page=0&size=10
GET /books?author=ив&page=0&size=10
GET /books?title=java&page=0&size=10
GET /books?author=ив&title=java&sort=title,asc
```

## Swagger / OpenAPI

Интерактивная документация API:

- Swagger UI: http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

Swagger позволяет просматривать endpoints и отправлять запросы через браузер.

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