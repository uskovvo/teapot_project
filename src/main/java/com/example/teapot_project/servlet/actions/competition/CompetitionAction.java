package com.example.teapot_project.servlet.actions.competition;

import com.example.teapot_project.dao.*;
import com.example.teapot_project.model.CompetitionTO;
import com.example.teapot_project.service.Randomizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CompetitionAction extends AbstractCompetitionAction{
    private Randomizer randomizer = new Randomizer();
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CompetitionTO competition = new CompetitionTO();
        UserDao userDao = UserDao.getInstance();
        GroupDao groupDao = GroupDao.getInstance();

//        List<User> users = userDao.readAll();
//        List<Group> group = groupDao.readAll();
//        competition.setGroupList(group);
//        competition.setUserList(users);
//        competition.setUserA(users.get(0));
//        competition.setUserB(users.get(1));
//        competition.setGroupA(group.get(0));
//        competition.setGroupB(group.get(1));

        randomizer.findVictims(competition);
        fillRequestWithData(competition, req);

        req.getRequestDispatcher("/competition.jsp").forward(req, resp);
    }


}
