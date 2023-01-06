package daoTest;

import com.example.teapot_project.dao.GroupDao;
import com.example.teapot_project.model.Group;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.GroupTestData;

import java.util.List;

public class GroupDaoTest {

    private static GroupDao groupDao= GroupDao.getInstance();

    @Test
    @DisplayName("Tests if there is 3 groups in db")
    void getAll(){
        List<Group> groups = groupDao.readAll();
        Assertions.assertEquals(3, groups.size());
    }

    @DisplayName("Tests get method")
    @Test
    void getTest() {
        Group group = groupDao.read(1);
        Assertions.assertEquals("RED", group.getGroupColor());
    }

    @DisplayName("Tests method update")
    @Test
    void update() {
        Group group = groupDao.read(1);
        String newColor = "BLACK";
        group.setGroupColor(newColor);
        groupDao.update(group);

        Group updatedGroup = groupDao.read(1);
        Assertions.assertEquals(newColor, updatedGroup.getGroupColor());

        updatedGroup.setGroupColor("RED");
        groupDao.update(updatedGroup);
    }


    @DisplayName("Test methods delete and add user")
    @Test
    void delete() {
        Group newGroup  = groupDao.create(GroupTestData.NEW_GROUP);
        Assertions.assertTrue(groupDao.delete(newGroup.getId()));
    }
}
