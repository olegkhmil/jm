package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("message", "please login");
        req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (req.getParameter("emailIndex") != null && req.getParameter("passwordIndex") != null) {//нужна проверка на валидность email
            session.setAttribute("email", req.getParameter("emailIndex"));
            session.setAttribute("password", req.getParameter("passwordIndex"));
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.setAttribute("message", "please login");
            req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
        }
    }
}
