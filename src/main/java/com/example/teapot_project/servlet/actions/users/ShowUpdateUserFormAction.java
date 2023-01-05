package com.example.teapot_project.servlet.actions.users;

import com.example.teapot_project.dao.UserDao;
import com.example.teapot_project.dao.UserRepository;

import javax.servlet.http.HttpServletRequest;

public class ShowUpdateUserFormAction extends AbstractUserFormAction {
    private UserRepository userRepository = UserDao.getInstance();

    @Override
    protected void addUserToRequest(HttpServletRequest req) {
        req.setAttribute("user", userRepository.read(getId(req)));

    }
}
