insert into authors (id, `author`)
values (1, 'oba');

insert into authors (id, `author`)
values (2, 'vachovski');

insert into genres (`id`, `genre`)
values (1, 'triller');

insert into genres (`id`, `genre`)
values (2, 'anime');

insert into genres (`id`, `genre`)
values (3, 'fantastic');

insert into genres (`id`, `genre`)
values (4, 'tttx');

insert into genres (`id`, `genre`)
values (5, 'ttx23');

insert into BOOKS (id, `name`, `author_id`)
values (1, 'matrix', 2);

insert into BOOKS (id, `name`, `author_id`)
values (2, 'death note', 1);

insert into BOOKS_GENRES (id, `book_id`, `genre_id`)
values (1, 2, 2);

insert into BOOKS_GENRES (id, `book_id`, `genre_id`)
values (3, 1, 1);

insert into BOOKS_GENRES (id, `book_id`, `genre_id`)
values (2, 1, 3);

insert into BOOKS_GENRES (id, `book_id`, `genre_id`)
values (4, 1, 4);

insert into BOOKS_GENRES (id, `book_id`, `genre_id`)
values (5, 1, 5);

insert into COMMENTS (id, `book_id`, `comment`)
values (1, 1, 'test comment 1');

insert into COMMENTS (id, `book_id`, `comment`)
values (2, 1, 'test comment 2');

insert into COMMENTS (id, `book_id`, `comment`)
values (3, 1, 'test comment 3');
