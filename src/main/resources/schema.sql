DROP TABLE IF EXISTS book_author;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS author;

CREATE TABLE book (
  book_id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(250) NOT NULL,
  ibn VARCHAR(10) NOT NULL,
  deleted BIT,
  data_change_created_by VARCHAR(250) NOT NULL,
  data_change_created_time DATE NOT NULL,
  data_change_last_modified_by VARCHAR(250),
  data_change_Last_time DATE
);

CREATE TABLE author (
  author_id INT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  second_name VARCHAR(250) NOT NULL,
  day_of_birth DATE NOT NULL,
  active BIT,
  deleted BIT,
  data_change_created_by VARCHAR(250) NOT NULL,
  data_change_created_time DATE NOT NULL,
  data_change_last_modified_by VARCHAR(250),
  data_change_Last_time DATE
);

CREATE TABLE book_author(
    book_author_id INT AUTO_INCREMENT PRIMARY KEY,
    author_id INT NOT NULL,
    book_id INT NOT NULL,
    deleted BIT,
    data_change_created_by VARCHAR(250) NOT NULL,
    data_change_created_time DATE NOT NULL,
    data_change_last_modified_by VARCHAR(250),
    data_change_Last_time DATE,
    FOREIGN KEY (author_id) REFERENCES author(author_id),
    FOREIGN KEY (book_id) REFERENCES book(book_id)
);


