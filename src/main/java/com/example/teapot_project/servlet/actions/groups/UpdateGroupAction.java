package com.example.teapot_project.servlet.actions.groups;

import com.example.teapot_project.dao.GroupDao;
import com.example.teapot_project.model.Group;
import com.example.teapot_project.servlet.actions.ServletAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateGroupAction implements ServletAction {
    private GroupDao repository = GroupDao.getInstance();
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Group group = new Group();
        group.setGroupColor(req.getParameter("name"));

        repository.create(group);
        resp.sendRedirect("users");
    }
}
