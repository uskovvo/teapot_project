package com.example.teapot_project.servlet.actions.users;

import com.example.teapot_project.dao.GroupDao;
import com.example.teapot_project.dao.GroupRepository;
import com.example.teapot_project.dao.UserDao;
import com.example.teapot_project.dao.UserRepository;
import com.example.teapot_project.model.User;
import com.example.teapot_project.servlet.actions.ServletAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractUserFormAction implements ServletAction {
    private GroupRepository groupRepository = GroupDao.getInstance();
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addUserToRequest(req);
        req.setAttribute("groups", groupRepository.readAll());
        req.getRequestDispatcher("userForm.jsp").forward(req, resp);
    }

    protected abstract void addUserToRequest(HttpServletRequest request);
}
