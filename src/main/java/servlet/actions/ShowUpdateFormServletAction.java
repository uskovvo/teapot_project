package servlet.actions;

import dao.UserDao;
import dao.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowUpdateFormServletAction implements ServletAction{
    private UserRepository userRepository = UserDao.getInstance();
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("user", userRepository.read(getId(req)));
        req.getRequestDispatcher("userForm.jsp").forward(req, resp);
    }
}
