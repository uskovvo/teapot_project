package com.example.teapot_project.servlet.actions;

import com.example.teapot_project.dao.UserDao;
import com.example.teapot_project.dao.UserRepository;
import com.example.teapot_project.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetAllServletAction implements ServletAction{
    private UserRepository repository = UserDao.getInstance();
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = repository.readAll();
        req.setAttribute("users", users);
        req.getRequestDispatcher("users.jsp").forward(req, resp);
    }
}
