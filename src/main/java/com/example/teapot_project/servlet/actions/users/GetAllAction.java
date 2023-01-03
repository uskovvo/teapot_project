package com.example.teapot_project.servlet.actions.users;

import com.example.teapot_project.dao.GroupDao;
import com.example.teapot_project.dao.GroupRepository;
import com.example.teapot_project.dao.UserDao;
import com.example.teapot_project.dao.UserRepository;
import com.example.teapot_project.model.Group;
import com.example.teapot_project.model.User;
import com.example.teapot_project.servlet.actions.ServletAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetAllAction implements ServletAction {
    private UserRepository userRepository = UserDao.getInstance();
    private GroupRepository groupRepository = GroupDao.getInstance();
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = userRepository.readAll();
        List<Group> groups = groupRepository.readAll();
        req.setAttribute("users", users);
        req.setAttribute("groups", groups);
        req.getRequestDispatcher("/users/users.jsp").forward(req, resp);
    }
}
