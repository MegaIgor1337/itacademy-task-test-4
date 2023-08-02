create table if not exists users
(
    id         bigint auto_increment
        primary key,
    name       varchar(20) not null,
    surname    varchar(40) not null,
    patronymic varchar(40) not null,
    email      varchar(50) not null,
    role       varchar(50) not null,
    constraint uk_users_email
        unique (email)
);

