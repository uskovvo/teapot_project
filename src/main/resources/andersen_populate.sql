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
    age      integer     NOT NULL CHECK ( age >= 0 AND age < 150) DEFAULT 0,
    group_id BIGINT REFERENCES groups (id) ON DELETE CASCADE,
    status   bool  DEFAULT false
);

insert into users(name, surname, group_id)
VALUES ('Валерий', 'Усков', 1);

insert into users(name, surname, group_id)
VALUES ('Роман', 'Соколов', 1);

insert into users(name, surname, group_id)
VALUES ('Михаил', 'Апенко', 1);

insert into users(name, surname, group_id)
VALUES ('Федор', 'Ермаков', 1);

insert into users(name, surname)
VALUES ('Полина', 'Белквич');

insert into users(name, surname)
VALUES ('Андрей', 'Пштыр');

insert into users(name, surname)
VALUES ('Павел', 'Поляков');

insert into users(name, surname)
VALUES ('Сергей', 'Лосев');

insert into users(name, surname)
VALUES ('Владимир', 'Круль');

insert into users(name, surname)
VALUES ('Вадим', 'Уточнить');

insert into users(name, surname)
VALUES ('Егор', 'Макарьев');

insert into users(name, surname)
VALUES ('Зиля', 'Нурисламова');

insert into users(name, surname)
VALUES ('Владислав', 'Немиро');
