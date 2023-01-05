package com.example.teapot_project.dao;

import com.example.teapot_project.exceptions.NotValidDataException;
import com.example.teapot_project.model.Group;
import com.example.teapot_project.model.User;

import java.util.*;

public class Randomizer {

    public static void main(String[] args) {
    newtest();
    }
        static void newtest() {

            long firstVictimId;
            long secondVictimId;
            UserDao userDao = UserDao.getInstance();

            List<User> userList = new ArrayList<>();
            List<Group> groupList = new ArrayList<>();
            Map<Long, List<User>> map = new HashMap<>();

            fillLists(userList, groupList, map);

            long[]  array= findMaxUsersListId(map,0);
            if (array[1] ==0) {
            userDao.setStatusToFalse();
            fillLists(userList, groupList, map);
            array= findMaxUsersListId(map,0);
            }
            long maxId = array[0];
            long maxSize = array[1];
            firstVictimId= map.get(maxId).get((int)(Math.random()*(maxSize -1))).getId();

            array = findMaxUsersListId(map,maxId);
            if (array[1] == 0) {
                userDao.setStatusToFalse();
                fillLists(userList, groupList, map);
                maketrue(firstVictimId);
                array = findMaxUsersListId(map,maxId);
            }
            long secondMaxId = array[0];
            long secondMaxSize = array[1];

            secondVictimId = map.get(secondMaxId).get((int)(Math.random()*(secondMaxSize -1))).getId();


            System.out.println("victim id " + firstVictimId);
            System.out.println("victim " +userDao.read(firstVictimId));

            System.out.println("victim2 id " + secondVictimId);
            System.out.println("victim2 " +userDao.read(secondVictimId));

            maketrue(firstVictimId);
            maketrue(secondVictimId);
        }

        static void fillLists (List<User> userList , List<Group> groupList , Map<Long, List<User>> map){
            GroupDao groupDao = GroupDao.getInstance();
            UserDao userDao = UserDao.getInstance();

            userList = userDao.readAllWithFalseStatus();
            groupList = groupDao.readAll();
            if (groupList.size() < 2)
                throw new NotValidDataException("You should have at least 2 groups to fight");
//            TO DO HANDLE EXCEPTION

            for (Group group : groupList) {
                map.put(group.getId(), new ArrayList<>());
            }
            for (User user : userList) {
                long t = user.getGroupId();
                if (!user.isAnswerStatus() && t != 0)
                    map.get(t).add(user);
            }
        }

        static long[] findMaxUsersListId (Map<Long,List<User>> map, long id) {
                    long maxId = 0 ;
                    int maxSize = 0;
                    for (Long aLong : map.keySet()) {
                        if (aLong == id)
                            continue;
                        if (map.get(aLong).size() > maxSize){
                            maxSize = map.get(aLong).size();
                            maxId = aLong;}
                    }
                    return new long[]{maxId,maxSize};
          }

          static void maketrue(long id1){
              UserDao userDao = UserDao.getInstance();

        User user = userDao.read(id1);
              user.setAnswerStatus(!user.isAnswerStatus());
        userDao.update(user);

          }










}
