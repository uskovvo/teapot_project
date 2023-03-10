package com.example.teapot_project.dao;

import com.example.teapot_project.model.User;

import java.util.List;

public interface UserRepository {
    List<User> readAll();
    List<User> readAll(long groupId);
    User read(long userId);
    User create(User user);
    User update(User user);
    boolean delete(long id);
    List<User> readAllWithFalseStatus();
}
