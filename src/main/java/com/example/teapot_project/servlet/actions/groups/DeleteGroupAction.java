package com.example.teapot_project.servlet.actions.groups;

import com.example.teapot_project.dao.GroupDao;
import com.example.teapot_project.dao.GroupRepository;
import com.example.teapot_project.servlet.actions.ServletAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteGroupAction implements ServletAction {
    private GroupRepository repository = GroupDao.getInstance();
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        repository.delete(getId(req));
        resp.sendRedirect("/users?action=allGroups");
    }
}
