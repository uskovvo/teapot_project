package com.example.teapot_project.servlet.actions.users;

import com.example.teapot_project.dao.GroupDao;
import com.example.teapot_project.dao.UserDao;
import com.example.teapot_project.dao.UserRepository;
import com.example.teapot_project.model.User;
import com.example.teapot_project.servlet.actions.ServletAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowUpdateFormAction extends AbstractUserFormAction {
    private UserRepository userRepository = UserDao.getInstance();

    @Override
    protected void addUserToRequest(HttpServletRequest req) {
        req.setAttribute("user", userRepository.read(getId(req)));

    }
}
