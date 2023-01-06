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

        competition.setGroupList(groupDao.readAll());
        fillGroupsWithUsers();
        findFirstVictim();
        competition.setUserB(findSecondVictim());

        makeTrue(competition.getUserA());
        makeTrue(competition.getUserB());
        return competition;
    }

    public void fillGroupsWithUsers() {
        competition.setUserList(userDao.readAllWithFalseStatus());
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

    public void makeTrue(User user) {
        user.setAnswerStatus(!user.isAnswerStatus());
        competition.getUserList().remove(user);
        userDao.update(user);
    }

    public void findFirstVictim() {
        List<Group> groupList = competition.getGroupList();

        if (groupList.get(0).getUserList().isEmpty()) {
            userDao.setStatusToFalse();
            startCompetition();
            return;
        }

        List<User> list = groupList.get(0).getUserList();
        competition.setGroupA(groupList.get(0));
        competition.setUserA(list.get((int) (Math.random() * (list.size() - 1))));
    }

    private  void shuffleGroups(){
        List <Group> groupList = competition.getGroupList();
        Long firstVictimGroupId = competition.getUserA().getGroupId();
       Collections.shuffle(groupList);
        for (Group group : groupList) {
            List<User> userList = group.getUserList();
            if (!group.getId().equals(firstVictimGroupId) && !userList.isEmpty())
            {
                competition.setGroupB(group);
                break;
            }
        }
    }
    public User findSecondVictim() {
        shuffleGroups();

            if (competition.getGroupB() == null){
            userDao.setStatusToFalse();
            fillGroupsWithUsers();
            makeTrue(competition.getUserA());
            shuffleGroups();
            }
        List<User> list = competition.getGroupB().getUserList();
        return list.get((int) (Math.random() * (list.size() - 1)));
    }


    public CompetitionTO changeCompetitor(Long idToSave, Long idToChange) {
        setAnswerStatus(idToSave, idToChange, false);
        competition.setUserList(userDao.readAllWithFalseStatus());
        excludeUserFromNextRound(idToChange);
        competition.setGroupList(groupDao.readAll());
        fillGroupsWithUsers();
        saveUserForNextRound(idToSave);
        competition.setUserB(findSecondVictim());
        return competition;
    }

    private void setAnswerStatus(long userId1, long userId2, boolean status) {
        userDao.setChangeCompetitorsStatus(userId1, userId2, status);
    }

    private void saveUserForNextRound(Long idToSave) {
        List <User> copy = List.copyOf(competition.getUserList());
        for (User user : copy) {
            if (user.getId().equals(idToSave)) {
                competition.setUserA(user);
                makeTrue(user);
            }
        }

        for (Group group : competition.getGroupList()) {
                if (competition.getUserA().getGroupId().equals(group.getId())) {
                    competition.setGroupA(group);
                    break;
            }
        }

    }

    private void excludeUserFromNextRound(Long idToChange) {
        List<User> users = List.copyOf(competition.getUserList());
        for (int i = 0; i < users.size(); i++) {
            if (idToChange.equals(users.get(i).getId())) {
                competition.getUserList().remove(i);
                break;
            }
        }
    }

    public CompetitionTO prepareCompetitionersList() {
        competition.setGroupList(groupDao.readAll());
        competition.setUserList(userDao.readAll());
        fillGroupsWithUsers();
        checkCompetitionValidness();
        return competition;
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
}
