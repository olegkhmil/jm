package servlets;

import exception.DBException;
import model.User;
import service.UserService;
import service.UserServiceHibernate;
import service.UserServiceJDBC;
import util.DBHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/all")
public class UserServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.sendRedirect("https://www.google.com");
        try {
            List<User> users = UserServiceHibernate.getInstanceUSH().getAllUsers();
            req.setAttribute("usersFromDB", users);
            req.getRequestDispatcher("/WEB-INF/view/allUsers.jsp").forward(req, resp);
        } catch (DBException e) {
            resp.setStatus(500);
            req.setAttribute("result", "DB ERROR");
            req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //не получается перенаправить на метод "DELETE"
    }
}
