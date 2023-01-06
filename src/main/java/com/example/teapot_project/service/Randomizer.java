package com.example.teapot_project.service;

import com.example.teapot_project.dao.GroupDao;
import com.example.teapot_project.dao.UserDao;
import com.example.teapot_project.exceptions.NotValidDataException;
import com.example.teapot_project.model.CompetitionTO;
import com.example.teapot_project.model.Group;
import com.example.teapot_project.model.User;

import java.util.*;
import java.util.stream.Collectors;

public class Randomizer {
    private UserDao userDao;
    private GroupDao groupDao;
    private CompetitionTO competition;

    public Randomizer() {
        competition = new CompetitionTO();
        userDao = UserDao.getInstance();
        groupDao = GroupDao.getInstance();
    }

    public CompetitionTO startCompetition() {
        readUsersWithFalseAnswerStatus();
        competition.setGroupList(groupDao.readAll());
        fillGroupsWithUsers();
        validationUserQuantity();
        findFirstVictim();
        findSecondVictim();
        prepareCompetition();
        return competition;
    }

    public CompetitionTO prepareCompetitionersList() {
        readAllUsers();
        competition.setGroupList(groupDao.readAll());
        fillGroupsWithUsers();
        checkCompetitionValidness();
        return competition;
    }

    public CompetitionTO changeCompetitor(Long idToSave, Long idToChange) {
        setAnswerStatus(idToChange, idToSave, false);
        readUsersWithFalseAnswerStatus();
        excludeUserFromNextRound(idToChange);
        competition.setGroupList(groupDao.readAll());
        fillGroupsWithUsers();
        saveUserForNextRound(idToSave);
        findSecondVictim();
        prepareCompetition();

        return competition;
    }

    private void excludeUserFromNextRound(Long idToChange) {
        List<User> users = competition.getUserList();
        for (int i = 0; i <users.size() ; i++) {
            if(idToChange.equals(users.get(i).getId()));
            users.remove(i);
        }
    }

    private void saveUserForNextRound(Long idToSave) {
        for (User user : competition.getUserList()) {
           if (user.getId().equals(idToSave)){
            competition.setUserA(user);
               for (Group group : competition.getGroupList()) {
                   if (competition.getUserA().getId().equals(group.getId())){
                       competition.setGroupA(group);
                       break;
                   }
               }

           }
        }

    }


    private void validationUserQuantity() {
        List<Group> groupList = competition.getGroupList();
        if (groupList.get(0).getUserList().isEmpty()) {
            userDao.setStatusToFalse();
            startCompetition();
        }
    }



    private void checkCompetitionValidness() {
        int count = 0;
        for (Group group : competition.getGroupList()) {
            List<User> users = group.getUserList();
            if (!users.isEmpty()) {
                count++;
            }
        }
        if (count < 2) {
            competition.setNotValid(true);
        }
    }

    private void fillGroupsWithUsers() {
        List<User> userList = competition.getUserList();
        List<Group> groupList = competition.getGroupList();
        if (groupList.size() < 2)
            throw new NotValidDataException("You should have at least 2 groups and 2 users to fight");

        for (Group group : groupList) {
            group.setUserList(
                    userList.stream().filter(c -> c.getGroupId().
                            equals(group.getId())).collect(Collectors.toList()));
        }
        groupList.sort(((g1, g2) -> g2.getUserList().size() - g1.getUserList().size()));
    }


    private void findFirstVictim() {
        List<Group> groupList = competition.getGroupList();
        competition.setGroupA(groupList.get(0));
        competition.setUserA(getRandomUserFromGroup(competition.getGroupA()));
    }

    private void findSecondVictim() {
        setGroupB();
        Group groupB = competition.getGroupB();
        competition.setUserB(getRandomUserFromGroup(groupB));
    }

    private void setGroupB() {
        List<Group> groupList = competition.getGroupList();
        if (groupList.get(0).getId().equals(competition.getUserA().getGroupId()))
            competition.setGroupB(groupList.get(1));
        else
            competition.setGroupB(groupList.get(0));
        Group groupB = competition.getGroupB();

        if (groupB.getUserList().size() == 0) {
            userDao.setStatusToFalse();
            readUsersWithFalseAnswerStatus();
            fillGroupsWithUsers();

            if (groupList.get(0).getId().equals(competition.getUserA().getGroupId()))
                groupB = groupList.get(1);
            else
                groupB = groupList.get(0);
        }
}

    private User getRandomUserFromGroup(Group group) {
        List<User> userlist = group.getUserList();
        return userlist.get((int) (Math.random() * (userlist.size() - 1)));
    }

    private void readUsersWithFalseAnswerStatus() {
        competition.setUserList(userDao.readAllWithFalseStatus());
    }

    private void readAllUsers() {
        competition.setUserList(userDao.readAll());

    }

    private void prepareCompetition() {
        User userA = competition.getUserA();
        User userB = competition.getUserB();
        setAnswerStatus(userA.getId(), userB.getId(), true);
        competition.getUserList().remove(userA);
        competition.getUserList().remove(userB);

    }

    private void setAnswerStatus(long userId1, long userId2, boolean status) {
        userDao.setChangeCompetitorsStatus(userId1, userId2, status);
    }


}
