package servlets;

import exception.DBException;
import model.User;
import service.UserServiceHibernate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/all")
public class AllUsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.sendRedirect("https://www.google.com");
        try {
            List<User> users = UserServiceHibernate.getInstanceUSH().getAllUsers();
            req.setAttribute("usersFromDB", users);
            //resp.sendRedirect(req.getContextPath() + "/admin/all");
            req.getRequestDispatcher("/WEB-INF/view/allUsers.jsp").forward(req, resp);
        } catch (DBException e) {
            resp.setStatus(500);
            req.setAttribute("result", "DB ERROR");
            req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<User> users = UserServiceHibernate.getInstanceUSH().getAllUsers();
            req.setAttribute("usersFromDB", users);
            //resp.sendRedirect(req.getContextPath() + "/admin/all");
            req.getRequestDispatcher("/WEB-INF/view/allUsers.jsp").forward(req, resp);
        } catch (DBException e) {
            resp.setStatus(500);
            req.setAttribute("result", "DB ERROR");
            req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);
        }
    }
}