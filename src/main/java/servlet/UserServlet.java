package servlet;

import exceptions.NotValidDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.actions.*;

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

        actionMap.put("delete", new DeleteServletAction());
        actionMap.put("updateForm", new ShowUpdateFormServletAction());
        actionMap.put("createForm", new ShowCreateFormServletAction());
        actionMap.put("all", new GetAllServletAction());
        actionMap.put("update", new UpdateServletAction());
        actionMap.put(null, new GetAllServletAction());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        actionMap.get(action).execute(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            actionMap.get("update").execute(req, resp);
            resp.sendRedirect("users");
        } catch (NotValidDataException ex) {
            req.setAttribute("exception", ex.getMessage());
            req.getRequestDispatcher("notValidForm.jsp").forward(req, resp);
        }
    }


}



