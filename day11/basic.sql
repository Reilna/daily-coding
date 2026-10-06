CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    age INT NOT NULL
);

INSERT INTO users (name, age)
VALUES
('Алексей', 25),
('Мария', 17);

SELECT * FROM users;

SELECT * FROM users
WHERE age > 18;

SELECT * FROM users
WHERE age > 18
ORDER BY age ASC;

SELECT * FROM users
WHERE name LIKE 'А%';

SELECT name FROM users;

UPDATE users
SET age = 18
WHERE name = 'Мария';

DELETE FROM users
WHERE age < 18;

SELECT * FROM users;

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    product VARCHAR(100) NOT NULL,
    amount NUMERIC(10, 2) NOT NULL
)

INSERT INTO orders (user_id, product, amount)
VALUES
(1, 'Клавиатура', 3500.00),
(2, 'Мышь', 1500.00);

SELECT * FROM orders;