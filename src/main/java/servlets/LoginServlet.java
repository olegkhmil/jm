package servlets;

import exception.DBException;
import model.User;
import service.UserService;
import service.UserServiceHibernate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/")
public class LoginServlet extends HttpServlet {
    private UserService userService = UserServiceHibernate.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            req.setAttribute("message", "HELLO from login get");
            req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, resp);
        } else {
            try {
                User user = userService.getUserByEmail((String)session.getAttribute("email"));
                if(user != null && user.getRole().equals("admin")){
                    resp.sendRedirect(req.getContextPath() + "/admin/all");
                } else if(user != null && user.getRole().equals("user")){
                    resp.sendRedirect(req.getContextPath() + "/user");
                }
            } catch (DBException e) {
                resp.setStatus(500);
                req.setAttribute("result", "DB ERROR");
                req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            session = req.getSession();
        }
        if(req.getParameter("emailIndex") != null && req.getParameter("passwordIndex") != null) {
            session.setAttribute("email", req.getParameter("emailIndex"));
            session.setAttribute("password", req.getParameter("passwordIndex"));
            try {
                User user = userService.getUserByEmail((String)session.getAttribute("email"));
                if(user != null && user.getRole().equals("admin")){
                    resp.sendRedirect(req.getContextPath() + "/admin/all");
                } else if(user != null && user.getRole().equals("user")){
                    resp.sendRedirect(req.getContextPath() + "/user");
                }
            } catch (DBException e) {
                resp.setStatus(500);
                req.setAttribute("result", "DB ERROR");
                req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);
            }
        }else {
            req.setAttribute("message", "HELLO from login post else (param == null)");
            req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, resp);
        }
    }
}
