DROP TABLE IF EXISTS BOOKS;
DROP TABLE IF EXISTS GENRE;
DROP TABLE IF EXISTS AUTHOR;
CREATE TABLE BOOKS
(
    ID   BIGINT PRIMARY KEY,
    NAME VARCHAR(255),
    GENRE VARCHAR (255),
    AUTHOR VARCHAR (255),
    CONSTRAINT UC_Book UNIQUE (NAME, GENRE, AUTHOR)
);

CREATE TABLE GENRES
(
    ID    BIGINT PRIMARY KEY,
    GENRE VARCHAR(255) UNIQUE
);

CREATE TABLE AUTHORS
(
    ID    BIGINT PRIMARY KEY,
    AUTHOR VARCHAR(255) UNIQUE
)