create database if not exists jdtest;

create table if not exists jdtest.t_user(
uid varchar(255),
age varchar(3),
sex varchar(2),
active_date date,
limit_count double,
primary key (uid)
);