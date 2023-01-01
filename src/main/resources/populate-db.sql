DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS user_seq;

CREATE SEQUENCE user_seq start 1000;

CREATE TABLE users
(
    id      BIGINT PRIMARY KEY DEFAULT nextval('user_seq'),
    name    VARCHAR(30) NOT NULL,
    surname varchar(30) Not NULL,
    age integer NOT NULL CHECK ( age > 0 AND age < 150)
);


insert into users(id, name, surname, age)
VALUES (100, 'Makise', 'Kurisu', 19);

insert into users(id, name, surname, age)
VALUES (101, 'Vladimir', 'Lenin', 40);