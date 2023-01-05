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

    UserDao userDao = UserDao.getInstance();
    GroupDao groupDao = GroupDao.getInstance();

    public void findVictims(CompetitionTO competition) {

            User secondVictim;
            User firstVictim;
            List<User> userList = new ArrayList<>();
            List<Group> groupList = new ArrayList<>();

            fillLists(userList, groupList);
            firstVictim = findFirstVictim(userList, groupList);
            secondVictim = findSecondVictim(userList, groupList, firstVictim);


            makeTrue(firstVictim);
            makeTrue(secondVictim);

        competition.setUserA(firstVictim);
        competition.setGroupA(groupDao.read(firstVictim.getGroupId()));
        competition.setUserList(userDao.readAllWithFalseStatus());
        competition.setUserB(secondVictim);
        competition.setGroupB(groupDao.read(secondVictim.getGroupId()));
        competition.setGroupList(groupDao.readAll());
        }

    public void fillLists (List<User> userList , List<Group> groupList){

            userList.addAll(userDao.readAllWithFalseStatus());
            groupList.addAll(groupDao.readAll());
            if (groupList.size() < 2)
                throw new NotValidDataException("You should have at least 2 groups to fight");
//            TO DO HANDLE EXCEPTION

            for (Group group : groupList) {
                group.setUserList(
                        userList.stream().filter(c -> c.getGroupId().
                                equals(group.getId())).collect(Collectors.toList()));
            }
        }

    public long[] findMaxUsersListId (List<Group> groupList, long id) {
                    long maxId = 0 ;
                    int maxSize = 0;
                    for (Group group : groupList) {
                        if (group.getId() == id)
                            continue;
                        int size = group.getUserList().size();
                        if (size > maxSize){
                            maxSize = size;
                            maxId = group.getId();}
                    }

            return new long[]{maxId,maxSize};
          }

         public void makeTrue(User user){
              UserDao userDao = UserDao.getInstance();
              user.setAnswerStatus(!user.isAnswerStatus());
        userDao.update(user);
          }
        public User findFirstVictim(List<User> userList , List<Group> groupList){
            long[]  array = findMaxUsersListId(groupList,0);
            if (array[1] ==0) {
                userDao.setStatusToFalse();
                fillLists(userList, groupList);
                array= findMaxUsersListId(groupList,0);
            }

            long maxGroupId = array[0];
            long maxSize = array[1];
           User firstVictim = groupList.stream().filter(c -> c.getId() == maxGroupId).findAny().get().getUserList()
                    .get((int)(Math.random()*(maxSize -1)));
           return firstVictim;
        }
        public User findSecondVictim (List<User> userList , List<Group> groupList, User firstVictim){
           long [] array = findMaxUsersListId(groupList,firstVictim.getGroupId());
            if (array[1] == 0) {

                fillLists(userList, groupList);
                makeTrue(firstVictim);
                array = findMaxUsersListId(groupList,firstVictim.getGroupId());
            }
            long secondGroupMaxId = array[0];
            long secondMaxSize = array[1];

            User secondVictim = groupList.stream().filter(c -> c.getId() == secondGroupMaxId).findAny().get().getUserList()
                    .get((int)(Math.random()*(secondMaxSize -1)));
            return secondVictim;
        }

}
