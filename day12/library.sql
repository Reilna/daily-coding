CREATE TABLE readers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    price NUMERIC(10,2) CHECK ( price > 0 ) NOT NULL,
    reader_id INTEGER REFERENCES readers(id)
);

INSERT INTO readers (name)
VALUES
('Андрей'),
('Варя'),
('Маша');

INSERT INTO books (title, price, reader_id)
VALUES
('Норвежский лес', 3000.00, 1),
('Мёртвые души', 300.00, 1),
('Война и мир', 400.00, 2),
('Архив', 40000.00, NULL);

SELECT
    books.title,
    readers.name
FROM books
JOIN readers ON books.reader_id = readers.id;

SELECT
    r.id AS reader_id,
    r.name AS reader_name,
    COUNT(b.id) AS books_count,
    COALESCE(SUM(b.price), 0) AS total_price
FROM readers r
LEFT JOIN books b ON r.id = b.reader_id
GROUP BY r.id, r.name;

SELECT
    r.id AS reader_id,
    r.name AS reader_name,
    COUNT(b.id) AS books_count,
    COALESCE(SUM(b.price), 0) AS total_price
FROM readers r
         LEFT JOIN books b ON r.id = b.reader_id
GROUP BY r.id, r.name
HAVING COUNT(b.id) >= 2;
