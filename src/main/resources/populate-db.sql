DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS user_seq;
CREATE TYPE group_color AS ENUM ('Green', 'Red', 'Blue');
CREATE SEQUENCE user_seq start 1000;

CREATE TABLE users
(
    id      BIGINT PRIMARY KEY DEFAULT nextval('user_seq'),
    name    VARCHAR(30) NOT NULL,
    surname varchar(30) Not NULL,
    age integer NOT NULL CHECK ( age > 0 AND age < 150),
    group_id INTEGER NOT NULL,
    FOREIGN KEY (group_id) REFERENCES groups(id),
    status bool
);

CREATE TABLE groups
(
    id      BIGINT PRIMARY KEY,
    group_color group_color
);

insert into groups(group_color)
VALUES ('RED');
insert into groups(group_color)
VALUES ('BLUE');
insert into groups(group_color)
VALUES ('GREEN');

insert into users(id, name, surname,group_id, age)
VALUES (100, 'Makise', 'Kurisu', 1, 19);

insert into users(id, name, surname,group_id, age)
VALUES (101, 'Vladimir', 'Lenin', 2, 40);

