drop table if exists Student2;

create table Student2(
	username varchar(50) not null,
    password varchar(50) not null,
    firstname varchar(100) not null,
    lastname varchar(100) not null,
    constraint pkStudent primary key (username)
);