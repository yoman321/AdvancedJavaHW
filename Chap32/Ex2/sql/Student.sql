drop table if exists Student;

create table Student(
	ssn char(9) not null,
    firstName varchar(19),
    mi char(1),
    lastName varchar(15),
    phone char(10),
    birthdate char(10),
    street varchar (30),
    zip char(6),
    dept varchar(4),
    primary key (ssn)
);

insert into Student (ssn, firstName, mi, lastName, phone, birthdate, street, zip, dept)
values 
	(444111110, "Jacob", "R", "Smith", 9129219434, 1985-04-09, "99 Kingston Street", 31435, "BIOL"),
	(444111111, "John", "K", "Stevenson", 9129219434, "null", "100 Main Street", 31411, "BIOL"),
    (444111112, "George", "K", "Smith", 9129213454, 1974-10-10, "1200 Abercorn St.", 31419, "CS"),
    (444111113, "Frank", 'E', 'Jones', 9125919434, 1970-09-09, "100 Main Street", 31411, "BIOL"),
    (444111114, "Jean", 'K', "Smith", 9129219434, 1970-02-09, "100 Main Street", 31411, "CHEM"),
    (444111115, "Josh", "R", "Woo", 7075989434, 1970-02-09, "555 Franklin St.", 31411, "CHEM"),
    (444111116, "Josh", "R", "Smith", 9129219434, 1973-02-09, "100 Main Street", 31411, "BIOL"),
    (444111117, "Joy", "P", "Kennedy", 9129229434, 1974-03-19 ,"103 Bay Street", 31412, "CS"),
    (444111118, "Toni", "R", "Peterson", 9129229434, 1964-04-29, "103 Bay Street", 31412, 'MATH'),
    (444111119, 'Patrick', 'R', 'Stoneman', 9129229434, 1969-04-29, '101 Washington St.', 31435, 'MATH'),
	(444111120, 'Rick', 'R', 'Carter', 9125919434, 1986-04-09, '19 West Ford St.', 31411, 'BIOL');

