package com.example.teapot_project.dao;

import com.example.teapot_project.model.Group;
import com.example.teapot_project.model.User;

import java.util.List;

public interface GroupRepository {

    List<Group> readAll();
    Group read(long groupId);
    Group create(Group group);
    Group update(Group group);
    boolean delete(long id);

}
