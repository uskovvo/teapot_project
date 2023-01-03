package com.example.teapot_project.dao;

import com.example.teapot_project.exceptions.DatabaseOperationException;
import com.example.teapot_project.model.Group;
import com.example.teapot_project.utils.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDao implements GroupRepository {

    private static final Logger log = LoggerFactory.getLogger(GroupDao.class);
    private static GroupDao instance;

    private static final String CREATE_USER_QUERY = "INSERT INTO groups(group_color) VALUES (?)";
    private static final String UPDATE_USER_QUERY = "UPDATE groups SET group_color = ? WHERE id = ?";
    private static final String DELETE_USER_QUERY = "DELETE FROM groups WHERE id = ?";
    private static final String READ_ALL_USERS_QUERY = "SELECT * FROM groups";
    private static final String READ_USER_QUERY = "SELECT * FROM groups AS g WHERE g.id = ?";
    private static final String RANDOM_GROUP_QUERY = "SELECT * FROM groups WHERE id = ? ORDER BY rand() LIMIT 2";

    public static GroupDao getInstance() {
        if (instance == null) {
            synchronized (GroupDao.class) {
                if (instance == null) {
                    instance = new GroupDao();
                }
            }
        }
        return instance;
    }


    private GroupDao() {
    }

    @Override
    public List<Group> readAll() {
        List<Group> groups = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_ALL_USERS_QUERY)) {

            connection.setAutoCommit(false);

            readGroupsFromDatabase(statement, groups);
            connection.commit();

        } catch (SQLException e) {
            log.warn("Can't read groups from database", e);
        }
        return groups;
    }

    private void readGroupsFromDatabase(PreparedStatement statement, List<Group> groups) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            groups.add(parseGroup(resultSet));
        }
    }

    @Override
    public Group read(long groupId) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_USER_QUERY)) {

            connection.setAutoCommit(false);
            statement.setLong(1, groupId);

            Group group = readGroupFromDatabase(statement);
            connection.commit();
            return group;
            //todo decide how to handle exceptions like this
        } catch (SQLException e) {
            log.warn("Exception was caught", e);
            throw new DatabaseOperationException("Group wasn't reader", e);
        }
    }

    private Group readGroupFromDatabase(PreparedStatement statement) throws SQLException {
        ResultSet groupSet = statement.executeQuery();
        if (groupSet.next()) {
            return parseGroup(groupSet);
        } else throw new DatabaseOperationException("Group with such id is absent in database");
    }

    @Override
    public Group create(Group group) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            connection.setAutoCommit(false);
            statement.setString(1, group.getGroupColor());

            addGroupToDatabase(statement, group);
            connection.commit();
            return group;

        } catch (SQLException e) {
            log.warn("Group wasn't saved", e);
            throw new DatabaseOperationException("Group wasn't saved");
        }
    }

    private void addGroupToDatabase(PreparedStatement statement, Group group) throws SQLException {
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new DatabaseOperationException("Group wasn't saved");
        }
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            group.setId(generatedKeys.getLong("id"));
        }
    }

    @Override
    public Group update(Group group) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_QUERY)) {

            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            statement.setString(1, group.getGroupColor());
            statement.setLong(2, group.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseOperationException("Group wasn't deleted");
            }
            connection.commit();
            return group;

        } catch (SQLException e) {
            log.warn("Failed to save group", e);
            throw new DatabaseOperationException("Group wasn't updated", e);

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

    @Override
    public List<Group> randomTwo() {
        List<Group> groupRandom = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(RANDOM_GROUP_QUERY)) {
            connection.setAutoCommit(false);

            readGroupsFromDatabase(statement, groupRandom);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return groupRandom;
    }
    private Group parseGroup(ResultSet groupsSet) throws SQLException {
        Group group = new Group();
        group.setId(groupsSet.getLong("id"));
        group.setGroupColor(groupsSet.getString("group_color"));
        return group;
    }
}

