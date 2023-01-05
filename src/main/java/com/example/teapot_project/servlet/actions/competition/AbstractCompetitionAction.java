package com.example.teapot_project.servlet.actions.competition;

import com.example.teapot_project.model.CompetitionTO;
import com.example.teapot_project.service.UserService;
import com.example.teapot_project.servlet.UserServlet;
import com.example.teapot_project.servlet.actions.ServletAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractCompetitionAction implements ServletAction {
    protected UserService userService = UserService.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareCompetition(req);
        req.getRequestDispatcher("/competition.jsp").forward(req, resp);
    }

    protected abstract void prepareCompetition(HttpServletRequest req);

    protected void fillRequestWithData(CompetitionTO competition, HttpServletRequest request){
        request.setAttribute("groups", competition.getGroupList());
        request.setAttribute("users", competition.getUserList());
        request.setAttribute("userA", competition.getUserA());
        request.setAttribute("userB", competition.getUserB());
        request.setAttribute("groupA", competition.getGroupA());
        request.setAttribute("groupB", competition.getGroupB());

    }
}
