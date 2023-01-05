package com.example.teapot_project.servlet.actions.competition;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class ChangeCompetitorAction extends AbstractCompetitionAction {


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    private void requestCompetitionTO(HttpServletRequest req) {
        String save = (req.getParameter("save"));
        String change = (req.getParameter("change"));

        Long idToSave = Long.parseLong(save);
        Long idToChange = Long.parseLong(change);

    }
}
