package dao;

import com.example.teapot_project.dao.GroupDao;
import com.example.teapot_project.dao.UserDao;
import com.example.teapot_project.exceptions.DatabaseConnectionException;
import com.example.teapot_project.model.Group;
import com.example.teapot_project.model.User;
import com.example.teapot_project.utils.DataSource;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class AnotherTests  {

    private static GroupDao groupDao= GroupDao.getInstance();
    private static UserDao userDao = UserDao.getInstance();
    Connection connection;

    @BeforeEach
    public void before() {
        try {
            Path path = Path.of("src/main/resources/populate-testdb.sql");
            String table = Files.readString(path);
            this.connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(table);
            statement.execute();
        } catch (SQLException | IOException e) {
            throw new DatabaseConnectionException("Can't get database connection", new FileNotFoundException());
        }
    }

    void mockTest(){
        User newUser = mock(User.class);
        when(newUser.getName()).thenReturn("Kolya").thenReturn("Vasya");
        Assertions.assertEquals("Kolya", newUser.getName());
        Assertions.assertEquals("Vasya", newUser.getName());

        verify(newUser, times(2)).getName();
        Assertions.assertEquals(null,newUser.getSurname());
        verify(newUser, times(1)).getSurname();
    }

    @Test
    void newtest(){

//        List<User> userList = userDao.readAll();
//        System.out.println(userList);
//
//        List<Group> groupList = groupDao.readAll();
//        System.out.println(groupList);
//        Map<Long, List<User>> map = new HashMap<>();
//        for (Group group : groupList) {
//            map.put(group.getId(), new ArrayList<User>());
//        }
//        for (User user : userList) {
//        if (!user.isAnswerStatus() && user.getGroupId() != null)
//            map.get(user.getGroupId()).add(user);
//
//        }
//        System.out.println(map);




    }








//    @AfterEach
//    public void after() throws SQLException {
//        this.connection.close();
//    }




}
