CREATE DATABASE IF NOT EXISTS noteschedule;

USE noteschedule;

create table users
(
    id       int auto_increment
        primary key,
    username varchar(20)  not null,
    password varchar(100) not null,
    email    varchar(100) not null
);

create table goal
(
    id        int auto_increment
        primary key,
    user_id   int                  not null,
    goal_name varchar(255)         not null,
    done      tinyint(1) default 0 null,
    constraint goal_ibfk_1
        foreign key (user_id) references users (id)
);

create table event
(
    id         int auto_increment
        primary key,
    user_id    int                  not null,
    event_name varchar(255)         not null,
    date_start datetime             not null,
    date_end   datetime             not null,
    done       tinyint(1) default 0 null,
    goal_id    int                  null,
    constraint event_goal_id_fk
        foreign key (goal_id) references goal (id),
    constraint event_ibfk_1
        foreign key (user_id) references users (id)
);

create index user_id
    on goal (user_id);

create table todo
(
    id        int auto_increment
        primary key,
    user_id   int                  not null,
    todo_name varchar(255)         not null,
    date      date                 not null,
    done      tinyint(1) default 0 null,
    goal_id   int                  null,
    constraint todo_goal_id_fk
        foreign key (goal_id) references goal (id),
    constraint todo_ibfk_1
        foreign key (user_id) references users (id)
);

create index user_id
    on todo (user_id);


