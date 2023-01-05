DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS groups;
DROP SEQUENCE IF EXISTS user_seq;
CREATE SEQUENCE user_seq start 1000;

CREATE TABLE groups
(
    id      BIGSERIAL PRIMARY KEY,
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
    id      BIGINT PRIMARY KEY DEFAULT nextval('user_seq'),
    name    VARCHAR(30) NOT NULL,
    surname varchar(30) Not NULL,
    age integer NOT NULL CHECK ( age > 0 AND age < 150),
    group_id BIGINT REFERENCES groups(id) ON DELETE CASCADE ,
    status bool DEFAULT false
);

insert into users(id, name, surname,group_id, age, status)
VALUES (100, 'Makise', 'Kurisu', 1, 19, false);


insert into users(name, surname, group_id, age)
VALUES ('satoshi', 'yamamoto',1 ,  40);
insert into users(name, surname, group_id, age)
VALUES ('hidetoshi', 'osia',1 ,  40);
insert into users(name, surname, group_id, age)
VALUES ('hideo', 'kodzima',1 ,  40);

insert into users(id, name, surname,group_id, age, status)
VALUES (101, 'Vladimir', 'Lenin', 2, 40, false);
insert into users(id, name, surname,group_id, age, status)
VALUES (103, 'Vladimir', 'Zhirinovsky', 2, 80, false);
insert into users(name, surname, group_id, age)
VALUES ('Dmitry', 'Rogozin',2 ,  40);

insert into users(id, name, surname,group_id, age, status)
VALUES (102, 'Jared', 'Leto', 3, 40, false);
insert into users(name, surname, group_id, age)
VALUES ('Harry', 'Styles',3 ,  40);
insert into users(name, surname,group_id, age)
VALUES ('Barry', 'White',3 , 40);

insert into users(name, surname,group_id, age)
VALUES ('Vladimir', 'Visockiy',3 , 40);