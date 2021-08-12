drop function if exists insertStaff;

DELIMITER //

CREATE PROCEDURE insertStaff (id char(9), lastNanme varchar(15), firstName varchar(15), mi char(1), address varchar(20), city varchar(20), state char(2), telephone char(10), email varchar(49))
BEGIN
	insert into Staff() values (id, lastNanme, firstName, mi, address , city, state, telephone, email);
END;
//
DELIMITER ;