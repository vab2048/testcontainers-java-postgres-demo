CREATE TABLE IF NOT EXISTS book
(
    id      SERIAL PRIMARY KEY,
    name    TEXT NOT NULL,
    status  TEXT NOT NULL
);