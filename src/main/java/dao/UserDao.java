package dao;

import exceptions.DatabaseConnectionException;
import exceptions.DatabaseOperationException;
import exceptions.EntityNotFoundException;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(UserDao.class);
    private static UserDao instance;
    private Connection connection;

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
        try {
            connection = DataSource.getConnection();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to get connection to the database", e);
        }
    }


    public List<User> readAll() {
        List<User> users = new ArrayList<>();
        try (CallableStatement statement = connection.prepareCall("{call get_all()}")) {
            readUsersFromDatabase(statement, users);
        } catch (SQLException e) {
            log.warn("Exception was caught", e);
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
        try (CallableStatement statement = connection.prepareCall("{call get_by_id(?)}")) {
            statement.setLong(1, userId);
            User user = readUserFromDatabase(statement);
            return user;
            //todo decide how to handle exceptions like this
        } catch (SQLException e) {
            log.warn("Exception was caught", e);
            throw new EntityNotFoundException("No user with such id in database");
        }
    }

    private User readUserFromDatabase(CallableStatement statement) throws SQLException {
        ResultSet userSet = statement.executeQuery();
        if (userSet.next()) {
            return parseUser(userSet);
        } else throw new EntityNotFoundException("No user with such id in database");
    }

    @Override
    public User create(User user) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO users(name, surname, age) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setInt(3, user.getAge());

            addUserToDatabase(statement, user);
            return user;

        } catch (SQLException e) {
            log.warn("Exception was caught", e);
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
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE users SET name = ?, surname = ?, age = ? WHERE id = ?")) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setInt(3, user.getAge());
            statement.setLong(4, user.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("user wasn't updated");
            }
            return user;

        } catch (SQLException e) {
            log.warn("Failed to save user");
            throw new DatabaseOperationException("unsuccessful update", e);

        }
    }

    @Override
    public boolean delete(long id) {
        try (Statement statement = connection.createStatement()) {
            String query = String.format("DELETE FROM users WHERE id = %d", id);
            int affectedRows = statement.executeUpdate(query);
            return affectedRows != 0;
        } catch (SQLException e) {
            log.warn("unsuccessful delete", e);
            throw new DatabaseOperationException("unsuccessful delete", e);
        }

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
