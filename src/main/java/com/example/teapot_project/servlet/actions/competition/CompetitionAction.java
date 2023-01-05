package com.example.teapot_project.servlet.actions.competition;

import com.example.teapot_project.model.CompetitionTO;

import javax.servlet.http.HttpServletRequest;

public class CompetitionAction extends AbstractCompetitionAction {

    @Override
    protected void prepareCompetition(HttpServletRequest req) {
        CompetitionTO competition = userService.competitorsList();
        fillRequestWithData(competition, req);
    }


}
