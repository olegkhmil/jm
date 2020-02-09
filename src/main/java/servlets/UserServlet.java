package servlets;

import exception.DBException;
import model.User;
import service.UserServiceHibernate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = null;
        try {
            if(session != null && session.getAttribute("email") != null) {
                user = UserServiceHibernate.getInstanceUSH().getUserByEmail((String) session.getAttribute("email"));
            }
            if(user != null && user.getPassword().equals(session.getAttribute("password"))) {
                req.setAttribute("user", user);
                req.getRequestDispatcher("/WEB-INF/view/userPage.jsp").forward(req, resp);
            }else{
                req.setAttribute("message", "HELLO");
                req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, resp);
            }
        } catch (DBException e) {
            resp.setStatus(500);
            req.setAttribute("result", "DB ERROR");
            req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);
        }
    }
}
