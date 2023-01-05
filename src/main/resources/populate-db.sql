DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS groups;
DROP SEQUENCE IF EXISTS user_seq;
CREATE SEQUENCE user_seq start 1000;

CREATE TABLE groups
(
    id          BIGSERIAL PRIMARY KEY,
    group_color varchar(30) NOT NULL UNIQUE
);

insert into groups(group_color)
VALUES ('RED');
insert into groups(group_color)
VALUES ('BLUE');
insert into groups(group_color)
VALUES ('GREEN');

CREATE TABLE users
(
    id       BIGINT PRIMARY KEY DEFAULT nextval('user_seq'),
    name     VARCHAR(30) NOT NULL,
    surname  varchar(30) Not NULL,
    age      integer     NOT NULL CHECK ( age > 0 AND age < 150),
    group_id BIGINT REFERENCES groups (id) ON DELETE CASCADE,
    status   bool
);

insert into users(id, name, surname, group_id, age, status)
VALUES (100, 'Makise', 'Kurisu', 1, 19, false);

insert into users(id, name, surname, group_id, age, status)
VALUES (101, 'Vladimir', 'Lenin', 2, 40, false);

insert into users(id, name, surname, age, status)
VALUES (102, 'Jared', 'Leto', 40, false);


