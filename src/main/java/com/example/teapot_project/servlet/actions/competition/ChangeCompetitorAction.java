package com.example.teapot_project.servlet.actions.competition;

import com.example.teapot_project.model.CompetitionTO;

import javax.servlet.http.HttpServletRequest;

public class ChangeCompetitorAction extends AbstractCompetitionAction {

    @Override
    protected void prepareCompetition(HttpServletRequest req) {
        requestDataChange(req);
    }

    private CompetitionTO requestDataChange(HttpServletRequest req) {
        String save = (req.getParameter("save"));
        String change = (req.getParameter("change"));

        if (save.equals("") || change.equals("")) {
            return userService.startCompetition();
        } else {
            Long idToSave = Long.parseLong(save);
            Long idToChange = Long.parseLong(change);
            return userService.changeCompetitor(idToSave, idToChange);
        }

    }


}
