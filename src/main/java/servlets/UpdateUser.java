package servlets;

import exception.DBException;
import service.UserService;
import service.UserServiceHibernate;
import service.UserServiceJDBC;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/update")
public class UpdateUser extends HttpServlet {
    private UserService userService = UserServiceHibernate.getInstanceUSH();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/update.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            String name = req.getParameter("name");
            int age = Integer.parseInt(req.getParameter("age"));
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            if (userService.updateUser(id, name, age, email, password)) {
                req.setAttribute("result", "Success");
            } else {
                req.setAttribute("result", "User with this id don't exists or email already used");
            }
            resp.setStatus(200);
            req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);
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
