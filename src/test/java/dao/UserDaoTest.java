package dao;

import com.example.teapot_project.dao.UserDao;
import com.example.teapot_project.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.UserTestData;

import java.util.List;

class UserDaoTest {
    private static UserDao userDao = UserDao.getInstance();



    @Test
    @DisplayName("Works if there is 2 users in db")
    void getAll(){
        List<User> users = userDao.readAll();
        Assertions.assertEquals(3, users.size());
    }



    @Test
    void get() {
        User user = userDao.read(100);
        Assertions.assertEquals("Makise", user.getName());
    }


    @Test
    void update() {
        User user = userDao.read(100);
        String newName = "Cesar";
        user.setName(newName);
        userDao.update(user);

        User updatedUser = userDao.read(100);
        Assertions.assertEquals(newName, updatedUser.getName());

        updatedUser.setName("Makise");
        userDao.update(updatedUser);
    }
    @DisplayName("Test methods delete and add user")
    @Test
    void delete() {
        User newUser = userDao.create(UserTestData.NEW_USER);
        Assertions.assertTrue(userDao.delete(newUser.getId()));
    }
}
