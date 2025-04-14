CREATE TABLE author
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL UNIQUE
);

CREATE TABLE book
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(200) NOT NULL,
    description VARCHAR(200),
    author_id   BIGINT       NOT NULL,
    FOREIGN KEY (author_id) REFERENCES author (id) ON DELETE CASCADE
);
