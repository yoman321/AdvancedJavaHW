drop procedure if exists deptCount;

delimiter //

create procedure deptCount (out oCs char(4), out oBiol char(4), out oChem char(4), out oMath char(4))
begin 
select dept, count(*)
from Student
where dept is not null
group by dept;
end;
//
delimiter ;