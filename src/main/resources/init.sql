create database if not exists kataWorkDB;
use kataWorkDB;
CREATE TABLE if not exists Users
(
    id            int primary key auto_increment,
    user_name     varchar(100) NOT NULL,
    password      varchar(100) NOT NULL,
    year_of_birth int          NOT NULL,
    email         varchar(100)
);
create table if not exists roles
(
    id   int primary key auto_increment,
    name varchar(100) not null
);
create table if not exists users_roles
(
    user_id int not null,
    role_id int not null,
    primary key (user_id, role_id),
    foreign key (user_id) references Users (id),
    foreign key (role_id) references roles (id)
);
insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');
insert into Users (user_name, password, year_of_birth, email)
values ('root', '$2y$10$N1jKvfXvvSz0JBos3amWOehI/1xTjQRvyV8qMz5voBLOdIia7E.zq', 2000, 'root@ya.ru');
insert into users_roles (user_id, role_id)
values (1, 1),
       (1, 2);