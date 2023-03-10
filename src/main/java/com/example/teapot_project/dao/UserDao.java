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

    private static final String CREATE_USER_QUERY = "INSERT INTO users(name, surname, age, group_id, status) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET name = ?, surname = ?, age = ?, group_id = ?, status = ? WHERE id = ?";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String READ_ALL_USERS_QUERY = "SELECT * FROM users AS u ORDER BY u.group_id, u.status";
    private static final String READ_USER_QUERY = "SELECT * FROM users AS u WHERE u.id = ?";
    private static final String READ_USERS_BY_GROUP_QUERY = "SELECT * FROM users AS u WHERE u.group_id = ?";
    private static final String READ_ALL_USERS_QUERY_WITH_FALSE_STATUS = "SELECT * FROM users WHERE status=false ORDER BY group_id";
    private static final String UPDATE_USER_STATUS_TO_FALSE = "UPDATE users SET status = false WHERE status=true";
    private static final String CHANGE_COMPETITORS_STATUS = "UPDATE users as u SET status = ? WHERE u.id = ? OR u.id = ?";

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

    @Override
    public List<User> readAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_ALL_USERS_QUERY)) {
            connection.setAutoCommit(false);

            readUsersFromDatabase(statement, users);
            connection.commit();

        } catch (SQLException e) {
            log.warn("Can't read users from database", e);
        }
        return users;
    }

    public List<User> readAllWithFalseStatus() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_ALL_USERS_QUERY_WITH_FALSE_STATUS)) {
            connection.setAutoCommit(false);

            readUsersFromDatabase(statement, users);
            connection.commit();

        } catch (SQLException e) {
            log.warn("Can't read users from database", e);
        }
        return users;
    }

    @Override
    public List<User> readAll(long groupId) {
        List<User> users = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_USERS_BY_GROUP_QUERY)) {
            connection.setAutoCommit(false);

            statement.setLong(1, groupId);
            readUsersFromDatabase(statement, users);
            connection.commit();

        } catch (SQLException e) {
            log.warn("Can't read users from database", e);
        }
        return users;
    }


    private void readUsersFromDatabase(PreparedStatement statement, List<User> users) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            users.add(parseUser(resultSet));
        }
    }

    public User read(long userId) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_USER_QUERY)) {

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

    private User readUserFromDatabase(PreparedStatement statement) throws SQLException {
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

            ConfigureCreateQueryParameters(statement, user);
            addUserToDatabase(statement, user);
            connection.commit();
            return user;

        } catch (SQLException e) {
            log.warn("User wasn't saved", e);
            throw new DatabaseOperationException("User wasn't saved");
        }
    }

    private void ConfigureCreateQueryParameters(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getName());
        statement.setString(2, user.getSurname());
        statement.setInt(3, user.getAge());
        if (user.getGroupId() == null) {
            statement.setNull(4, Types.BIGINT);
        } else {
            statement.setLong(4, user.getGroupId());
        }
        statement.setBoolean(5, user.isAnswerStatus());
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
            ConfigureUpdateQueryParameters(statement, user);

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


    //@Override
    public void setStatusToFalse() {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_STATUS_TO_FALSE)) {

            connection.setAutoCommit(false);


            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseOperationException("Row wasn't updated");
            }
            connection.commit();

        } catch (SQLException e) {
            log.warn("Failed to update table", e);
            throw new DatabaseOperationException("Row wasn't updated", e);

        }
    }

    public void setChangeCompetitorsStatus(long userId1, long userId2, boolean status) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CHANGE_COMPETITORS_STATUS)) {

            connection.setAutoCommit(false);

            statement.setBoolean(1, status);
            statement.setLong(2, userId1);
            statement.setLong(3, userId2);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseOperationException("Row wasn't updated");
            }
            connection.commit();

        } catch (SQLException e) {
            log.warn("Failed to update table", e);
            throw new DatabaseOperationException("Row wasn't updated", e);

        }
    }

    private void ConfigureUpdateQueryParameters(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getName());
        statement.setString(2, user.getSurname());
        statement.setInt(3, user.getAge());
        statement.setLong(4, user.getGroupId());
        statement.setBoolean(5, user.isAnswerStatus());
        statement.setLong(6, user.getId());
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
        user.setGroupId(usersSet.getLong("group_id"));
        user.setAnswerStatus(usersSet.getBoolean("status"));
        return user;
    }

}
