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

    public CompetitionTO startCompetition(){

        competition.setGroupList(groupDao.readAll());
        fillGroupsWithUsers();
        competition.setUserA(findFirstVictim());
        competition.setUserB(findSecondVictim());
        makeTrue(competition.getUserA());
        makeTrue(competition.getUserB());
    return competition;
    }

    public void fillGroupsWithUsers(){
        competition.setUserList(userDao.readAllWithFalseStatus());
        List<User> userList = competition.getUserList();
        System.out.println("userlist when fill groups" + userList);
        List<Group> groupList = competition.getGroupList();
        System.out.println("grouplist when fill groups" + groupList);
            if (groupList.size() < 2)
                throw new NotValidDataException("You should have at least 2 groups and 2 users to fight");

            for (Group group : groupList) {
                group.setUserList(
                        userList.stream().filter(c -> c.getGroupId().
                                equals(group.getId())).collect(Collectors.toList()));
            }
        groupList.sort(((g1, g2) ->  g2.getUserList().size() - g1.getUserList().size()));
        }

         public void makeTrue(User user){
              user.setAnswerStatus(!user.isAnswerStatus());
        userDao.update(user);
          }
        public User findFirstVictim(){
            List<Group> groupList = competition.getGroupList();

            if (groupList.get(0).getUserList().size()  == 0) {
                userDao.setStatusToFalse();
                startCompetition();
            }

            List<User> list = groupList.get(0).getUserList();
            System.out.println("grouplist in first victim " + groupList);
            competition.setGroupA(groupList.get(0));
            System.out.println("list size " + list.size());
            int n = (int)(Math.random()*(list.size() - 1));
            System.out.println(n);
            User user = list.get(n);
            System.out.println(user);
           return list.get((int)(Math.random()*(list.size() - 1)));
        }
        public User findSecondVictim (){
            List<Group> groupList = competition.getGroupList();

            Group group;
            if (groupList.get(0).getId().equals(competition.getUserA().getGroupId()))
                group = groupList.get(1);
            else
                group = groupList.get(0);

            if (group.getUserList().size() == 0) {
                userDao.setStatusToFalse();
                fillGroupsWithUsers();
                makeTrue(competition.getUserA());
                if (groupList.get(0).getId().equals(competition.getUserA().getGroupId()))
                    group = groupList.get(1);
                else
                    group = groupList.get(0);
            }

            List<User> list = group.getUserList();
            competition.setGroupA(group);

            return list.get((int)(Math.random()*(list.size() -1)));
        }

}
