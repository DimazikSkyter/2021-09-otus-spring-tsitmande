DROP TABLE IF EXISTS BOOKS;
DROP TABLE IF EXISTS GENRE;
DROP TABLE IF EXISTS AUTHOR;

CREATE TABLE GENRES
(
    ID    BIGINT PRIMARY KEY,
    GENRE VARCHAR(255) UNIQUE
);

CREATE TABLE AUTHORS
(
    ID    BIGINT PRIMARY KEY,
    AUTHOR VARCHAR(255) UNIQUE
);

CREATE TABLE BOOKS
(
    ID   BIGINT PRIMARY KEY,
    NAME VARCHAR(255),
    GENRE_ID BIGINT,
    AUTHOR_ID BIGINT,
    CONSTRAINT UC_Book UNIQUE (NAME, GENRE_ID, AUTHOR_ID)
);

ALTER TABLE BOOKS
    ADD CONSTRAINT fk_genre FOREIGN KEY(GENRE_ID) REFERENCES GENRES(id);
ALTER TABLE BOOKS
    ADD CONSTRAINT fk_author FOREIGN KEY(AUTHOR_ID) REFERENCES AUTHORS(id);