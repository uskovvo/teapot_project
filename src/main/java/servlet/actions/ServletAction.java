package servlet.actions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@FunctionalInterface
public interface ServletAction {
    void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    default long getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
