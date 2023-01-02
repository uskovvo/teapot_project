package com.example.teapot_project.servlet.actions;

import com.example.teapot_project.dao.UserDao;
import com.example.teapot_project.dao.UserRepository;
import com.example.teapot_project.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateServletAction implements ServletAction {
    private UserRepository repository = UserDao.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        fillUserFields(user, req);
        if (user.getId() == null) {
            repository.create(user);
        } else {
            repository.update(user);
        }
    }

    private void fillUserFields(User user, HttpServletRequest req) {
        user.setName(req.getParameter("name"));
        user.setSurname(req.getParameter("surname"));
        user.setAge(Integer.parseInt(req.getParameter("age")));

        if (!req.getParameter("id").equals("")) {
            user.setId(getId(req));
        }
    }
}
