package com.example.teapot_project.servlet;

import com.example.teapot_project.exceptions.DatabaseOperationException;
import com.example.teapot_project.exceptions.NotValidDataException;
import com.example.teapot_project.servlet.actions.*;
import com.example.teapot_project.servlet.actions.groups.*;
import com.example.teapot_project.servlet.actions.users.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger("UserServlet");
    private static Map<String, ServletAction> actionMap;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        log.info("init UserServlet");

        actionMap = new HashMap<>();

        actionMap.put("delete", new DeleteUserAction());
        actionMap.put("updateForm", new ShowUpdateUserFormAction());
        actionMap.put("createForm", new ShowCreateUserFormAction());
        actionMap.put("all", new GetAllUsersAction());
        actionMap.put("updateUser", new UpdateUserAction());
        actionMap.put(null, new GetAllUsersAction());

        actionMap.put("groupForm", new ShowCreateGroupFormAction());
        actionMap.put("updateGroup", new UpdateGroupAction());
        actionMap.put("allGroups", new ReadAllGroupsAction());
        actionMap.put("updateGroupForm", new ShowUpdateGroupFormAction());
        actionMap.put("deleteGroup", new DeleteGroupAction());
        actionMap.put("getGroupUsers", new ReadGroupUsersAction());





    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        actionMap.get(action).execute(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        try {
            actionMap.get(action).execute(req, resp);
        } catch (NotValidDataException | DatabaseOperationException ex) {
            req.setAttribute("exception", ex.getMessage());
            req.getRequestDispatcher("notValidForm.jsp").forward(req, resp);
        }
    }


}



