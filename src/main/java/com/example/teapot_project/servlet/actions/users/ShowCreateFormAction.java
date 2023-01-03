package com.example.teapot_project.servlet.actions.users;

import com.example.teapot_project.model.User;
import com.example.teapot_project.servlet.actions.ServletAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowCreateFormAction extends AbstractUserFormAction {
    @Override
    protected void addUserToRequest(HttpServletRequest req) {
        req.setAttribute("user", new User());

    }
}
