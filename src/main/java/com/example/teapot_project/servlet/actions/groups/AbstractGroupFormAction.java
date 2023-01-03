package com.example.teapot_project.servlet.actions.groups;

import com.example.teapot_project.dao.GroupDao;
import com.example.teapot_project.dao.GroupRepository;
import com.example.teapot_project.servlet.actions.ServletAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractGroupFormAction implements ServletAction {
    protected GroupRepository groupRepository = GroupDao.getInstance();
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addGroupToRequest(req);
        req.setAttribute("groups", groupRepository.readAll());
        req.getRequestDispatcher("/groups/groupForm.jsp").forward(req, resp);
    }

    protected abstract void addGroupToRequest(HttpServletRequest request);
}
