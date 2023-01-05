package com.example.teapot_project.servlet.actions.competition;

import com.example.teapot_project.model.CompetitionTO;
import com.example.teapot_project.servlet.actions.ServletAction;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractCompetitionAction implements ServletAction {

    protected void fillRequestWithData(CompetitionTO competition, HttpServletRequest request){
        request.setAttribute("groups", competition.getGroupList());
        request.setAttribute("users", competition.getUserList());
        request.setAttribute("userA", competition.getUserA());
        request.setAttribute("userB", competition.getUserB());
        request.setAttribute("groupA", competition.getGroupA());
        request.setAttribute("groupB", competition.getGroupB());

    }
}
