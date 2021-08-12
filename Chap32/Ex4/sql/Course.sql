drop table if exists Course;

create table Course(
	courseId char(5) not null,
    subjectId char(4) not null,
    courseNumber char(4) not null,
    title varchar(25) not null,
    numOfCredits char(1) not null,
    primary key (courseId)
);

insert into Course (courseId, subjectId, courseNumber, title, numOfCredits)
values 
	(11111, "CSCI", 1301, "Introduction to Java I", 4),
	(11112, "CSCI", 1302, "Introduction to Java II", 3),
	(11113, "CSCI", 3720, "Database Systems", 3),
	(11114, "CSCI", 4750, "Rapid Java Application", 3),
	(11115, "MATH", 2750, "Calculus I", 5),
	(11116, "MATH", 3750, "Calculus II", 5),
	(11117, "EDUC", 1111, "Reading", 3),
	(11118, "ITEC", 1344, "Database Administration", 3);
	