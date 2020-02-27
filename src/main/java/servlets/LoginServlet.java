package servlets;

import exception.DBException;
import model.User;
import service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("message", "please login");
        req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try {
            if (req.getParameter("emailIndex") != null && req.getParameter("passwordIndex") != null) {//нужна проверка на валидность email
                User user = userService.getUserByEmail(req.getParameter("emailIndex"));
                if (user != null && user.getPassword().equals(req.getParameter("passwordIndex"))) {
                    session.setAttribute("user", user);
                    resp.sendRedirect(req.getContextPath() + "/login");
                } else {
                    req.setAttribute("message", "User don't exists or wrong password");
                    req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
                }
            } else {
                req.setAttribute("message", "User don't exists or wrong password");
                req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
            }
        } catch (DBException e) {
            req.setAttribute("result", "DB ERROR");
            req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);
        }
    }
}
