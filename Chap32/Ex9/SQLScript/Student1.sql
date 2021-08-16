drop table if exists Student1;

create table Student1 (
	username varchar(59) not null,
    password varchar(50) not null,
    fullname varchar(200) not null,
    constraint pkStudent primary key (username)
);