create function get_all() returns SETOF users
    language plpgsql
as
$$
BEGIN
    RETURN QUERY SELECT * FROM users;
END
$$;

alter function get_all() owner to postgres;




create function get_by_id(userid bigint) returns SETOF users
    language plpgsql
as
$$BEGIN
    RETURN QUERY SELECT * FROM users u WHERE u.id = userId;
end;
$$;

alter function get_by_id(bigint) owner to postgres;