package com.example.teapot_project.servlet.actions;

import com.example.teapot_project.dao.UserDao;
import com.example.teapot_project.dao.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteServletAction implements ServletAction{
    private UserRepository repository = UserDao.getInstance()
;    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = getId(req);
        repository.delete(id);
        resp.sendRedirect("users");
    }
}
