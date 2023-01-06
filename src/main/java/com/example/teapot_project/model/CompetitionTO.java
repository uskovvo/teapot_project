package com.example.teapot_project.model;

import java.util.List;

public class CompetitionTO {
    private User userA;
    private User userB;
    private Group groupA;
    private Group groupB;
    private List<User> userList;
    private List<Group> groupList;
    private boolean notValid;

    public CompetitionTO() {
    }

    public void addGroupToList(Group group) {
        groupList.add(group);
    }

    public void addUserToList(User user) {
        userList.add(user);
    }

    public User getUserA() {
        return userA;
    }

    public void setUserA(User userA) {
        this.userA = userA;
    }

    public User getUserB() {
        return userB;
    }

    public void setUserB(User userB) {
        this.userB = userB;
    }

    public Group getGroupA() {
        return groupA;
    }

    public void setGroupA(Group groupA) {
        this.groupA = groupA;
    }

    public Group getGroupB() {
        return groupB;
    }

    public void setGroupB(Group groupB) {
        this.groupB = groupB;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Group> getGroupList() {
        return groupList;
    }


    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    public boolean isNotValid() {
        return notValid;
    }

    public void setNotValid(boolean notValid) {
        this.notValid = notValid;
    }
}

