--INSERT BOOKS
INSERT INTO BOOK (book_id, title, ibn, deleted, data_change_created_by, data_change_created_time) values
(1, 'FIRST_BOOK', '1234', 0, 'INIT', CURRENT_TIMESTAMP());
INSERT INTO BOOK (book_id, title, ibn, deleted, data_change_created_by, data_change_created_time) values
(2,'SECOND_BOOK', '5678', 0, 'INIT', CURRENT_TIMESTAMP());
INSERT INTO BOOK (book_id, title, ibn, deleted, data_change_created_by, data_change_created_time) values
(3, 'THIRD_BOOK', '0000', 0, 'INIT', CURRENT_TIMESTAMP());

--INSERT AUTHORS
INSERT INTO AUTHOR(author_id, first_name, second_name, day_of_birth, active, deleted, data_change_created_by, data_change_created_time) values
(1, 'JURE', 'MALEŠ', '1987-11-23', 1, 0, 'INIT', CURRENT_TIMESTAMP());
INSERT INTO AUTHOR(author_id, first_name, second_name, day_of_birth, active, deleted, data_change_created_by, data_change_created_time) values
(2, 'NIKOLINA', 'ANTOLIĆ', '1991-11-15', 1, 0, 'INIT', CURRENT_TIMESTAMP());

--CONNECT AUTHORS & BOOK
INSERT INTO BOOK_AUTHOR (book_author_id, book_id, author_id, deleted, data_change_created_by, data_change_created_time) values
(1, 1, 1, 0, 'INIT', CURRENT_TIMESTAMP());
INSERT INTO BOOK_AUTHOR (book_author_id, book_id, author_id, deleted, data_change_created_by, data_change_created_time) values
(2, 1, 2, 0, 'INIT', CURRENT_TIMESTAMP());
INSERT INTO BOOK_AUTHOR (book_author_id, book_id, author_id, deleted, data_change_created_by, data_change_created_time) values
(3, 2, 1, 0, 'INIT', CURRENT_TIMESTAMP());
INSERT INTO BOOK_AUTHOR (book_author_id, book_id, author_id, deleted, data_change_created_by, data_change_created_time) values
(4, 3, 2, 0, 'INIT', CURRENT_TIMESTAMP());