CREATE OR REPLACE PROCEDURE get_all()
    language 'plpgsql'
AS
$$
BEGIN
RETURN QUERY SELECT * FROM users;
END;
$$