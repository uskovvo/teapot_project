package com.example.teapot_project.service;

import com.example.teapot_project.dao.GroupDao;
import com.example.teapot_project.dao.UserDao;
import com.example.teapot_project.model.CompetitionTO;

public class UserService {
    private static UserService instance;
    private UserDao userDao = UserDao.getInstance();
    private GroupDao groupDao = GroupDao.getInstance();

    public static UserService getInstance() {
        if (instance == null) {
            synchronized (UserService.class) {
                if (instance == null) {
                    instance = new UserService();
                }
            }
        }
        return instance;
    }

    private UserService() {
    }

    public CompetitionTO startCompetition() {
        CompetitionTO competition = new CompetitionTO();
        Randomizer randomizer = new Randomizer();
        randomizer.findVictims(competition);
        competition.setUserList(userDao.readAllWithFalseStatus());
        competition.setGroupList(groupDao.readAll());
        return competition;
    }

    public CompetitionTO changeCompetitor(Long idToSave, Long idToChange) {
        return new CompetitionTO();
    }

}
