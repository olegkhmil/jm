package servlets;

import exception.DBException;
import service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/add")
public class AddUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = req.getParameter("name");
            int age = Integer.parseInt(req.getParameter("age"));
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String role = req.getParameter("role");
            if (UserServiceImpl.getInstance().addUser(name, age, email, password, role)) {
                resp.sendRedirect(req.getContextPath() + "/admin/all");
            } else {
                resp.setStatus(200);
                req.setAttribute("result", "User with email: " + email + " exists");
                req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);
            }
        } catch (NumberFormatException e) {
            resp.setStatus(400);
            req.setAttribute("result", "Wrong age");
            req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);
        } catch (DBException e) {
            resp.setStatus(500);
            req.setAttribute("result", "DB ERROR");
            req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);
        }
    }
}
