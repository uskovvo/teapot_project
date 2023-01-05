package com.example.teapot_project.servlet.actions.users;

import com.example.teapot_project.model.User;

import javax.servlet.http.HttpServletRequest;

public class ShowCreateUserFormAction extends AbstractUserFormAction {
    @Override
    protected void addUserToRequest(HttpServletRequest req) {
        req.setAttribute("user", new User());

    }
}
