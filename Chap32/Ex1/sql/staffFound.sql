drop procedure if exists staffFound;

delimiter //

create procedure staffFound(in inId char(9), out outLastName varchar(15), out outFirstName varchar(15), out OutMi char(1), out outAddress varchar(20), out outCity varchar(20), out outState char(2), out outTelephone char(10), out outEmail varchar(40))
begin 
declare result int;
select count(*) into result from Staff where Staff.id = inId;

if result >= 1 then
	SELECT lastName, firstName, mi, address, city, state, telephone, email
	INTO outLastName, outFirstName, outMi, outAddress, outCity, outState, outTelephone, outEmail
	from Staff where id = inId;

else 
	select "0", "0", "0", "0", "0", "0", "0", "0"
    INTO outLastName, outFirstName, outMi, outAddress, outCity, outState, outTelephone, outEmail;
    
    end if;
    
end;
//
delimiter ;