drop function if exists updateStaff;

delimiter //

create function updateStaff(id char(9), lastName varchar(15), firstName varchar(15), mi char(1), address varchar(20), city varchar(20), state char(2), telephone char(10), email varchar(49))
	returns INTEGER
    READS SQL DATA
	DETERMINISTIC
begin
	declare result int;
    
    select count(*) into result from Staff where Staff.id = id;
    
    if result >= 1 then
		update Staff
		set Staff.firstName = firstName, Staff.lastName = lastName, Staff.mi = mi, Staff.address = address, Staff.city = city, Staff.state = state, Staff.telephone = telephone, Staff.email = email
        where Staff.id = id;
        
        return 1;
        
        end if;
        
	return 0;

end;

//

delimiter ;
