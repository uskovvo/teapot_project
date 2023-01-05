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
    CompetitionTO competition;

    public void findVictims(CompetitionTO competition) {


            User secondVictim;
            User firstVictim;
            List<User> userList = new ArrayList<>();
            List<Group> groupList = new ArrayList<>();

            fillLists(userList, groupList);
            long[]  array = findMaxUsersListId(groupList,0);
            if (array[1] ==0) {
            userDao.setStatusToFalse();
            fillLists(userList, groupList);
            array= findMaxUsersListId(groupList,0);
            }

            long maxGroupId = array[0];
            long maxSize = array[1];
            firstVictim = groupList.stream().filter(c -> c.getId() == maxGroupId).findAny().get().getUserList()
                .get((int)(Math.random()*(maxSize -1)));


            array = findMaxUsersListId(groupList,maxGroupId);
            if (array[1] == 0) {

                fillLists(userList, groupList);
                makeTrue(firstVictim.getId());
                array = findMaxUsersListId(groupList,maxGroupId);
            }
            long secondGroupMaxId = array[0];
            long secondMaxSize = array[1];

            secondVictim = groupList.stream().filter(c -> c.getId() == secondGroupMaxId).findAny().get().getUserList()
                    .get((int)(Math.random()*(secondMaxSize -1)));

            makeTrue(firstVictim.getId());
            makeTrue(secondVictim.getId());

        competition.setUserA(firstVictim);
        competition.setGroupA(groupDao.read(maxGroupId));
        competition.setUserList(userDao.readAllWithFalseStatus());
        competition.setUserB(secondVictim);
        competition.setGroupB(groupDao.read(secondGroupMaxId));
        competition.setGroupList(groupDao.readAll());
        }

        void fillLists (List<User> userList , List<Group> groupList){

            userList = userDao.readAllWithFalseStatus();
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
        static long[] findMaxUsersListId (List<Group> groupList, long id) {
                    long maxId = 0 ;
                    int maxSize = 0;
            System.out.println("grouplis from method find" + groupList);
                    for (Group group : groupList) {
                        if (group.getId() == id)
                            continue;
                        int size = group.getUserList().size();
                        if (size > maxSize){
                            maxSize = size;
                            maxId = group.getId();}
                    }
            System.out.println("max id " + maxId );
            System.out.println("max size "+ maxSize);

            return new long[]{maxId,maxSize};
          }

          static void makeTrue(long id1){
              UserDao userDao = UserDao.getInstance();

        User user = userDao.read(id1);
              user.setAnswerStatus(!user.isAnswerStatus());
        userDao.update(user);
          }



}
