package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession(false).removeAttribute("email");
        req.getSession(false).removeAttribute("password");
        req.setAttribute("message", "HELLO from logout");
        req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, resp);
    }
}
