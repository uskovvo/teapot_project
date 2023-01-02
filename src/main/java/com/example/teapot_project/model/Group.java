package com.example.teapot_project.model;

import java.util.List;

public class Group {

    private Long id;
    private  String groupColor;

    private List<User> userList;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Group() {
    }

    public Group(Long id, String groupColor) {
        this.id = id;
        this.groupColor = groupColor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupColor() {
        return groupColor;
    }

    public void setGroupColor(String groupColor) {
        this.groupColor = groupColor;
    }
}
