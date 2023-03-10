package com.example.teapot_project.servlet.actions.users;

import com.example.teapot_project.dao.UserDao;
import com.example.teapot_project.dao.UserRepository;
import com.example.teapot_project.model.User;
import com.example.teapot_project.servlet.actions.ServletAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateUserAction implements ServletAction {
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
        resp.sendRedirect("/teapot_project/users");
    }

    private void fillUserFields(User user, HttpServletRequest req) {
        user.setName(req.getParameter("name"));
        user.setSurname(req.getParameter("surname"));
        user.setAge(Integer.parseInt(req.getParameter("age")));
        String groupId = req.getParameter("groupId");
        if (!groupId.equals("")) {
            user.setGroupId(Long.parseLong(groupId));
        }
        String answerStatus = req.getParameter("answerStatus");
        if (!answerStatus.equals("")) {
            user.setAnswerStatus(Boolean.parseBoolean(answerStatus));
        }
        if (!req.getParameter("id").equals("")) {
            user.setId(getId(req));
        }
    }
}
