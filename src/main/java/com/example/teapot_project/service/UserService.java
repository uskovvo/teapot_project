package com.example.teapot_project.service;

import com.example.teapot_project.dao.GroupDao;
import com.example.teapot_project.dao.GroupRepository;
import com.example.teapot_project.dao.UserDao;
import com.example.teapot_project.dao.UserRepository;
import com.example.teapot_project.model.CompetitionTO;

public class UserService {
    private static UserService instance;
    private UserRepository userDao = UserDao.getInstance();
    private GroupRepository groupDao = GroupDao.getInstance();

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
        Randomizer randomizer = new Randomizer();

        return randomizer.startCompetition();
    }

    public CompetitionTO changeCompetitor(Long idToSave, Long idToChange) {
        Randomizer randomizer = new Randomizer();
        return randomizer.changeCompetitor(idToSave, idToChange);
    }

    public CompetitionTO competitorsList(){
        Randomizer randomizer =new Randomizer();
        return randomizer.prepareCompetitionersList();
    }

}
