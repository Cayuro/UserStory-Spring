INSERT INTO venues (name, address, city, capacity)
WITH RECURSIVE nums(n) AS (
    SELECT 1
    UNION ALL
    SELECT n + 1 FROM nums WHERE n < 10
)
SELECT
    CONCAT('Seed Venue ', LPAD(CAST(n AS VARCHAR), 2, '0')),
    CONCAT('Address ', n),
    CASE WHEN MOD(n, 2) = 0 THEN 'Bogotá' ELSE 'Medellín' END,
    100 + (n * 50)
FROM nums;

INSERT INTO categories (name, description)
WITH RECURSIVE nums(n) AS (
    SELECT 1
    UNION ALL
    SELECT n + 1 FROM nums WHERE n < 10
)
SELECT
    CONCAT('Seed Category ', LPAD(CAST(n AS VARCHAR), 2, '0')),
    CONCAT('Category description ', n)
FROM nums;

INSERT INTO events (name, date, description, venue_id, active)
WITH RECURSIVE nums(n) AS (
    SELECT 1
    UNION ALL
    SELECT n + 1 FROM nums WHERE n < 100
)
SELECT
    CONCAT('Seed Event ', LPAD(CAST(n AS VARCHAR), 3, '0')),
    CONCAT('2026-', LPAD(CAST(((n - 1) % 12) + 1 AS VARCHAR), 2, '0'), '-', LPAD(CAST(((n - 1) % 28) + 1 AS VARCHAR), 2, '0')),
    CONCAT('Generated event ', n),
    (SELECT id FROM venues WHERE name = CONCAT('Seed Venue ', LPAD(CAST(((n - 1) % 10) + 1 AS VARCHAR), 2, '0'))),
    TRUE
FROM nums;

INSERT INTO events_categories (event_id, category_id)
WITH RECURSIVE nums(n) AS (
    SELECT 1
    UNION ALL
    SELECT n + 1 FROM nums WHERE n < 100
)
SELECT
    e.id,
    c.id
FROM nums
JOIN events e
    ON e.name = CONCAT('Seed Event ', LPAD(CAST(n AS VARCHAR), 3, '0'))
JOIN categories c
    ON c.name = CONCAT('Seed Category ', LPAD(CAST(((n - 1) % 10) + 1 AS VARCHAR), 2, '0'));
