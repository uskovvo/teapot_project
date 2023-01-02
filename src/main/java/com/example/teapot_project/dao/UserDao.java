package com.example.teapot_project.dao;

import com.example.teapot_project.exceptions.DatabaseOperationException;
import com.example.teapot_project.model.User;
import com.example.teapot_project.utils.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(UserDao.class);
    private static UserDao instance;

    private static final String CREATE_USER_QUERY = "INSERT INTO users(name, surname, age) VALUES (?, ?, ?)";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET name = ?, surname = ?, age = ? WHERE id = ?";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";

    public static UserDao getInstance() {
        if (instance == null) {
            synchronized (UserDao.class) {
                if (instance == null) {
                    instance = new UserDao();
                }
            }
        }
        return instance;
    }

    private UserDao() {
    }


    public List<User> readAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             CallableStatement statement = connection.prepareCall("{call get_all()}")) {
            connection.setAutoCommit(false);

            readUsersFromDatabase(statement, users);
            connection.commit();

        } catch (SQLException e) {
            log.warn("Can't read users from database", e);
        }
        return users;
    }

    private void readUsersFromDatabase(CallableStatement statement, List<User> users) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            users.add(parseUser(resultSet));
        }
    }

    public User read(long userId) {
        try (Connection connection = DataSource.getConnection();
             CallableStatement statement = connection.prepareCall("{call get_by_id(?)}")) {

            connection.setAutoCommit(false);
            statement.setLong(1, userId);

            User user = readUserFromDatabase(statement);
            connection.commit();
            return user;
            //todo decide how to handle exceptions like this
        } catch (SQLException e) {
            log.warn("Exception was caught", e);
            throw new DatabaseOperationException("User wasn't reader", e);
        }
    }

    private User readUserFromDatabase(CallableStatement statement) throws SQLException {
        ResultSet userSet = statement.executeQuery();
        if (userSet.next()) {
            return parseUser(userSet);
        } else throw new DatabaseOperationException("User with such id is absent in database");
    }

    @Override
    public User create(User user) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            connection.setAutoCommit(false);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setInt(3, user.getAge());

            addUserToDatabase(statement, user);
            connection.commit();
            return user;

        } catch (SQLException e) {
            log.warn("User wasn't saved", e);
            throw new DatabaseOperationException("User wasn't saved");
        }
    }

    private User addUserToDatabase(PreparedStatement statement, User user) throws SQLException {
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new DatabaseOperationException("User wasn't saved");
        }
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            user.setId(generatedKeys.getLong("id"));
        }
        return user;
    }


    @Override
    public User update(User user) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_QUERY)) {

            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setInt(3, user.getAge());
            statement.setLong(4, user.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
               throw new DatabaseOperationException("User wasn't deleted");
            }
            connection.commit();
            return user;

        } catch (SQLException e) {
            log.warn("Failed to save user", e);
            throw new DatabaseOperationException("User wasn't updated", e);

        }
    }

    @Override
    public boolean delete(long id) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER_QUERY)) {

            connection.setAutoCommit(false);
            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();
            connection.commit();
            return affectedRows != 0;

        } catch (SQLException e) {
            log.warn("unsuccessful delete", e);
        }
        return false;
    }

    private User parseUser(ResultSet usersSet) throws SQLException {
        User user = new User();
        user.setId(usersSet.getLong("id"));
        user.setName(usersSet.getString("name"));
        user.setSurname(usersSet.getString("surname"));
        user.setAge(usersSet.getInt("age"));
        return user;
    }

}
