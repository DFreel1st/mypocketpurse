CREATE PROCEDURE CLOSE_USERS_LABEL(IN label_id int)
    language plpgsql
as
$$
begin
    update labels
    set td = now()::timestamp
    where id = label_id;
end;
$$

