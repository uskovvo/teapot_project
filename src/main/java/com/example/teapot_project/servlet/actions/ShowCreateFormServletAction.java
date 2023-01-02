package com.example.teapot_project.servlet.actions;

import com.example.teapot_project.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowCreateFormServletAction implements ServletAction{
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("user", new User());
        req.getRequestDispatcher("userForm.jsp").forward(req, resp);
    }
}
