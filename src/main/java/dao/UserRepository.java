package dao;

import model.User;

import java.util.List;

public interface UserRepository {
    List<User> readAll();
    User read(long userId);
    User create(User user);
    User update(User user);
    boolean delete(long id);
}
