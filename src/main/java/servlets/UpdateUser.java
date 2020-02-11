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
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/update")
public class UpdateUser extends HttpServlet {
    private UserService userService = UserServiceHibernate.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("user", userService.getUserById(Long.parseLong(req.getParameter("id"))));
            req.getRequestDispatcher("/WEB-INF/view/update.jsp").forward(req, resp);
        } catch (DBException e) {
            resp.setStatus(500);
            req.setAttribute("result", "DB ERROR");
            req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            String name = req.getParameter("name");
            int age = Integer.parseInt(req.getParameter("age"));
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String role = req.getParameter("role");
            if (userService.updateUser(id, name, age, email, password, role)) {
                List<User> users = userService.getAllUsers();
                req.setAttribute("usersFromDB", users);
                req.getRequestDispatcher("/WEB-INF/view/allUsers.jsp").forward(req, resp);
            } else {
                resp.setStatus(200);
                req.setAttribute("result", "User with this id don't exists or email already used");
                req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);
            }

        } catch (NumberFormatException e) {
            resp.setStatus(400);
            req.setAttribute("result", "Wrong id or age (use only numbers");
            req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);
        } catch (DBException e) {
            resp.setStatus(500);
            req.setAttribute("result", "DB ERROR");
            req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);
        }
    }
}
