package com.example.teapot_project.servlet.actions.groups;

import com.example.teapot_project.dao.GroupDao;
import com.example.teapot_project.dao.GroupRepository;
import com.example.teapot_project.model.Group;
import com.example.teapot_project.servlet.actions.ServletAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ReadAllGroupsAction implements ServletAction {
    private GroupRepository repository = GroupDao.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Group> groups = repository.readAll();
        req.setAttribute("groups", groups);
        req.getRequestDispatcher("/groups/groups.jsp").forward(req, resp);

    }
}
