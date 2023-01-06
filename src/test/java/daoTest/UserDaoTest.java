package daoTest;

import com.example.teapot_project.dao.UserDao;
import com.example.teapot_project.exceptions.DatabaseConnectionException;
import com.example.teapot_project.exceptions.NotValidDataException;
import com.example.teapot_project.model.User;
import com.example.teapot_project.utils.DataSource;
import org.junit.jupiter.api.*;
import utils.UserTestData;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

class UserDaoTest {
    private  UserDao userDao;
   private Connection connection;


    @BeforeEach
    public void before() {
        try {
            Path path = Path.of("src/main/resources/populate-db.sql");
            String table = Files.readString(path);
            this.connection = DataSource.getConnection();
            this.userDao = UserDao.getInstance();
            PreparedStatement statement = connection.prepareStatement(table);
            statement.execute();
        } catch (SQLException | IOException e) {


            throw new DatabaseConnectionException("Can't get database connection", new FileNotFoundException());
        }
    }
    @AfterEach
    public void after() throws SQLException {
        this.connection.close();
    }


    @Test
    @DisplayName("Tests if there is 4 users in db")
    void getAllTest(){
        List<User> users = userDao.readAll();
        Assertions.assertEquals(4, users.size());
    }
    @Test
    @DisplayName("Checks if there is 1 users where Groupid=1")
    void getAllWithGroupId(){
        List<User> users = userDao.readAll(1);
        Assertions.assertEquals(1, users.size());
    }


    @Test
    @DisplayName("Checks if there is 5 users where Groupid=1")
    void getAllWithMockGroupId(){
        List<User> users = userDao.readAll(1);
        Assertions.assertEquals(1, users.size());
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

    @DisplayName("Test methods throw NotValidDataException with wrong id ")
    @Test

    void NotValidDataExceptionTestId(){
        Assertions.assertThrows(NotValidDataException.class, () -> UserTestData.NEW_USER.setId(-1L));
        Assertions.assertThrows(NotValidDataException.class, () -> UserTestData.NEW_USER.setId(null));
    }

    @DisplayName("Test methods throw NotValidDataException with wrong Age ")
    @Test
    void NotValidDataExceptionTestAge(){
        Assertions.assertThrows(NotValidDataException.class, () -> UserTestData.NEW_USER.setAge(-11));
        Assertions.assertThrows(NotValidDataException.class, () -> UserTestData.NEW_USER.setAge(444));
    }

    @DisplayName("Test methods throw NotValidDataException with wrong Name ")
    @Test
    void NotValidDataExceptionTestName(){
        Assertions.assertThrows(NotValidDataException.class, () -> UserTestData.NEW_USER.setName(""));
        Assertions.assertThrows(NotValidDataException.class, () -> UserTestData.NEW_USER.setName("444"));
        Assertions.assertThrows(NotValidDataException.class, () -> UserTestData.NEW_USER.setName("ddddd4"));
        Assertions.assertThrows(NotValidDataException.class, () -> UserTestData.NEW_USER.setName("o"));
    }


}
