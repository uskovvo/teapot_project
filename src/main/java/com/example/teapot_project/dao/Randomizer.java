package com.example.teapot_project.dao;

import com.example.teapot_project.model.Group;
import com.example.teapot_project.model.User;

import java.sql.Connection;
import java.util.*;

public class Randomizer {

    public static void main(String[] args) {
    newtest();

    }
        static void newtest() {

            long firstVictimId;
            long secondVictimId;

            GroupDao groupDao = GroupDao.getInstance();
            UserDao userDao = UserDao.getInstance();

            List<User> userList = userDao.readAll();
          //  System.out.println("user list " + userList);

            List<Group> groupList = groupDao.readAll();
            //System.out.println("group list " + groupList);
            Map<Long, List<User>> map = new HashMap<>();
            for (Group group : groupList) {
                map.put(group.getId(), new ArrayList<>());
            }
            for (User user : userList) {
                long t = user.getGroupId();
                if (!user.isAnswerStatus() && t != 0)
                    map.get(t).add(user);

            }

            //System.out.println("map " + map);
            long[]  array= findMaxUsersListId(map,0);
            long maxId = array[0];
            long maxSize = array[1];

            array = findMaxUsersListId(map,maxId);
            long secondMaxId = array[0];
            long secondMaxSize = array[1];


            firstVictimId= map.get(maxId).get((int)(Math.random()*(maxSize -1))).getId();
            System.out.println("victim id " + firstVictimId);
            System.out.println("victim " +userDao.read(firstVictimId));

            secondVictimId = map.get(secondMaxId).get((int)(Math.random()*(secondMaxSize -1))).getId();
            System.out.println("victim2 id " + secondVictimId);
            System.out.println("victim2 " +userDao.read(secondVictimId));

            maketrue(firstVictimId,secondVictimId);
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

          static void maketrue(long id1, long id2){
              GroupDao groupDao = GroupDao.getInstance();
              UserDao userDao = UserDao.getInstance();

        User user1 = userDao.read(id1);
        user1.setAnswerStatus(true);
        userDao.update(user1);
              User user2 = userDao.read(id2);
              user2.setAnswerStatus(true);
              userDao.update(user2);

          }










}
