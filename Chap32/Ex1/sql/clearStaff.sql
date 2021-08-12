drop function if exists clearStaff;

delimiter //

create function clearStaff(id char(9))
	returns boolean
    reads sql data
    deterministic
begin
	declare result int;
    
    delete from Staff where Staff.id = id;
    
    select count(*) into result from Staff where Staff.id = id;
    
    if result <= 0 then
		return true;
		end if;
	
    return false;
end;
//
delimiter ;