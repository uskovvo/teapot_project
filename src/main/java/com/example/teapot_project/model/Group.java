package com.example.teapot_project.model;

import java.util.List;

public class Group {

    private Long id;
    private  String groupColor;

    private List<User> userList;

    public void addUser(User user){
        userList.add(user);
    }

    public Group(long id, String groupColor) {
        this.id = id;
        this.groupColor = groupColor;
    }

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
    public Group( String groupColor) {
        this.groupColor = groupColor;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", groupColor='" + groupColor + '\'' +
                ", userList=" + userList +
                '}';
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
