package com.example.teapot_project.servlet.actions.groups;

import com.example.teapot_project.model.Group;
import com.example.teapot_project.servlet.actions.ServletAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowGroupFormAction implements ServletAction {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("group", new Group());
        req.getRequestDispatcher("groupForm.jsp").forward(req, resp);
    }
}
